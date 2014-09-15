package opencraft.event.world;

import opencraft.lib.event.IEvent;
import opencraft.world.chunk.EntityChunk;

public class EventDecorateChunk implements IEvent {
	
	public EntityChunk chunk;
	
	public EventDecorateChunk(EntityChunk chunk) {
		this.chunk = chunk;
	}
}
