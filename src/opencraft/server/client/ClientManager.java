/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage sandbox style modding.
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

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import opencraft.OpenCraft;
import opencraft.event.object.living.player.EventPlayerPartWorld;
import opencraft.lib.entity.file.EntityLoader;
import opencraft.lib.event.IEvent;
import opencraft.lib.event.IEventListener;
import opencraft.lib.event.packet.Packet;
import opencraft.packet.c2s.PacketClientInfo;
import opencraft.world.object.living.player.Player;

public class ClientManager extends Thread {
	
	Logger log;
	
	private int port;
	List<Client> clientPool = new LinkedList<Client>();
	Map<String, Client> mapClient = new HashMap<String, Client>();
	
	public ClientManager(int port) {
		this.log = OpenCraft.log;
		log.info("Starting ClientManager");
		this.port = port;
	}
	
	Player getPlayer(String id, String secret) {
		for (File file : OpenCraft.playerDir.listFiles()) {
			if (file.getName().equals(id)) {
				Player player = (Player) EntityLoader.loadEntity(file);
				if (player.isMatchingClient(id) && player.isValidClient(secret)) {
					return player;
				}
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
		log.info("ClientManager start listening");
		try {
			ServerSocket ss = new ServerSocket(port);
			while (!this.isInterrupted()) {
				log.info("Waiting client...");
				Client client = new Client(ss.accept(), this);
				log.info("Client detected");
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
		this.mapClient.remove(client.info.clientId);
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
