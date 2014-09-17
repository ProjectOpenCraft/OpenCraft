package opencraft.world.object.render.component;

import org.json.simple.JSONObject;

import opencraft.lib.entity.Entity;
import opencraft.lib.entity.IEntity;
import opencraft.lib.entity.data.Box;
import opencraft.lib.entity.data.DoubleXYZ;

public class RenderComponentBox extends RenderComponent {

	@Override
	public String getId() {
		return "render|OpenCraft|box";
	}

	@Override
	public String getType() {
		return "box";
	}
	
	public DoubleXYZ coord;
	public double yaw;
	public double pitch;
	public double roll;
	public Box box;
	
	public RenderComponentBox() {
		this.coord = null;
		this.yaw = 0d;
		this.pitch = 0d;
		this.roll = 0d;
		this.box = null;
	}
	
	public RenderComponentBox(DoubleXYZ coord, double yaw, double pitch, double roll, Box box) {
		this.coord = coord;
		this.yaw = yaw;
		this.pitch = pitch;
		this.roll = roll;
		this.box = box;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJSON(JSONObject json) {
		super.toJSON(json);
		json.put("coord", this.coord.toJSON(new JSONObject()));
		json.put("yaw", this.yaw);
		json.put("pitch", this.pitch);
		json.put("roll", this.roll);
		json.put("box", this.box.toJSON(new JSONObject()));
		return json;
	}
	
	@Override
	public IEntity fromJSON(JSONObject json) {
		super.fromJSON(json);
		this.coord = (DoubleXYZ) Entity.registry.getEntity((JSONObject) json.get("coord"));
		this.yaw = (double) json.get("yaw");
		this.pitch = (double) json.get("pitch");
		this.roll = (double) json.get("roll");
		this.box = (Box) Entity.registry.getEntity((JSONObject) json.get("box"));
		return this;
	}
}
