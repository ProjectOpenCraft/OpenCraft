/*
 *OpenCraft - Build your own open world!
 *
 *Author : HyeonuPark(Moonrise1275)
 *
 *--------------------------------------------------------------------------
 *
 *The MIT License (MIT)
 *
 *Copyright (c) <year> <copyright holders>
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
