package opencraft.packet.c2s;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import opencraft.lib.entity.IEntity;
import opencraft.lib.event.packet.Packet;

public class PacketRequestMod extends Packet {

	@Override
	public String getId() {
		return "packet|OpenCraft|requestMod";
	}
	
	public List<String> modList;
	
	public PacketRequestMod() {
		this.modList = null;
	}
	
	public PacketRequestMod(List<String> mods) {
		this.modList = mods;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJSON(JSONObject json) {
		super.toJSON(json);
		JSONArray arr = new JSONArray();
		for (String mod : modList) {
			arr.add(mod);
		}
		json.put("mods", arr);
		return json;
	}
	
	@Override
	public IEntity fromJSON(JSONObject json) {
		super.fromJSON(json);
		JSONArray arr = (JSONArray) json.get("mods");
		for(Object mod : arr) {
			this.modList.add(mod.toString());
		}
		return this;
	}
}
