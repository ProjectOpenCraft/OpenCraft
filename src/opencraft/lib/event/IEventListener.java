/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage minecraft style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * class IEventListener
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft.lib.event;

public interface IEventListener {
	
	Class<? extends IEvent> getEventClass();
	IEvent handleEvent(IEvent event);

}
