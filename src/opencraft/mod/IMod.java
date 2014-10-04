package opencraft.mod;

import opencraft.lib.INamed;
import opencraft.lib.event.IEventHandler;

public interface IMod extends IEventHandler, INamed {
	
	int getVersion();
	void init();
}
