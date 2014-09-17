/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage minecraft style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * class EntityRegistry
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft.lib.entity;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

public class EntityRegistry implements IEntityRegistry {
	
	public static final String ENTITY_ID = "entityId";
	
	private Map<String, Class<? extends IEntity>> mapEntity;
	
	public EntityRegistry() {
		this.mapEntity = Collections.synchronizedMap(new HashMap<String, Class<? extends IEntity>>());
	}

	@Override
	public void registerEntity(IEntity entity) {
		this.mapEntity.put(entity.getId(), entity.getClass());
	}

	@Override
	public IEntity getEntity(JSONObject json) {
		String id = (String) json.get(ENTITY_ID);
		if (this.mapEntity.get(id) == null) return null;
		IEntity entity = null;
		try {
			entity = this.mapEntity.get(id).newInstance().fromJSON(json);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return entity;
	}

	@Override
	public Class<? extends IEntity> getClass(String id) {
		return this.mapEntity.get(id);
	}
}
