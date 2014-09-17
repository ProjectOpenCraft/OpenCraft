/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage minecraft style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * class DoubleXYZ
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft.lib.entity.data;

import opencraft.lib.entity.Entity;
import opencraft.lib.entity.IEntity;

import org.json.simple.JSONObject;

public class DoubleXYZ extends Entity {
	
	
	public final double x, y, z;
	
	public DoubleXYZ() {
		this.x = Double.MAX_VALUE;
		this.y = Double.MAX_VALUE;
		this.z = Double.MAX_VALUE;
	}
	
	public DoubleXYZ(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public DoubleXYZ(String str) {
		if (str.indexOf("(") != 0 || str.indexOf(")") != str.length() -1) {
			this.x = Double.MAX_VALUE;
			this.y = Double.MAX_VALUE;
			this.z = Double.MAX_VALUE;
		} else {
			String[] arr = str.replaceAll("(", "").replaceAll(")", "").split(",");
			if (arr.length < 3) {
				this.x = Double.MAX_VALUE;
				this.y = Double.MAX_VALUE;
				this.z = Double.MAX_VALUE;
			} else {
				double valX = Double.MAX_VALUE;
				double valY = Double.MAX_VALUE;
				double valZ = Double.MAX_VALUE;
				try {
					valX = Double.parseDouble(arr[0]);
					valY = Double.parseDouble(arr[1]);
					valZ = Double.parseDouble(arr[2]);
				} catch(NumberFormatException e) {
					this.x = Double.MAX_VALUE;
					this.y = Double.MAX_VALUE;
					this.z = Double.MAX_VALUE;
					return;
				}
				x=valX;
				y=valY;
				z=valZ;
			}
		}
	}
	
	public DoubleXYZ(JSONObject json) {
		this.x = (double) json.get("x");
		this.y = (double) json.get("y");
		this.z = (double) json.get("z");
	}
	
	@Override
	public String toString() {
		return new StringBuffer("(").append(x).append(",").append(y).append(",").append(z).append(")").toString();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public JSONObject toJSON(JSONObject json) {
		super.toJSON(json);
		json.put("x", x);
		json.put("y", y);
		json.put("z", z);
		return json;
	}
	
	@Override
	public IEntity fromJSON(JSONObject json) {
		super.fromJSON(json);
		return new DoubleXYZ(json);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DoubleXYZ) {
			DoubleXYZ d = (DoubleXYZ) obj;
			return this.x == d.x && this.y == d.y && this.z == d.z;
		} else return false;
	}

	@Override
	public String getId() {
		return "data|OpenCraft|doubleXYZ";
	}
}
