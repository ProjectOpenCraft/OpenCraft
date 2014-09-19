package opencraft.packet.c2s;

import org.json.simple.JSONObject;

import opencraft.lib.entity.IEntity;
import opencraft.lib.event.packet.Packet;

public class PacketKeyInput extends Packet {
	
	public final String keyName;
	public final boolean keyDown;
	
	public PacketKeyInput() {
		this.keyName = "";
		this.keyDown = true;
	}
	
	public PacketKeyInput(String name, boolean isDown) {
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
		return new PacketKeyInput((String)json.get("name"), (Boolean)json.get("down"));
	}

	@Override
	public String getId() {
		return "packet|OpenCraft|keyInput";
	}
}