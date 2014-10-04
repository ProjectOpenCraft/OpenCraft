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
