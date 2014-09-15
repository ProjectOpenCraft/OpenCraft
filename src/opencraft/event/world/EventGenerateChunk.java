package opencraft.event.world;

import opencraft.lib.event.IEvent;
import opencraft.world.chunk.EntityChunk;

public class EventGenerateChunk implements IEvent {
	
	public EntityChunk chunk;
	
	public EventGenerateChunk(EntityChunk chunk) {
		this.chunk = chunk;
	}
}
