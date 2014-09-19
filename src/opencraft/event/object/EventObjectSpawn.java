package opencraft.event.object;

import opencraft.event.ICancelable;
import opencraft.lib.event.IEvent;
import opencraft.world.object.EntityObject;

public class EventObjectSpawn implements IEvent, ICancelable {
	
	private boolean isCanceled = false;
	public EntityObject obj;
	
	public EventObjectSpawn(EntityObject obj) {
		this.obj = obj;
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
