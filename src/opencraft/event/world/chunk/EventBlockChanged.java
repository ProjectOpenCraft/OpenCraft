/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage minecraft style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * class EventBlockChanged
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft.event.world.chunk;

import opencraft.lib.event.IEvent;
import opencraft.world.block.IBlock;

public class EventBlockChanged implements IEvent {
	
	public final IBlock oldBlock;
	public IBlock newBlock;
	
	public EventBlockChanged(IBlock oldBlock, IBlock newBlock) {
		this.oldBlock = oldBlock;
		this.newBlock = newBlock;
	}
}
