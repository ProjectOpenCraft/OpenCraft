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

package opencraft.world.object.living;

import org.json.simple.JSONObject;

import opencraft.lib.entity.IEntity;
import opencraft.lib.entity.data.DoubleXYZ;
import opencraft.world.object.EntityObject;

public abstract class EntityObjectLiving extends EntityObject {
	
	protected double headPitch;
	protected double headYaw;
	
	protected int maxHealth;
	protected int curHealth;
	
	public EntityObjectLiving() {
		super();
		this.headPitch = 0d;
		this.headYaw = 0d;
		this.maxHealth = 0;
		this.curHealth = 0;
	}
	
	public EntityObjectLiving(String world, DoubleXYZ coord, String type, double pitch, double yaw, int maxHealth, int curHealth) {
		super(world, coord, type);
		this.headPitch = pitch;
		this.headYaw = yaw;
		this.maxHealth = maxHealth;
		this.curHealth = curHealth;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJSON(JSONObject json) {
		super.toJSON(json);
		json.put("angle", angle);
		json.put("pitch", headPitch);
		json.put("yaw", headYaw);
		json.put("maxH", maxHealth);
		json.put("curH", curHealth);
		return json;
	}
	
	@Override
	public IEntity fromJSON(JSONObject json) {
		super.fromJSON(json);
		this.angle = (double) json.get("angle");
		this.headPitch = (double) json.get("pitch");
		this.headYaw = (double) json.get("yaw");
		this.maxHealth = (int) json.get("maxH");
		this.curHealth = (int) json.get("curH");
		return this;
	}
}
