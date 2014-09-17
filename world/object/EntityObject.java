/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage minecraft style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * class EntityObject
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft.world.object;

import java.util.UUID;

import org.json.simple.JSONObject;

import opencraft.lib.INamed;
import opencraft.lib.entity.Entity;
import opencraft.lib.entity.IEntity;
import opencraft.lib.entity.data.DoubleXYZ;
import opencraft.lib.tick.ITickable;
import opencraft.world.EntityWorld;
import opencraft.world.object.render.ObjectRenderInfo;

public abstract class EntityObject extends Entity implements ITickable, INamed {
	
	protected EntityWorld world;
	protected DoubleXYZ coord;
	public final String uuid;
	
	public EntityObject() {
		this.uuid = UUID.randomUUID().toString();
		this.world = null;
		this.coord = null;
	}
	
	public String getUUID() {
		return this.uuid;
	}
	
	public abstract ObjectRenderInfo getRenderInfo();
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJSON(JSONObject json) {
		super.toJSON(json);
		json.put("world", world.toJSON(new JSONObject()));
		json.put("coord", coord.toJSON(new JSONObject()));
		return json;
	}
	
	@Override
	public IEntity fromJSON(JSONObject json) {
		super.fromJSON(json);
		this.world = (EntityWorld) Entity.registry.getEntity((JSONObject) json.get("world"));
		this.coord = (DoubleXYZ) Entity.registry.getEntity((JSONObject) json.get("coord"));
		return this;
	}
}
