/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage sandbox style modding.
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

public abstract class Mod implements IMod {
	
	private IEventDispatcher ed = new EventDispatcher();

	@Override
	public IEventDispatcher event() {
		return this.ed;
	}
}
