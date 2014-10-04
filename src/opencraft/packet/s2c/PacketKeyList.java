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

package opencraft.packet.s2c;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import opencraft.lib.entity.IEntity;
import opencraft.lib.event.packet.Packet;

public class PacketKeyList extends Packet {

	@Override
	public String getId() {
		return "packet|OpenCraft|keyList";
	}
	
	public Map<String, Integer> keyMap;
	
	public PacketKeyList() {
		this.keyMap = new HashMap<String, Integer>();
	}
	
	public PacketKeyList(Map<String, Integer> keyMap) {
		this.keyMap = keyMap;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJSON(JSONObject json) {
		super.toJSON(json);
		for (String key : keyMap.keySet()) {
			json.put(key, this.keyMap.get(key));
		}
		return json;
	}
	
	@Override
	public IEntity fromJSON(JSONObject json) {
		super.fromJSON(json);
		for (Object key : json.keySet()) {
			this.keyMap.put((String)key, (Integer)json.get(key));
		}
		return this;
	}
}
