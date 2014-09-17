package opencraft.mod;

import opencraft.lib.event.IEventHandler;

public interface IMod extends IEventHandler {
	
	String getName();
	int getVersion();
}
