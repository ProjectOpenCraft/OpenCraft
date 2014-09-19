/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage sandbox style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * class EntityObjectItem
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft.world.object;

import org.json.simple.JSONObject;

import opencraft.event.object.EventObjectDespawn;
import opencraft.item.EntityItem;
import opencraft.lib.entity.Entity;
import opencraft.lib.entity.IEntity;

public class EntityObjectItem extends EntityObject {
	
	private int lifeTime;
	
	public final EntityItem item;
	public double angle;
	public EntityObjectItem() {
		this.item = null;
		this.angle = 0d;
	}
	
	public EntityObjectItem(EntityItem item, double angle) {
		this.item = item;
		this.angle = angle;
		this.lifeTime = 6000;
	}

	@Override
	public String getName() {
		return "obj|" + this.item.getName();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJSON(JSONObject json) {
		super.toJSON(json);
		json.put("item", item.toJSON(new JSONObject()));
		json.put("angle", angle);
		return json;
	}
	
	@Override
	public IEntity fromJSON(JSONObject json) {
		super.fromJSON(json);
		EntityItem item = (EntityItem) Entity.registry.getEntity((JSONObject) json.get("item"));
		double angle = (double) json.get("angle");
		return new EntityObjectItem(item, angle);
		
	}

	@Override
	public void tick() {
		this.lifeTime--;
		if (this.lifeTime < 0) {
			EventObjectDespawn event = (EventObjectDespawn) event().emit(new EventObjectDespawn(this));
			if (!event.isCanceled()) {
				this.lifeTime = event.lifeTime;
			} else {
				
			}
		}
	}

	@Override
	public String getId() {
		return "object|OpenCraft|item";
	}
}
