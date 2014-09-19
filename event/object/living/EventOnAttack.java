/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage minecraft style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * class EventOnAttack
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft.event.object.living;

import opencraft.event.ICancelable;
import opencraft.item.EntityItem;
import opencraft.lib.event.IEvent;
import opencraft.world.object.living.EntityObjectLiving;
import opencraft.world.object.living.player.Player;

public class EventOnAttack implements IEvent, ICancelable {
	
	private boolean isCanceled = false;
	
	public final Player player;
	public final EntityItem weapon;
	public final EntityObjectLiving target;
	public int damage;
	
	public EventOnAttack(Player player, EntityItem weapon, EntityObjectLiving target, int damage) {
		this.player = player;
		this.weapon = weapon;
		this.target = target;
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
