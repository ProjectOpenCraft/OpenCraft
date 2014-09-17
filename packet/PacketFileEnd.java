package opencraft.packet;

import org.json.simple.JSONObject;

import opencraft.lib.entity.IEntity;
import opencraft.lib.event.packet.Packet;

public class PacketFileEnd extends Packet {

	@Override
	public String getId() {
		return "packet|OpenCraft|fileEnd";
	}
	
	public final String filePath;
	
	public PacketFileEnd() {
		this.filePath = "";
	}
	
	public PacketFileEnd(String name) {
		this.filePath = name;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJSON(JSONObject json) {
		super.toJSON(json);
		json.put("name", this.filePath);
		return json;
	}
	
	@Override
	public IEntity fromJSON(JSONObject json) {
		super.fromJSON(json);
		return new PacketFileEnd((String)json.get("name"));
	}
}
