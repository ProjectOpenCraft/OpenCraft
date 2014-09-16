/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage minecraft style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * class PacketBlockMap
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft.packet.s2c;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import opencraft.lib.entity.IEntity;
import opencraft.lib.event.packet.Packet;

public class PacketBlockMap extends Packet {

	@Override
	public String getId() {
		return "packet|OpenCraft|blockMap";
	}
	
	public final Map<Character, String> mapBlock;
	
	public PacketBlockMap() {
		this.mapBlock = null;
	}
	
	public PacketBlockMap(Map<Character, String> map) {
		this.mapBlock = map;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJSON(JSONObject json) {
		super.toJSON(json);
		for (Character cha : this.mapBlock.keySet()) {
			json.put(cha, this.mapBlock.get(cha));
		}
		return json;
	}
	
	@Override
	public IEntity fromJSON(JSONObject json) {
		super.fromJSON(json);
		Map<Character, String> map = new HashMap<Character, String>();
		for (Object ch : json.keySet()) {
			Character cha = (Character) ch;
			map.put(cha, (String) json.get(cha));
		}
		return new PacketBlockMap(map);
	}
}
