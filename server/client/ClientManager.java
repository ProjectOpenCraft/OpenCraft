/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage minecraft style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * class ClientManager
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft.server.client;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import opencraft.event.object.living.player.EventPlayerPartWorld;
import opencraft.lib.entity.IEntity;
import opencraft.lib.entity.storage.EntityStorageList;
import opencraft.lib.event.IEvent;
import opencraft.lib.event.IEventListener;
import opencraft.lib.event.packet.Packet;
import opencraft.packet.c2s.PacketClientInfo;
import opencraft.world.object.living.player.Player;

public class ClientManager extends Thread {
	
	private int port;
	List<Client> clientPool = new LinkedList<Client>();
	Map<String, Client> mapClient = new HashMap<String, Client>();
	EntityStorageList playerEntityPool = new EntityStorageList();
	
	public ClientManager(int port) {
		this.port = port;
	}
	
	public void registerPlayer(Player player) {
		this.playerEntityPool.add(player);
	}
	
	Player getPlayer(String id, String secret) {
		for (IEntity player : playerEntityPool.values()) {
			if (player instanceof Player && ((Player) player).isMatchingClient(id) && ((Player) player).isValidClient(secret)) {
				return (Player)player;
			}
		}
		return null;
	}
	
	public void sendToAll(Packet packet) {
		for (Client client : mapClient.values()) {
			client.sender.emit(packet);
		}
	}
	
	public void run() {
		try {
			ServerSocket ss = new ServerSocket(port);
			while (!this.isInterrupted()) {
				Client client = new Client(ss.accept(), this);
				this.clientPool.add(client);
				client.receiver.addListener(new ClientInfoListener(client));
			}
			ss.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	void removeClient(Client client) {
		client.player.event().emit(new EventPlayerPartWorld(client.player.getWorld()));
		this.clientPool.remove(client);
		this.mapClient.remove(client.getName());
	}
	
	class ClientInfoListener implements IEventListener {
		
		Client client;
		
		public ClientInfoListener(Client client) {
			this.client = client;
		}

		@Override
		public Class<? extends IEvent> getEventClass() {
			return PacketClientInfo.class;
		}

		@Override
		public IEvent handleEvent(IEvent event) {
			PacketClientInfo packet = (PacketClientInfo)event;
			client.setInfo(packet.info);
			client.join();
			return event;
		}
	}
}
