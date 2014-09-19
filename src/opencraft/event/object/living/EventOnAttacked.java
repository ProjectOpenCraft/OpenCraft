/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage sandbox style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * class EventOnAttacked
 * 
 * author - Moonrise1275
 * All rights reserved.
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
