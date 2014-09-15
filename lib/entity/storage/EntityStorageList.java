/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage minecraft style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * class EntityStorageList
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft.lib.entity.storage;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import opencraft.lib.entity.Entity;
import opencraft.lib.entity.IEntity;

public class EntityStorageList extends EntityStorage {
	
	static {
		ENTITY_ID = "entitystorage|OpenCraft|List";
	}
	
	private List<IEntity> listEntity;
	
	public EntityStorageList() {
		this.listEntity = Collections.synchronizedList(new LinkedList<IEntity>());
	}

	@Override
	public boolean put(Object key, IEntity value) {
		this.listEntity.add(value);
		return true;
	}
	
	public void add(IEntity entity) {
		this.listEntity.add(entity);
	}

	@Override
	public IEntity get(Object key) {
		try {
			int index = Integer.parseInt(key.toString());
			return this.listEntity.get(index);
		} catch(NumberFormatException e) {
			return null;
		}
	}
	
	@Override
	public boolean removeKey(Object key) {
		int index;
		try {
			index = Integer.parseInt(key.toString());
		} catch(NumberFormatException e) {
			return false;
		}
		if (index < 0 || index >= this.listEntity.size()) return false;
		this.listEntity.remove(index);
		return true;
	}

	@Override
	public boolean removeValue(IEntity value) {
		for (IEntity ntt : this.listEntity) {
			if (ntt.equals(value)) {
				this.listEntity.remove(ntt);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public Collection<IEntity> values() {
		return this.listEntity;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJSON(JSONObject json) {
		super.toJSON(json);
		JSONArray arr = new JSONArray();
		for (IEntity ntt : this.listEntity) {
			arr.add(ntt.toJSON(new JSONObject()));
		}
		json.put("array", arr);
		return json;
	}
	
	@Override 
	public IEntity fromJSON(JSONObject json) {
		super.fromJSON(json);
		JSONArray arr = (JSONArray) json.get("array");
		for (Object jntt : arr) {
			IEntity ntt = Entity.registry.getEntity((JSONObject)jntt);
			this.listEntity.add(ntt);
		}
		return this;
	}
}
