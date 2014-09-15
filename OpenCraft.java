/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage minecraft style modding.
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

import opencraft.lib.entity.Entity;
import opencraft.lib.entity.data.Box;
import opencraft.lib.entity.data.DoubleXYZ;
import opencraft.lib.entity.data.IntXYZ;
import opencraft.lib.entity.storage.EntityStorageIntXYZ;
import opencraft.lib.entity.storage.EntityStorageList;
import opencraft.lib.entity.storage.EntityStorageString;
import opencraft.world.chunk.EntityChunk;

public class OpenCraft {
	
	public static final File runDir = new File(".");
	public static final File modDir = new File(runDir, "mods");
	public static final File worldDir = new File(runDir, "worlds");
	public static final File playerDir = new File(worldDir, "players");
	
	public static void load() {
		registerEntity();
	}
	
	private static void registerEntity() {
		Entity.registry.registerEntity(new IntXYZ());
		Entity.registry.registerEntity(new DoubleXYZ());
		Entity.registry.registerEntity(new Box());
		
		Entity.registry.registerEntity(new EntityStorageString());
		Entity.registry.registerEntity(new EntityStorageList());
		Entity.registry.registerEntity(new EntityStorageIntXYZ());
		
		Entity.registry.registerEntity(new EntityChunk());
	}
}
