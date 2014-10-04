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

public class PacketPlayerSight extends Packet {
	
	public final double pitch;
	public final double yaw;
	
	public PacketPlayerSight() {
		this.pitch = 0d;
		this.yaw = 0d;
	}
	
	public PacketPlayerSight(double pitch, double yaw) {
		this.pitch = pitch;
		this.yaw = yaw;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public JSONObject toJSON(JSONObject json) {
		super.toJSON(json);
		json.put("p", this.pitch);
		json.put("y", this.yaw);
		return json;
	}
	
	@Override
	public IEntity fromJSON(JSONObject json) {
		super.fromJSON(json);
		return new PacketPlayerSight((Double)json.get("p"), (Double)json.get("y"));
	}

	@Override
	public String getId() {
		return "packet|OpenCraft|PlayerSight";
	}
}
