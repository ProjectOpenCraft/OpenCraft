/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage sandbox style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * class EntityObjectLiving
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft.world.object.living;

import org.json.simple.JSONObject;

import opencraft.lib.entity.IEntity;
import opencraft.lib.entity.data.DoubleXYZ;
import opencraft.world.object.EntityObject;

public abstract class EntityObjectLiving extends EntityObject {
	
	protected double headPitch;
	protected double headYaw;
	
	protected int maxHealth;
	protected int curHealth;
	
	public EntityObjectLiving() {
		super();
		this.headPitch = 0d;
		this.headYaw = 0d;
		this.maxHealth = 0;
		this.curHealth = 0;
	}
	
	public EntityObjectLiving(String world, DoubleXYZ coord, String type, double pitch, double yaw, int maxHealth, int curHealth) {
		super(world, coord, type);
		this.headPitch = pitch;
		this.headYaw = yaw;
		this.maxHealth = maxHealth;
		this.curHealth = curHealth;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJSON(JSONObject json) {
		super.toJSON(json);
		json.put("angle", angle);
		json.put("pitch", headPitch);
		json.put("yaw", headYaw);
		json.put("maxH", maxHealth);
		json.put("curH", curHealth);
		return json;
	}
	
	@Override
	public IEntity fromJSON(JSONObject json) {
		super.fromJSON(json);
		this.angle = (double) json.get("angle");
		this.headPitch = (double) json.get("pitch");
		this.headYaw = (double) json.get("yaw");
		this.maxHealth = (int) json.get("maxH");
		this.curHealth = (int) json.get("curH");
		return this;
	}
}
