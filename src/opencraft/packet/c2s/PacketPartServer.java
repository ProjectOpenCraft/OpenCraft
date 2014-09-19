package opencraft.packet.c2s;

import org.json.simple.JSONObject;

import opencraft.lib.entity.IEntity;
import opencraft.lib.event.packet.Packet;

public class PacketPartServer extends Packet {

	@Override
	public String getId() {
		return "packet|OpenCraft|partServer";
	}
	
	public String message;
	
	public PacketPartServer() {
		this.message = "";
	}
	
	public PacketPartServer(String msg) {
		this.message = msg;
	}
	
	@Override
	@SuppressWarnings("unchecked")
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
