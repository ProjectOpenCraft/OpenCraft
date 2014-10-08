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

package opencraft.lib.event.packet;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.google.gson.Gson;

import opencraft.lib.event.EventDispatcher;
import opencraft.lib.event.IEvent;

public class PacketSender extends EventDispatcher {
	
	public static Gson gson = new Gson();
	
	public BufferedOutputStream out;
	DataOutputStream w;
	
	public PacketSender(OutputStream output) {
		this.out = new BufferedOutputStream(output);
		this.w = new DataOutputStream(new BufferedOutputStream(output));
	}
	
	@Override
	public synchronized IEvent emit(IEvent event) {
		super.emit(event);
		
		if (event instanceof Packet) {
			String data = gson.toJson(event);
			try {
				data = new StringBuffer("pac").append(data).append("ket").toString();
				w.writeUTF(data);
				w.flush();
				((Packet) event).sendBinary(out);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return event;
	}
}
