/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage sandbox style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * class Client
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft.server.client;

import java.io.File;
import java.io.IOException;
import java.net.Socket;

import org.apache.log4j.Logger;

import opencraft.OpenCraft;
import opencraft.event.object.EventObjectDespawn;
import opencraft.event.object.EventObjectSpawn;
import opencraft.event.object.living.player.EventPlayerJoinWorld;
import opencraft.event.object.living.player.EventPlayerPartWorld;
import opencraft.lib.INamed;
import opencraft.lib.entity.data.IntXYZ;
import opencraft.lib.event.IEvent;
import opencraft.lib.event.IEventListener;
import opencraft.lib.event.packet.PacketReceiver;
import opencraft.lib.event.packet.PacketSender;
import opencraft.packet.PacketFileStart;
import opencraft.packet.c2s.PacketKeyInput;
import opencraft.packet.c2s.PacketPartServer;
import opencraft.packet.c2s.PacketPlayerSight;
import opencraft.server.OpenCraftServer;
import opencraft.world.EntityWorld;
import opencraft.world.chunk.ChunkManager;
import opencraft.world.object.living.player.Player;

public class Client implements INamed {
	
	Logger log;
	
	ClientManager manager;
	ClientInfo info;
	
	Socket socket;
	PacketReceiver receiver = null;
	PacketSender sender = null;
	
	Player player = null;;
	
	public Client(Socket soc, ClientManager manager) throws IOException {
		this.log = OpenCraft.log;
		
		this.socket = soc;
		this.manager = manager;
		try {
			this.receiver = new PacketReceiver(socket.getInputStream());
			this.sender = new PacketSender(socket.getOutputStream());
			
		} catch(IOException e) {
			soc.close();
			manager.removeClient(this);
			e.printStackTrace();
		}
	}
	
	void join() {
		
		log.info(this.info.name + " join the game");
		
		this.player = manager.getPlayer(info);
		if (this.player == null) return;
		player.setClient(this);
		
		IntXYZ chunkAddr = new IntXYZ(((Double)player.getCoord().x).intValue(), ((Double)player.getCoord().y).intValue(), ((Double)player.getCoord().z).intValue());
		
		manager.mapClient.put(info.name, this);
		manager.clientPool.remove(this);
		
		OpenCraftServer.instance().getTickManager().addTick(player);
		player = (Player) ((EventObjectSpawn) player.getWorld().event().emit(new EventObjectSpawn(player))).obj;
		player.getWorld().getChunkManager().forceLoadChunk(chunkAddr).addObject(player);
		player.event().emit(new EventPlayerJoinWorld(player.getWorld()));
		
		this.receiver.addListener(new KeyInputListener(player));
		this.receiver.addListener(new PlayerSightListener(player));
		this.receiver.addListener(new PartServerListener(this));
	}
	
	void part() {
		log.info(this.info.name + " part the game");
		
		this.player.event().emit(new EventPlayerPartWorld(this.player.getWorld()));
		player.getChunk().removeObject(player);
		player.getWorld().event().emit(new EventObjectDespawn(player));
		OpenCraftServer.instance().getTickManager().removeTick(player);
		this.receiver.stop();
		try {
			this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		manager.removeClient(this);
		
	}
	
	public static void sendFile(PacketSender sender, File file, String path) {
		if (!file.exists() || sender == null) return;
		long length = file.length();
		sender.emit(new PacketFileStart(file, path, length));
	}
	
	public PacketReceiver receiver() {
		return receiver;
	}
	
	public PacketSender sender() {
		return sender;
	}
	
	void setInfo(ClientInfo info) {
		this.info = info;
	}
	
	public String getName() {
		return info.name;
	}
	
	String getSkin() {
		return info.skinAddress;
	}
	
	class KeyInputListener implements IEventListener {
		
		Player player;
		
		public KeyInputListener(Player p) {
			this.player = p;
		}

		@Override
		public Class<? extends IEvent> getEventClass() {
			return PacketKeyInput.class;
		}

		@Override
		public IEvent handleEvent(IEvent event) {
			player.event().emit(event);
			return event;
		}
	}
	
	class PlayerSightListener implements IEventListener {
		
		Player player;
		
		public PlayerSightListener(Player p) {
			this.player = p;
		}

		@Override
		public Class<? extends IEvent> getEventClass() {
			return PacketPlayerSight.class;
		}

		@Override
		public IEvent handleEvent(IEvent event) {
			player.event().emit(event);
			return event;
		}
	}
	
	class PartServerListener implements IEventListener {
		
		Client client;
		
		public PartServerListener(Client c) {
			this.client = c;
		}

		@Override
		public Class<? extends IEvent> getEventClass() {
			return PacketPartServer.class;
		}

		@Override
		public IEvent handleEvent(IEvent event) {
			client.part();
			return event;
		}
	}
}
