package opencraft.event.object.living.player;

import opencraft.lib.event.IEvent;
import opencraft.world.EntityWorld;

public class EventPlayerJoinWorld implements IEvent {
	
	public EntityWorld world;
	
	public EventPlayerJoinWorld(EntityWorld world) {
		this.world = world;
	}
}
