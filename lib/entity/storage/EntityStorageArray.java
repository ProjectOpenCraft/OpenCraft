/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage minecraft style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * class EntityStorageArray
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft.lib.entity.storage;

import java.util.ArrayList;
import java.util.Collection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import opencraft.lib.entity.Entity;
import opencraft.lib.entity.IEntity;

public class EntityStorageArray extends EntityStorage {
	
	private IEntity[] arrayEntity;
	
	public EntityStorageArray() {
		this.arrayEntity = new IEntity[0];
	}
	
	public EntityStorageArray(int size) {
		this.arrayEntity = new IEntity[size];
	}

	@Override
	public boolean put(Object key, IEntity value) {
		int index = 0;
		try {
			index = Integer.parseInt(key.toString());
		} catch(NumberFormatException e) {
			return false;
		}
		if (index < 0 || index >= this.arrayEntity.length) return false;
		this.arrayEntity[index] = value;
		return true;
	}

	@Override
	public IEntity get(Object key) {
		int index = 0;
		try {
			index = Integer.parseInt(key.toString());
		} catch(NumberFormatException e) {
			return null;
		}
		if (index < 0 || index >= this.arrayEntity.length) return null;
		return this.arrayEntity[index];
	}
	
	@Override
	public boolean removeKey(Object key) {
		int index = 0;
		try {
			index = Integer.parseInt(key.toString());
		} catch(NumberFormatException e) {
			return false;
		}
		if (index < 0 || index > this.arrayEntity.length -1) return false;
		this.arrayEntity[index] = null;
		return true;
	}

	@Override
	public boolean removeValue(IEntity value) {
		for (int i=0;i<this.arrayEntity.length;i++) {
			if (this.arrayEntity[i].equals(value)) {
				this.arrayEntity[i] = null;
				return true;
			}
		}
		return false;
	}

	@Override
	public Collection<IEntity> values() {
		ArrayList<IEntity> result = new ArrayList<IEntity>(this.arrayEntity.length);
		for (int i=0;i<result.size();i++) {
			result.add(i, this.arrayEntity[i]);
		}
		return result;
	}
	
	public int size() {
		return this.arrayEntity.length;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJSON(JSONObject json) {
		super.toJSON(json);
		JSONArray arr = new JSONArray();
		for (int i=0;i<this.arrayEntity.length;i++) {
			arr.add(i, this.arrayEntity[i].toJSON(new JSONObject()));
		}
		json.put("array", arr);
		return json;
	}
	
	@Override
	public IEntity fromJSON(JSONObject json) {
		super.fromJSON(json);
		JSONArray arr = (JSONArray) json.get("array");
		this.arrayEntity = new IEntity[arr.size()];
		for (int i=0;i<arr.size();i++) {
			this.arrayEntity[i] = Entity.registry.getEntity((JSONObject) arr.get(i));
		}
		return this;
	}
}
