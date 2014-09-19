/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage sandbox style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * class EventDecorateChunk
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft.event.world;

import opencraft.lib.event.IEvent;
import opencraft.world.chunk.EntityChunk;

public class EventDecorateChunk implements IEvent {
	
	public EntityChunk chunk;
	
	public EventDecorateChunk(EntityChunk chunk) {
		this.chunk = chunk;
	}
}
