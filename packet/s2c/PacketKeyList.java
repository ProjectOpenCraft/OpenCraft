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
