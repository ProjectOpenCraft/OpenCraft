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

package opencraft.lib.client;

import java.io.IOException;
import java.net.Socket;

import opencraft.lib.INamed;
import opencraft.lib.entity.Entity;
import opencraft.lib.event.packet.PacketReceiver;
import opencraft.lib.event.packet.PacketSender;
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
			this.receiver = new PacketReceiver(Entity.registry, socket.getInputStream());
			this.sender = new PacketSender(socket.getOutputStream());
			
			
		} catch(IOException e) {
			soc.close();
			manager.removeClient(this);
			e.printStackTrace();
		}
	}
	
	void join() {
		this.player = manager.getPlayer(info.clientId, info.clientSecret);
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
}
