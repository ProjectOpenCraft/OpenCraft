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
import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import opencraft.lib.entity.Entity;
import opencraft.lib.entity.IEntity;

public class EntityStorageList extends EntityStorage {
	
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

	@Override
	public String getId() {
		return "entityStorage|OpenCraft|list";
	}
}
