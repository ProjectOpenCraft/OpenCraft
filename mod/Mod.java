/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage minecraft style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * class Mod
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft.mod;

import opencraft.lib.event.EventDispatcher;
import opencraft.lib.event.IEventDispatcher;
import opencraft.lib.event.IEventHandler;

public abstract class Mod implements IEventHandler {
	
	
	
	private IEventDispatcher ed = new EventDispatcher();

	@Override
	public IEventDispatcher event() {
		return this.ed;
	}
}
