/*
 *OpenCraft - Build your own open world!
 *
 *Author : HyeonuPark(Moonrise1275)
 *
 *--------------------------------------------------------------------------
 *
 *The MIT License (MIT)
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

	@Override
	public String getId() {
		return "entityStorage|OpenCraft|array";
	}
}
