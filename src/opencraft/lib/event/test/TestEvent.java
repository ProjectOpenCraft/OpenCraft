/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage sandbox style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * class TestEvent
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft.lib.event.test;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import opencraft.lib.entity.Entity;
import opencraft.lib.entity.IEntity;
import opencraft.lib.entity.IEntityRegistry;
import opencraft.lib.event.EventDispatcher;
import opencraft.lib.event.IEvent;
import opencraft.lib.event.IEventDispatcher;
import opencraft.lib.event.IEventListener;
import opencraft.lib.event.packet.Packet;

public class TestEvent{

	public static void main(String[] args) throws ParseException {
		
		IEventDispatcher ed = new EventDispatcher();
		ed.addListener(new IEventListener() {

			@Override
			public Class<? extends IEvent> getEventClass() {
				return EventPerson.class;
			}

			@Override
			public IEvent handleEvent(IEvent event) {
				EventPerson e = (EventPerson) event;
				
				System.out.println(e.name + " - " + e.age);
				
				return event;
			}
			
		});
		ed.addListener(new IEventListener() {

			@Override
			public Class<? extends IEvent> getEventClass() {
				return EventPerson.class;
			}

			@Override
			public IEvent handleEvent(IEvent event) {
				EventPerson e = (EventPerson) event;
				
				System.out.println("Name : " + e.name);
				System.out.println("Age : " + e.age);
				
				return event;
			}
			
		});
		ed.emit(new TestEvent.EventPerson("Moonrise", 20L));
		
		
		IEntityRegistry registry = Entity.registry;
		JSONParser parser = new JSONParser();
		registry.registerEntity(new EventPerson());
		
		String data = new TestEvent.EventPerson("arcticfox", 19L).toJSON(new JSONObject()).toJSONString();
		
		Packet packet = (Packet) registry.getEntity((JSONObject) parser.parse(data));
		ed.emit(packet);
	}
	
	public static class EventPerson extends Packet {
		
		public String name;
		public long age;
		
		public EventPerson() {
			this.name = "";
			this.age =0;
		}
		
		public EventPerson(String n, long a) {
			this.name = n;
			this.age = a;
		}

		@Override
		@SuppressWarnings("unchecked")
		public JSONObject toJSON(JSONObject json) {
			super.toJSON(json);
			
			json.put("name", name);
			json.put("age", age);
			
			return json;
		}

		@Override
		public IEntity fromJSON(JSONObject json) {
			this.name = (String) json.get("name");
			this.age = (long) json.get("age");
			
			return this;
		}

		@Override
		public String getId() {
			return "packet|testOpenCraft|person";
		}
	}
}