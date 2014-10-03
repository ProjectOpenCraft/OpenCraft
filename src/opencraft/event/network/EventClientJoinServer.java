package opencraft.event.network;

import opencraft.lib.event.IEvent;
import opencraft.server.client.Client;

public class EventClientJoinServer implements IEvent {
	
	public Client client;
	
	public EventClientJoinServer(Client client) {
		this.client = client;
	}
}
