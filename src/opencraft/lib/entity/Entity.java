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

package opencraft.lib.entity;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import opencraft.lib.event.EventDispatcher;
import opencraft.lib.event.IEventDispatcher;

public abstract class Entity implements IEntity {
	
	public static Gson gson = new Gson();
	
	private IEventDispatcher ed = new EventDispatcher();
	private Map<Class<? extends IEntity>, IEntity> mapSubEntity = Collections.synchronizedMap(new HashMap<Class<? extends IEntity>, IEntity>());
	
	@Override
	public IEventDispatcher event() {
		return ed;
	}
	
	@Override
	public IEntity copy() {
		return gson.fromJson(gson.toJson(this), this.getClass());
	}
	
	@Override
	public void addSubEntity(IEntity entity) {
		this.mapSubEntity.put(entity.getClass(), entity);
	}
	
	@Override
	public IEntity getSubEntity(Class<? extends IEntity> clazz) {
		return this.mapSubEntity.get(clazz);
	}
}
