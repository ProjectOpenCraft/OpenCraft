package opencraft.world.object.render.component;

import org.json.simple.JSONObject;

import opencraft.lib.entity.Entity;
import opencraft.lib.entity.IEntity;
import opencraft.lib.entity.data.DoubleXYZ;

public class RenderComponentPoint extends RenderComponent {

	@Override
	public String getId() {
		return "render|OpenCraft|point";
	}

	@Override
	public String getType() {
		return "point";
	}
	
	public String name;
	public DoubleXYZ coord;
	
	public RenderComponentPoint() {
		this.name = "";
		this.coord = null;
	}
	
	public RenderComponentPoint(String name, DoubleXYZ coord) {
		this.name = name;
		this.coord = coord;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJSON(JSONObject json) {
		super.toJSON(json);
		json.put("name", name);
		json.put("coord", coord.toJSON(new JSONObject()));
		return json;
	}
	
	@Override
	public IEntity fromJSON(JSONObject json) {
		super.fromJSON(json);
		this.name = (String) json.get("name");
		this.coord = (DoubleXYZ) Entity.registry.getEntity((JSONObject) json.get("coord"));
		return this;
	}
}
