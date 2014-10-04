/*
 *OpenCraft - Build your own open world!
 *
 *Author : HyeonuPark(Moonrise1275)
 *
 *--------------------------------------------------------------------------
 *
 *The MIT License (MIT)
 *
 *Permission is hereby granted, free of charge, to any person obtaining a copy
 *of this software and associated documentation files (the "Software"), to deal
 *in the Software without restriction, including without limitation the rights
 *to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *copies of the Software, and to permit persons to whom the Software is
 *furnished to do so, subject to the following conditions:
 *
 *The above copyright notice and this permission notice shall be included in
 *all copies or substantial portions of the Software.
 *
 *THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *THE SOFTWARE.
 *
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
import opencraft.lib.event.EventDispatcher;
import opencraft.lib.event.IEvent;
import opencraft.lib.event.IEventDispatcher;
import opencraft.lib.event.IEventHandler;
import opencraft.lib.event.IEventListener;
import opencraft.lib.event.packet.Packet;
import opencraft.packet.c2s.PacketClientInfo;
import opencraft.server.OpenCraftServer;
import opencraft.world.EntityWorld;
import opencraft.world.object.living.player.Player;

public class ClientManager extends Thread implements IEventHandler {
	
	Logger log;
	IEventDispatcher ed = new EventDispatcher();
	
	private int port;
	List<Client> clientPool = new LinkedList<Client>();
	Map<String, Client> mapClient = new HashMap<String, Client>();
	
	public ClientManager(int port) {
		this.setName("ClientManager");
		this.log = OpenCraft.log;
		log.info("Starting ClientManager");
		this.port = port;
	}
	
	public IEventDispatcher event() {
		return this.ed;
	}
	
	Player getPlayer(ClientInfo info) {
		for (File file : OpenCraft.playerDir.listFiles()) {
			if (file.getName().equals(info.clientId)) {
				Player player = (Player) EntityLoader.loadEntity(file);
				if (player.isMatchingClient(info.clientId)) {
					if (player.isValidClient(info.clientSecret)) {
						return player;
					} else return null;
				}
			}
		}
		EntityWorld world = OpenCraftServer.instance().getWorldManager().getWorld();
		Player player = new Player(world.getName(), world.getSpawnPoint(), info);
		return player;
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
				log.debug("Waiting client...");
				Client client = new Client(ss.accept(), this);
				log.debug("Client detected");
				this.clientPool.add(client);
				client.receiver.addListener(new ClientInfoListener(client));
			}
			ss.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	void removeClient(Client client) {
		log.info(client.getName() + " is disconnected");
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
