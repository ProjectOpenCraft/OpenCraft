/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage minecraft style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * class EntityStorageString
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft.lib.entity.storage;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import opencraft.lib.entity.Entity;
import opencraft.lib.entity.IEntity;

import org.json.simple.JSONObject;

public class EntityStorageString extends EntityStorage {
	
	private Map<String, IEntity> mapEntity;
	
	public EntityStorageString() {
		this.mapEntity = Collections.synchronizedMap(new HashMap<String, IEntity>());
	}

	@Override
	@SuppressWarnings("unchecked")
	public JSONObject toJSON(JSONObject json) {
		for (String key : mapEntity.keySet()) {
			json.put(key, mapEntity.get(key).toJSON(new JSONObject()));
		}
		return json;
	}

	@Override
	public IEntity fromJSON(JSONObject json) {
		for (Object key : json.keySet()) {
			this.mapEntity.put((String) key, Entity.registry.getEntity((JSONObject)json.get(key)));
		}
		return this;
	}

	@Override
	public boolean put(Object key, IEntity value) {
		if (!(key instanceof String)) return false;
		this.mapEntity.put((String) key, value);
		return true;
	}

	@Override
	public IEntity get(Object key) {
		if (!(key instanceof String)) return null;
		return this.mapEntity.get(key);
	}

	@Override
	public Collection<IEntity> values() {
		return this.mapEntity.values();
	}
	
	@Override
	public boolean removeKey(Object key) {
		if (!(key instanceof String)) return false;
		this.mapEntity.remove(key);
		return true;
	}

	@Override
	public boolean removeValue(IEntity value) {
		for (Object key : this.mapEntity.keySet()) {
			if (this.mapEntity.get(key).equals(value)) {
				this.mapEntity.remove(key);
				return true;
			}
		}
		return false;
	}

	@Override
	public String getId() {
		return "entityStoraeg|OpenCraft|string";
	}
}
