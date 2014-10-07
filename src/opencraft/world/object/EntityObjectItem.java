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

package opencraft.world.object;

import opencraft.event.object.EventObjectDespawn;
import opencraft.item.EntityItem;

public class EntityObjectItem extends EntityObject {
	
	private int lifeTime;
	
	public final EntityItem item;
	public double angle;
	public EntityObjectItem() {
		this.item = null;
		this.angle = 0d;
	}
	
	public EntityObjectItem(EntityItem item, double angle) {
		this.item = item;
		this.angle = angle;
		this.lifeTime = 6000;
	}

	@Override
	public String getName() {
		return "obj|" + this.item.getName();
	}

	@Override
	public void tick() {
		this.lifeTime--;
		if (this.lifeTime < 0) {
			EventObjectDespawn event = (EventObjectDespawn) event().emit(new EventObjectDespawn(this));
			if (!event.isCanceled()) {
				this.lifeTime = event.lifeTime;
			} else {
				
			}
		}
	}
}
