/*
 *OpenCraft - Build your own open world!
 *
 *Author : HyeonuPark(Moonrise1275)
 *
 *--------------------------------------------------------------------------
 *
 *The MIT License (MIT)
 *
 *Copyright (c) <year> <copyright holders>
 *
 *Permission is hereby granted, free of charge, to any person obtaining a copy
 *of this software and associated documentation files (the "Software"), to deal
 *in the Software without restriction, including without limitation the rights
 *to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *copies of the Software, and to permit persons to whom the Software is
 *furnished to do so, subject to the following conditions:
 *
 *The above copyright notice and this permission notice shall be included in
 *all copies or substantial portions of the Software.
 *
 *THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *THE SOFTWARE.
 *
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

	@Override
	public String getId() {
		return "entityStorage|OpenCraft|intXYZ";
	}

}
