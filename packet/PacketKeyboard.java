package opencraft.packet;

import org.json.simple.JSONObject;

import opencraft.lib.entity.IEntity;
import opencraft.lib.event.packet.Packet;

public class PacketKeyboard extends Packet {
	
	public final String keyName;
	public final boolean keyDown;
	
	public PacketKeyboard() {
		this.keyName = "";
		this.keyDown = true;
	}
	
	public PacketKeyboard(String name, boolean isDown) {
		this.keyName = name;
		this.keyDown = isDown;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public JSONObject toJSON(JSONObject json) {
		super.toJSON(json);
		json.put("name", this.keyName);
		json.put("down", this.keyDown);
		return json;
	}
	
	@Override
	public IEntity fromJSON(JSONObject json) {
		super.fromJSON(json);
		return new PacketKeyboard((String)json.get("name"), (Boolean)json.get("down"));
	}
}