/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage minecraft style modding.
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

import opencraft.lib.INamed;
import opencraft.lib.event.IEvent;
import opencraft.lib.event.IEventListener;
import opencraft.lib.event.packet.PacketReceiver;
import opencraft.lib.event.packet.PacketSender;
import opencraft.packet.PacketFileEnd;
import opencraft.packet.PacketFileStart;
import opencraft.packet.c2s.PacketKeyInput;
import opencraft.packet.c2s.PacketPlayerSight;
import opencraft.world.object.living.player.Player;

public class Client implements INamed {
	
	ClientManager manager;
	ClientInfo info;
	
	Socket socket;
	PacketReceiver receiver = null;
	PacketSender sender = null;
	
	Player player = null;;
	
	public Client(Socket soc, ClientManager manager) throws IOException {
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
		this.player = manager.getPlayer(info.clientId, info.clientSecret);
		if (this.player == null) return;
		player.setClient(this);
		
		this.receiver.addListener(new KeyInputListener(player));
		this.receiver.addListener(new PlayerSightListener(player));
	}
	
	public static void sendFile(PacketSender sender, File file, String path) {
		if (!file.exists() || sender == null) return;
		long length = file.length();
		sender.emit(new PacketFileStart(path, length));
		
		//TODO send mod's assets by nio
		
		sender.emit(new PacketFileEnd(path));
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
}
