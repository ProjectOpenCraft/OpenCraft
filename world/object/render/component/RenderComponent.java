package opencraft.world.object.render.component;

import org.json.simple.JSONObject;

import opencraft.lib.entity.Entity;

public abstract class RenderComponent extends Entity {
	
	public abstract String getType();
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJSON(JSONObject json) {
		super.toJSON(json);
		json.put("type", getType());
		return json;
	}
}
