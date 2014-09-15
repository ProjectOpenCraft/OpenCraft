/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage minecraft style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * class PacketSender
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft.lib.event.packet;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.json.simple.JSONObject;

import opencraft.lib.event.EventDispatcher;
import opencraft.lib.event.IEvent;

public class PacketSender extends EventDispatcher {
	
	BufferedWriter w;
	
	public PacketSender(OutputStream output) {
		this.w = new BufferedWriter(new OutputStreamWriter(output));
	}
	
	@Override
	public IEvent emit(IEvent event) {
		super.emit(event);
		
		if (event instanceof Packet) {
			String data = ((Packet) event).toJSON(new JSONObject()).toJSONString();
			try {
				w.write(data);
				w.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return event;
	}
}
