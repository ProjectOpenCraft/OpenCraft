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

package opencraft.event.object;

import opencraft.event.ICancelable;
import opencraft.lib.event.IEvent;
import opencraft.world.object.EntityObject;

public class EventObjectDespawn implements IEvent, ICancelable {
	
	private boolean isCanceled = false;
	public final EntityObject object;
	public int lifeTime = 0;
	
	public EventObjectDespawn(EntityObject obj) {
		this.object = obj;
	}

	@Override
	public void setCanceled(boolean cancel) {
		this.isCanceled = cancel;
	}

	@Override
	public boolean isCanceled() {
		return this.isCanceled;
	}

}
