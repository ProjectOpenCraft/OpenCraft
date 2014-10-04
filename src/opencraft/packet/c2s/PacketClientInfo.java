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

package opencraft.packet.c2s;

import org.json.simple.JSONObject;

import opencraft.lib.entity.IEntity;
import opencraft.lib.event.packet.Packet;
import opencraft.server.client.ClientInfo;

public class PacketClientInfo extends Packet {
	
	
	public final ClientInfo info;
	
	public PacketClientInfo() {
		this.info = null;
	}
	
	public PacketClientInfo(ClientInfo i) {
		this.info = i;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public JSONObject toJSON(JSONObject json) {
		super.toJSON(json);
		json.put("info", this.info.toJSON(new JSONObject()));
		return json;
	}
	
	@Override
	public IEntity fromJSON(JSONObject json) {
		super.fromJSON(json);
		return new PacketClientInfo((ClientInfo) new ClientInfo().fromJSON((JSONObject) json.get("info")));
	}

	@Override
	public String getId() {
		return "packet|OpenCraft|ClientInfo";
	}
}
