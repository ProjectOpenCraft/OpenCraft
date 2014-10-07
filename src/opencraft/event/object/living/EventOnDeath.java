package opencraft.event.object.living;

import opencraft.event.ICancelable;
import opencraft.lib.event.IEvent;
import opencraft.world.object.living.EntityObjectLiving;

public class EventOnDeath implements IEvent, ICancelable {
	
	private boolean canceled = false;
	
	public EntityObjectLiving living;
	public int lastHealth = 0;
	
	public EventOnDeath(EntityObjectLiving living) {
		this.living = living;
	}

	@Override
	public void setCanceled(boolean cancel) {
		this.canceled = cancel;
	}

	@Override
	public boolean isCanceled() {
		return this.canceled;
	}
}
