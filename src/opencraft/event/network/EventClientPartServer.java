package opencraft.event.network;

import opencraft.lib.event.IEvent;
import opencraft.server.client.Client;

public class EventClientPartServer implements IEvent {
	
	public Client client;
	
	public EventClientPartServer(Client client) {
		this.client = client;
	}
}
