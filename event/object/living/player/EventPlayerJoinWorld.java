package opencraft.event.object.living.player;

import opencraft.lib.event.IEvent;
import opencraft.world.EntityWorld;

public class EventPlayerJoinWorld implements IEvent {
	
	public final EntityWorld world;
	
	public EventPlayerJoinWorld(EntityWorld world) {
		this.world = world;
	}
}
