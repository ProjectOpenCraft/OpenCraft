/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage minecraft style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * class EventOnObjectDespawn
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft.event.object;

import opencraft.event.ICancelable;
import opencraft.lib.event.IEvent;
import opencraft.world.object.EntityObject;

public class EventOnObjectDespawn implements IEvent, ICancelable {
	
	private boolean isCanceled = false;
	public final EntityObject object;
	public int lifeTime = 0;
	
	public EventOnObjectDespawn(EntityObject obj) {
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
