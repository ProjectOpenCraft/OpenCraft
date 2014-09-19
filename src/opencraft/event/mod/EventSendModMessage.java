/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage sandbox style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * class EventSendModMessage
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft.event.mod;

import opencraft.lib.event.IEvent;

public class EventSendModMessage implements IEvent {
	
	public String target;
	public String name;
	public Object cargo;
	
	public EventSendModMessage(String target, String name, Object cargo) {
		this.target = target;
		this.name = name;
		this.cargo = cargo;
	}
}
