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
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import opencraft.OpenCraft;
import opencraft.event.network.EventClientJoinServer;
import opencraft.event.network.EventClientPartServer;
import opencraft.event.object.EventObjectDespawn;
import opencraft.event.object.EventObjectSpawn;
import opencraft.event.object.living.player.EventPlayerJoinWorld;
import opencraft.event.object.living.player.EventPlayerPartWorld;
import opencraft.lib.INamed;
import opencraft.lib.entity.file.EntityLoader;
import opencraft.lib.event.EnumEventOrder;
import opencraft.lib.event.IEvent;
import opencraft.lib.event.IEventListener;
import opencraft.lib.event.packet.PacketReceiver;
import opencraft.lib.event.packet.PacketSender;
import opencraft.packet.PacketFileStart;
import opencraft.packet.c2s.PacketKeyInput;
import opencraft.packet.c2s.PacketPartServer;
import opencraft.packet.c2s.PacketPlayerSight;
import opencraft.packet.s2c.PacketUpdateGui;
import opencraft.packet.s2c.PacketUpdateHud;
import opencraft.server.OpenCraftServer;
import opencraft.world.chunk.ChunkAddress;
import opencraft.world.object.living.player.Player;

public class Client implements INamed {
	
	Logger log;
	
	ClientManager manager;
	ClientInfo info;
	
	Socket socket;
	PacketReceiver receiver = null;
	PacketSender sender = null;
	
	Player player = null;
	
	Ocan gui = null;
	Set<Ocan> huds = Collections.synchronizedSet(new HashSet<Ocan>());
	
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
		
		ChunkAddress chunkAddr = new ChunkAddress(this.getName(), player.getCoord().multiply(1d/32d).toInt());//new IntXYZ(((Double)Math.floor(player.getCoord().x /32)).intValue(), ((Double)Math.floor(player.getCoord().y /32)).intValue(), ((Double)Math.floor(player.getCoord().z /32)).intValue()));
		
		manager.mapClient.put(info.name, this);
		manager.clientPool.remove(this);
		
		manager.event().emit(new EventClientJoinServer(this));
		
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
		
		manager.event().emit(new EventClientPartServer(this));
		
		try {
			EntityLoader.saveEntity(player, new File(OpenCraft.playerDir, player.getUUID()));
			log.info("Saved player informations");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		this.receiver.stop();
		try {
			this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		manager.removeClient(this);
		
	}
	
	public Ocan getGUI() {
		return this.gui;
	}
	
	public void setGUI(Ocan gui) {
		this.gui = gui;
		this.sender.emit(new PacketUpdateGui(gui));
	}
	
	public Ocan getHUD(String mod, String name) {
		for (Ocan hud : this.huds) {
			if (hud.mod.equals(mod) && hud.name.equals(name)) {
				return hud;
			}
		}
		return null;
	}
	
	public void addHUD(Ocan hud) {
		this.huds.add(hud);
		this.sender.emit(new PacketUpdateHud(hud));
	}
	
	public Ocan removeHUD(String mod, String name) {
		Ocan result = null;
		for (Ocan hud : this.huds) {
			if (hud.mod.equals(mod) && hud.name.equals(name)) {
				result = hud;
			}
		}
		if (result != null) this.huds.remove(result);
		return result;
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

		@Override
		public EnumEventOrder getOrder() {
			return EnumEventOrder.lowest;
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

		@Override
		public EnumEventOrder getOrder() {
			return EnumEventOrder.lowest;
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

		@Override
		public EnumEventOrder getOrder() {
			return EnumEventOrder.lowest;
		}
	}
}
