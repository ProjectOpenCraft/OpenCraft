/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage minecraft style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * class Entity
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft.lib.entity;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import opencraft.lib.event.EventDispatcher;
import opencraft.lib.event.IEventDispatcher;

import org.json.simple.JSONObject;

public abstract class Entity implements IEntity {
	
	public static IEntityRegistry registry = new EntityRegistry();
	
	private IEventDispatcher ed = new EventDispatcher();
	private Map<Class<? extends IEntity>, IEntity> mapSubEntity = Collections.synchronizedMap(new HashMap<Class<? extends IEntity>, IEntity>());
	
	@Override
	public IEventDispatcher event() {
		return ed;
	}
	
	@Override
	public void addSubEntity(IEntity entity) {
		this.mapSubEntity.put(entity.getClass(), entity);
	}
	
	@Override
	public IEntity getSubEntity(Class<? extends IEntity> clazz) {
		return this.mapSubEntity.get(clazz);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public JSONObject toJSON(JSONObject json) {
		json.put(EntityRegistry.ENTITY_ID, getId());
		return json;
	}
	
	@Override
	public IEntity fromJSON(JSONObject json) {
		json.remove(EntityRegistry.ENTITY_ID);
		return this;
	}
}
