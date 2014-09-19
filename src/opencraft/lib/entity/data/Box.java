/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage sandbox style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * class Box
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft.lib.entity.data;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import opencraft.lib.entity.Entity;
import opencraft.lib.entity.IEntity;

public class Box extends Entity {
	
	
	public final double mX, mY, mZ, MX, MY, MZ;
	
	public Box() {
		this.mX = Double.MAX_VALUE;
		this.mY = Double.MAX_VALUE;
		this.mZ = Double.MAX_VALUE;
		this.MX = Double.MAX_VALUE;
		this.MY = Double.MAX_VALUE;
		this.MZ = Double.MAX_VALUE;
	}
	
	public Box(double mx, double my, double mz, double Mx, double My, double Mz) {
		this.mX = mx;
		this.mY = my;
		this.mZ = mz;
		this.MX = Mx;
		this.MY = My;
		this.MZ = Mz;
	}
	
	public Box(DoubleXYZ p1, DoubleXYZ p2) {
		this.mX = Math.min(p1.x, p2.x);
		this.mY = Math.min(p1.y, p2.y);
		this.mZ = Math.min(p1.z, p2.z);
		this.MX = Math.max(p1.x, p2.x);
		this.MY = Math.max(p1.y, p2.y);
		this.MZ = Math.max(p1.z, p2.z);
	}
	
	public Box(JSONObject json) {
		JSONArray jarr = (JSONArray) json.get("pp");
		
		if (jarr.size() >= 2) {
			DoubleXYZ p1 = new DoubleXYZ((JSONObject)jarr.get(0));
			DoubleXYZ p2 = new DoubleXYZ((JSONObject)jarr.get(1));
			this.mX = Math.min(p1.x, p2.x);
			this.mY = Math.min(p1.y, p2.y);
			this.mZ = Math.min(p1.z, p2.z);
			this.MX = Math.max(p1.x, p2.x);
			this.MY = Math.max(p1.y, p2.y);
			this.MZ = Math.max(p1.z, p2.z);
		} else {
			this.mX = 0;
			this.mY = 0;
			this.mZ = 0;
			this.MX = 0;
			this.MY = 0;
			this.MZ = 0;
		}
	}
	
	@Override
	public String toString() {
		return new StringBuffer("(").append(mX).append(",").append(mY).append(",").append(mZ).append(",").append(MX).append(",").append(MY).append(",").append(MZ).append(")").toString();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJSON(JSONObject json) {
		super.toJSON(json);
		JSONArray jarr = new JSONArray();
		DoubleXYZ p1 = new DoubleXYZ(mX, mY, mZ);
		DoubleXYZ p2 = new DoubleXYZ(MX, MY, MZ);
		jarr.add(p1.toJSON(new JSONObject()));
		jarr.add(p2.toJSON(new JSONObject()));
		json.put("pp", jarr);
		return json;
	}
	
	@Override
	public IEntity fromJSON(JSONObject json) {
		super.fromJSON(json);
		return new Box(json);
	}

	@Override
	public String getId() {
		return "data|OpenCraft|Box";
	}
}

