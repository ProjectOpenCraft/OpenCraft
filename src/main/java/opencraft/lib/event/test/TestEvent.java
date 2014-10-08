/*
 *OpenCraft - Build your own open world!
 *
 *Author : HyeonuPark(Moonrise1275)
 *
 *--------------------------------------------------------------------------
 *
 *The MIT License (MIT)
 *
 *Permission is hereby granted, free of charge, to any person obtaining a copy
 *of this software and associated documentation files (the "Software"), to deal
 *in the Software without restriction, including without limitation the rights
 *to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *copies of the Software, and to permit persons to whom the Software is
 *furnished to do so, subject to the following conditions:
 *
 *The above copyright notice and this permission notice shall be included in
 *all copies or substantial portions of the Software.
 *
 *THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *THE SOFTWARE.
 *
 */

package opencraft.lib.event.test;

import com.google.gson.Gson;

import opencraft.lib.event.EnumEventOrder;
import opencraft.lib.event.EventDispatcher;
import opencraft.lib.event.IEvent;
import opencraft.lib.event.IEventDispatcher;
import opencraft.lib.event.IEventListener;
import opencraft.lib.event.packet.Packet;

public class TestEvent{
	
	public static Gson gson = new Gson();

	public static void main(String[] args) {
		
		IEventDispatcher ed = new EventDispatcher();
		ed.addListener(new IEventListener() {

			@Override
			public Class<? extends IEvent> getEventClass() {
				return PacketPerson.class;
			}

			@Override
			public IEvent handleEvent(IEvent event) {
				PacketPerson e = (PacketPerson) event;
				
				System.out.println(e.name + " - " + e.age);
				
				return event;
			}

			@Override
			public EnumEventOrder getOrder() {
				return EnumEventOrder.lower;
			}
			
		});
		ed.addListener(new IEventListener() {

			@Override
			public Class<? extends IEvent> getEventClass() {
				return PacketPerson.class;
			}

			@Override
			public IEvent handleEvent(IEvent event) {
				PacketPerson e = (PacketPerson) event;
				
				System.out.println("Name : " + e.name);
				System.out.println("Age : " + e.age);
				
				return event;
			}

			@Override
			public EnumEventOrder getOrder() {
				return EnumEventOrder.lower;
			}
			
		});
		ed.emit(new PacketPerson("Moonrise", 20L));
		
		String data = gson.toJson(new PacketPerson("arcticfox", 19L));
		
		
		
		Packet packet = gson.fromJson(data, PacketPerson.class);
		ed.emit(packet);
	}
	
	public static class PacketPerson extends Packet {
		
		public String name;
		public long age;
		
		public PacketPerson(String n, long a) {
			super("OpenCraft|testPerson");
			this.name = n;
			this.age = a;
		}
	}
}
