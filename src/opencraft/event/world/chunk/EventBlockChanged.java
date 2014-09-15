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
