package opencraft.world.object.render;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import opencraft.lib.entity.Entity;
import opencraft.lib.entity.IEntity;
import opencraft.lib.entity.data.IntXYZ;
import opencraft.world.object.render.component.RenderComponent;

public abstract class ObjectRenderInfo extends Entity {

	@Override
	public String getId() {
		return "render|OpenCraft|info";
	}
	
	public IntXYZ coord;
	public double angle;
	public List<RenderComponent> components = new ArrayList<RenderComponent>();
	
	public ObjectRenderInfo() {
		this.coord = null;
		this.angle = 0d;
	}
	
	public ObjectRenderInfo(IntXYZ coord, double angle) {
		this.coord = coord;
		this.angle = angle;
	}
	
	public void addComponents(RenderComponent component) {
		this.components.add(component);
	}
	public List<RenderComponent> getComponents() {
		return this.components;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJSON(JSONObject json) {
		super.toJSON(json);
		json.put("coord", this.coord.toJSON(new JSONObject()));
		json.put("angle", this.angle);
		JSONArray jarr = new JSONArray();
		for (RenderComponent rc : getComponents()) {
			jarr.add(rc.toJSON(new JSONObject()));
		}
		json.put("comps", jarr);
		return json;
	}
	
	@Override
	public IEntity fromJSON(JSONObject json) {
		super.fromJSON(json);
		this.coord = (IntXYZ) Entity.registry.getEntity((JSONObject) json.get("coord"));
		this.angle = (double) json.get("angle");
		JSONArray jarr = (JSONArray) json.get("comps");
		for (Object obj : jarr) {
			this.addComponents((RenderComponent) Entity.registry.getEntity((JSONObject) obj));
		}
		return this;
	}
}
