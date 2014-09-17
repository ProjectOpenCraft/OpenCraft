package opencraft.world.object.render.component;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import opencraft.lib.entity.Entity;
import opencraft.lib.entity.IEntity;
import opencraft.lib.entity.data.DoubleXY;

public class RenderComponentTriangle extends RenderComponent {

	@Override
	public String getId() {
		return "render|OpenCraft|triangle";
	}
	
	public String[] wpoints;
	public DoubleXY[] tpoints;
	public String texture;
	
	public RenderComponentTriangle() {
		this.wpoints = new String[3];
		this.tpoints = new DoubleXY[3];
		this.texture = "";
	}
	
	public RenderComponentTriangle(String[] wpoints, DoubleXY[] tpoints, String texture) {
		this.wpoints = wpoints;
		this.tpoints = tpoints;
		this.texture = texture;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJSON(JSONObject json) {
		super.toJSON(json);
		json.put("texture", texture);
		JSONArray jarr1 = new JSONArray();
		for (int i=0; i<wpoints.length; i++) {
			jarr1.add(wpoints[i]);
		}
		json.put("wpoints", jarr1);
		JSONArray jarr2 = new JSONArray();
		for (int i=0; i<tpoints.length; i++) {
			jarr2.add(tpoints[i].toJSON(new JSONObject()));
		}
		json.put("tpoints", jarr2);
		return json;
	}
	
	@Override
	public IEntity fromJSON(JSONObject json) {
		super.fromJSON(json);
		this.texture = (String) json.get("texture");
		JSONArray jarr1 = (JSONArray) json.get("wpoints");
		for (int i=0; i<wpoints.length; i++) {
			wpoints[i] = (String) jarr1.get(i);
		}
		JSONArray jarr2 = (JSONArray) json.get("tpoints");
		for (int i=0; i<tpoints.length; i++) {
			tpoints[i] = (DoubleXY) Entity.registry.getEntity((JSONObject) jarr2.get(i));
		}
		return this;
	}
	
	@Override
	public String getType() {
		return "triangle";
	}
}