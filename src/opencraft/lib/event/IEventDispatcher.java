/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage sandbox style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * interface IEventDispatcher
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft.lib.event;

public interface IEventDispatcher {
	
	boolean addListener(IEventListener listener);
	boolean removeListener(IEventListener listener);
	IEvent emit(IEvent event);
	
}
