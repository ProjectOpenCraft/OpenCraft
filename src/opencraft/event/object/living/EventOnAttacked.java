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


package opencraft.event.object.living;

import opencraft.event.ICancelable;
import opencraft.item.EntityItem;
import opencraft.lib.event.IEvent;
import opencraft.world.object.living.EntityObjectLiving;
import opencraft.world.object.living.IAttacker;

public class EventOnAttacked implements IEvent, ICancelable {
	
	private boolean isCanceled = false;
	
	public final IAttacker attacker;
	public final EntityItem weapon;
	public final EntityObjectLiving living;
	public int damage;

	public EventOnAttacked(EntityObjectLiving obj, IAttacker attacker, EntityItem weapon, EntityObjectLiving living, int damage) {
		this.attacker = attacker;
		this.weapon = weapon;
		this.living = living;
		this.damage = damage;
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
