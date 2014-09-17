package opencraft.world.object;

import org.json.simple.JSONObject;

import opencraft.lib.entity.Entity;
import opencraft.lib.entity.IEntity;
import opencraft.lib.entity.data.DoubleXYZ;

public class ObjectRenderInfo extends Entity {

	@Override
	public String getId() {
		return "render|OpenCraft|info";
	}
	
	public DoubleXYZ coord;
	public double angle;
	public long type;
	
	public ObjectRenderInfo() {
		this.coord = null;
		this.angle = 0d;
		this.type = 0l;
	}
	
	public ObjectRenderInfo(DoubleXYZ coord, double angle, long type) {
		this.coord = coord;
		this.angle = angle;
		this.type = type;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJSON(JSONObject json) {
		super.toJSON(json);
		json.put("coord", this.coord.toJSON(new JSONObject()));
		json.put("angle", this.angle);
		json.put("type", this.type);
		return json;
	}
	
	@Override
	public IEntity fromJSON(JSONObject json) {
		super.fromJSON(json);
		this.coord = (DoubleXYZ) Entity.registry.getEntity((JSONObject) json.get("coord"));
		this.angle = (double) json.get("angle");
		this.type = (long) json.get("type");
		return this;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ObjectRenderInfo) {
			ObjectRenderInfo o = (ObjectRenderInfo)obj;
			return this.coord.equals(o) && this.angle == o.angle && this.type == o.type; 
		} else return false;
	}
}
