/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage sandbox style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * class IntXYZ
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft.lib.entity.data;

import org.json.simple.JSONObject;

import opencraft.lib.entity.Entity;
import opencraft.lib.entity.IEntity;

public class IntXYZ extends Entity {
	
	
	public final int x, y, z;
	
	public IntXYZ() {
		this.x = Integer.MAX_VALUE;
		this.y = Integer.MAX_VALUE;
		this.z = Integer.MAX_VALUE;
	}
	
	public IntXYZ(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public IntXYZ(String str) {
		if (str.indexOf("(") != 0 || str.indexOf(")") != str.length() -1) {
			this.x = Integer.MAX_VALUE;
			this.y = Integer.MAX_VALUE;
			this.z = Integer.MAX_VALUE;
		} else {
			String[] arr = str.replaceAll("(", "").replaceAll(")", "").split(",");
			if (arr.length < 3) {
				this.x = Integer.MAX_VALUE;
				this.y = Integer.MAX_VALUE;
				this.z = Integer.MAX_VALUE;
			} else {
				int valX = Integer.MAX_VALUE;
				int valY = Integer.MAX_VALUE;
				int valZ = Integer.MAX_VALUE;
				try {
					valX = Integer.parseInt(arr[0]);
					valY = Integer.parseInt(arr[1]);
					valZ = Integer.parseInt(arr[2]);
				} catch(NumberFormatException e) {
					this.x = Integer.MAX_VALUE;
					this.y = Integer.MAX_VALUE;
					this.z = Integer.MAX_VALUE;
					return;
				}
				x=valX;
				y=valY;
				z=valZ;
			}
		}
	}
	
	public IntXYZ(JSONObject json) {
		this.x = (int) json.get("x");
		this.y = (int) json.get("y");
		this.z = (int) json.get("z");
	}
	
	@Override
	public String toString() {
		return new StringBuilder("(").append(x).append(",").append(y).append(",").append(z).append(")").toString();
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
		return new IntXYZ(json);
	}

	@Override
	public String getId() {
		return "data|OpenCraft|intXYZ";
	}
}
