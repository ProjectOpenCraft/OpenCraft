package opencraft.packet.s2c;

import org.json.simple.JSONObject;

import opencraft.lib.entity.IEntity;
import opencraft.lib.event.packet.Packet;

public class PacketYouDied extends Packet {

	@Override
	public String getId() {
		return "packet|OpenCraft|die";
	}
	
	public String message;
	
	public PacketYouDied() {
		this.message = "";
	}
	
	public PacketYouDied(String msg) {
		this.message = msg;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJSON(JSONObject json) {
		super.toJSON(json);
		json.put("msg", this.message);
		return json;
	}
	
	@Override
	public IEntity fromJSON(JSONObject json) {
		super.fromJSON(json);
		this.message = (String) json.get("msg");
		return this;
	}
}
