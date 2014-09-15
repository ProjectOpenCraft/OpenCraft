/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage minecraft style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * class EventGenerateChunk
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft.event.world;

import opencraft.lib.event.IEvent;
import opencraft.world.chunk.EntityChunk;

public class EventGenerateChunk implements IEvent {
	
	public EntityChunk chunk;
	
	public EventGenerateChunk(EntityChunk chunk) {
		this.chunk = chunk;
	}
}
