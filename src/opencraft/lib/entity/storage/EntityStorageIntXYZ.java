/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage minecraft style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * class EntityStorageIntXYZ
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
import opencraft.lib.entity.data.IntXYZ;

import org.json.simple.JSONObject;

public class EntityStorageIntXYZ extends EntityStorage {
	
	static {
		ENTITY_ID = "entitystorage|OpenCraft|IntXYZ";
	}
	
	Map<IntXYZ, IEntity> mapEntity;
	
	public EntityStorageIntXYZ() {
		this.mapEntity = Collections.synchronizedMap(new HashMap<IntXYZ, IEntity>());
	}

	@Override
	public boolean put(Object key, IEntity value) {
		if (!(key instanceof IntXYZ)) return false;
		this.mapEntity.put((IntXYZ) key, value);
		return true;
	}

	@Override
	public IEntity get(Object key) {
		if (!(key instanceof IntXYZ)) return null;
		return this.mapEntity.get(key);
	}

	@Override
	@SuppressWarnings("unchecked")
	public JSONObject toJSON(JSONObject json) {
		super.toJSON(json);
		for (IntXYZ key : this.mapEntity.keySet()) {
			String name = key.toString();
			JSONObject j = this.mapEntity.get(key).toJSON(new JSONObject());
			json.put(name, j);
		}
		return json;
	}

	@Override
	public IEntity fromJSON(JSONObject json) {
		super.fromJSON(json);
		for (Object key : json.keySet()) {
			if (!(key instanceof String)) key = key.toString();
			this.mapEntity.put(new IntXYZ((String) key), Entity.registry.getEntity((JSONObject) json.get(key)));
		}
		return this;
	}

	@Override
	public Collection<IEntity> values() {
		return this.mapEntity.values();
	}
	
	@Override
	public boolean removeKey(Object key) {
		if (!(key instanceof IntXYZ)) return false;
		this.mapEntity.remove(key);
		return true;
	}

	@Override
	public boolean removeValue(IEntity value) {
		for (IntXYZ key : this.mapEntity.keySet()) {
			if (this.mapEntity.get(key).equals(value)) {
				this.mapEntity.remove(key);
				return true;
			}
		}
		return false;
	}

}
