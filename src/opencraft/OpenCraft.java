/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage sandbox style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * class OpenCraft
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft;

import java.io.File;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import opencraft.lib.entity.Entity;
import opencraft.lib.entity.data.Box;
import opencraft.lib.entity.data.DoubleXYZ;
import opencraft.lib.entity.data.IntXYZ;
import opencraft.lib.entity.storage.EntityStorageIntXYZ;
import opencraft.lib.entity.storage.EntityStorageList;
import opencraft.lib.entity.storage.EntityStorageString;
import opencraft.packet.PacketFileStart;
import opencraft.packet.c2s.PacketClientInfo;
import opencraft.packet.c2s.PacketKeyInput;
import opencraft.packet.c2s.PacketPartServer;
import opencraft.packet.c2s.PacketPlayerSight;
import opencraft.packet.c2s.PacketRequestMod;
import opencraft.packet.s2c.PacketBlockMap;
import opencraft.packet.s2c.PacketFullChunk;
import opencraft.packet.s2c.PacketKeyList;
import opencraft.packet.s2c.PacketSendModList;
import opencraft.packet.s2c.PacketUpdateBlock;
import opencraft.packet.s2c.PacketUpdateObject;
import opencraft.packet.s2c.PacketYouDied;
import opencraft.server.OpenCraftServer;
import opencraft.server.client.ClientInfo;
import opencraft.world.chunk.EntityChunk;
import opencraft.world.object.EntityObjectItem;
import opencraft.world.object.ObjectRenderInfo;
import opencraft.world.object.living.player.Player;

public class OpenCraft {
	
	public static final File runDir = new File(".");
	public static final File modDir = new File(runDir, "mods");
	public static final File worldDir = new File(runDir, "worlds");
	public static final File playerDir = new File(worldDir, "players");
	
	public static Logger log;
	
	public static void main(String[] args) {
		BasicConfigurator.configure();
		log = LogManager.getLogger("[OpenCraft]");
		createDir();
		registerEntity();
		OpenCraftServer.instance();
	}
	
	private static void registerEntity() {
		Entity.registry.registerEntity(new IntXYZ());
		Entity.registry.registerEntity(new DoubleXYZ());
		Entity.registry.registerEntity(new Box());
		
		Entity.registry.registerEntity(new EntityStorageString());
		Entity.registry.registerEntity(new EntityStorageList());
		Entity.registry.registerEntity(new EntityStorageIntXYZ());
		
		Entity.registry.registerEntity(new ClientInfo());
		Entity.registry.registerEntity(new ObjectRenderInfo());
		Entity.registry.registerEntity(new Player());
		
		Entity.registry.registerEntity(new EntityChunk());
		Entity.registry.registerEntity(new EntityObjectItem());
		
		Entity.registry.registerEntity(new PacketFileStart());
		Entity.registry.registerEntity(new PacketClientInfo());
		Entity.registry.registerEntity(new PacketKeyInput());
		Entity.registry.registerEntity(new PacketPartServer());
		Entity.registry.registerEntity(new PacketPlayerSight());
		Entity.registry.registerEntity(new PacketRequestMod());
		Entity.registry.registerEntity(new PacketBlockMap());
		Entity.registry.registerEntity(new PacketFullChunk());
		Entity.registry.registerEntity(new PacketKeyList());
		Entity.registry.registerEntity(new PacketSendModList());
		Entity.registry.registerEntity(new PacketUpdateBlock());
		Entity.registry.registerEntity(new PacketUpdateObject());
		Entity.registry.registerEntity(new PacketYouDied());
	}
	
	private static void createDir() {
		modDir.mkdirs();
		playerDir.mkdirs();
		worldDir.mkdirs();
	}
}
