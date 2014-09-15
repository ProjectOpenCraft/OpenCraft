/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage minecraft style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * class PacketReceiver
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft.lib.event.packet;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import opencraft.lib.entity.IEntityRegistry;
import opencraft.lib.event.EventDispatcher;

public class PacketReceiver extends EventDispatcher {
	
	public PacketReceiver(IEntityRegistry registry, InputStream input) {
		super();
		ReceiverThread thread = new ReceiverThread(this, registry, input);
		thread.start();
	}
	
	class ReceiverThread extends Thread {
		
		PacketReceiver pr;
		IEntityRegistry registry;
		BufferedReader r;
		JSONParser parser;
		
		public ReceiverThread(PacketReceiver pr, IEntityRegistry registry, InputStream input) {
			this.pr = pr;
			this.registry = registry;
			this.r = new BufferedReader(new InputStreamReader(input));
			this.parser = new JSONParser();
		}
		
		public void run() {
			while (r != null) {
				try {
					JSONObject json = (JSONObject) parser.parse(r.readLine());
					Packet packet = (Packet) registry.getEntity(json);
					
					pr.emit(packet);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
