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
