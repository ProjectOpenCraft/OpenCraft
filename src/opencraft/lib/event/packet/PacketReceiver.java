/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage sandbox style modding.
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

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import opencraft.OpenCraft;
import opencraft.lib.entity.Entity;
import opencraft.lib.event.EventDispatcher;
import opencraft.packet.c2s.PacketPartServer;

public class PacketReceiver extends EventDispatcher {
	
	ReceiverThread thread;
	
	public PacketReceiver(InputStream input) {
		super();
		thread = new ReceiverThread(this, input);
		thread.start();
	}
	
	public void stop() {
		this.thread.interrupt();
	}
	
	class ReceiverThread extends Thread {
		
		PacketReceiver pr;
		DataInputStream r;
		JSONParser parser;
		
		public ReceiverThread(PacketReceiver pr, InputStream input) {
			this.setName("PacketReceiver");
			this.pr = pr;
			this.r = new DataInputStream(new BufferedInputStream(input));
			this.parser = new JSONParser();
		}
		
		public void run() {
			while (r != null && !this.isInterrupted()) {
				try {
					StringBuilder sb = new StringBuilder();
					
					String pac = r.readUTF();
					if (pac != null && pac.startsWith("pac")) {
						sb.append(pac);
						if (!pac.endsWith("ket")) {
							String stack;
							do {
								stack = r.readUTF();
								sb.append(stack);
							} while (!stack.endsWith("ket"));
						}
						String data = sb.toString();
						JSONObject json = (JSONObject) parser.parse(data.substring(3, data.length() - 3));
						Packet packet = (Packet) Entity.registry.getEntity(json);
						
						OpenCraft.log.debug(data);
						pr.emit(packet);
					}
					
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (IOException e) {
					OpenCraft.log.debug("Client connection closed");
					this.pr.emit(new PacketPartServer("Connection closed"));
					this.interrupt();
				}
			}
		}
	}
}
