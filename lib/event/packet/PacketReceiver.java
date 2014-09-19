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

import opencraft.lib.entity.Entity;
import opencraft.lib.event.EventDispatcher;

public class PacketReceiver extends EventDispatcher {
	
	public PacketReceiver(InputStream input) {
		super();
		ReceiverThread thread = new ReceiverThread(this, input);
		thread.start();
	}
	
	class ReceiverThread extends Thread {
		
		PacketReceiver pr;
		BufferedReader r;
		JSONParser parser;
		
		public ReceiverThread(PacketReceiver pr, InputStream input) {
			this.pr = pr;
			this.r = new BufferedReader(new InputStreamReader(input));
			this.parser = new JSONParser();
		}
		
		public void run() {
			while (r != null) {
				try {
					StringBuilder sb = new StringBuilder();
					
					String pac;
					if ((pac = r.readLine()).startsWith("pac")) {
						sb.append(pac);
						String stack;
						do {
							stack = r.readLine();
							sb.append(stack);
						} while (!stack.endsWith("ket"));
					}
					String data = sb.toString();
					JSONObject json = (JSONObject) parser.parse(data.substring(3, data.length() - 3));
					Packet packet = (Packet) Entity.registry.getEntity(json);
					
					pr.emit(packet);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
