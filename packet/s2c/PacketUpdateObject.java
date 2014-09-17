package opencraft.packet.s2c;

import org.json.simple.JSONObject;

import opencraft.lib.entity.Entity;
import opencraft.lib.entity.IEntity;
import opencraft.lib.entity.data.DoubleXYZ;
import opencraft.lib.event.packet.Packet;

public class PacketUpdateObject extends Packet {

	@Override
	public String getId() {
		return "packet|OpenCraft|updateObject";
	}
	
	public DoubleXYZ coord;
	public double angle;
	public int renderType;
	
	public PacketUpdateObject() {
		this.coord = null;
		this.angle = 0d;
		this.renderType = 0;
	}
	
	public PacketUpdateObject(DoubleXYZ coord, double angle, int type) {
		this.coord = coord;
		this.angle = angle;
		this.renderType = type;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJSON(JSONObject json) {
		super.toJSON(json);
		json.put("coord", this.coord.toJSON(new JSONObject()));
		json.put("angle", this.angle);
		json.put("type", this.renderType);
		return json;
	}
	
	@Override
	public IEntity fromJSON(JSONObject json) {
		super.fromJSON(json);
		this.coord = (DoubleXYZ) Entity.registry.getEntity((JSONObject) json.get("coord"));
		this.angle = (double) json.get("angle");
		this.renderType = (int) json.get("type");
		return this;
	}
}
