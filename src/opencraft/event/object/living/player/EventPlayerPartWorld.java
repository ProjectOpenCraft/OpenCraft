package opencraft.event.object.living.player;

import opencraft.lib.event.IEvent;
import opencraft.world.EntityWorld;

public class EventPlayerPartWorld implements IEvent {
	
	public EntityWorld world;
	
	public EventPlayerPartWorld(EntityWorld world) {
		this.world = world;
	}
}
