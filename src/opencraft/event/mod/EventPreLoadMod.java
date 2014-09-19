/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage sandbox style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * class EventPreLoadMod
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft.event.mod;

import java.util.List;

import opencraft.lib.event.IEvent;

public class EventPreLoadMod implements IEvent {
	
	public final List<String> listMod;
	
	public EventPreLoadMod(List<String> list) {
		this.listMod = list;
	}
}
