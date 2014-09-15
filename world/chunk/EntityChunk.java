/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage minecraft style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * class EntityChunk
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft.world.chunk;

import java.util.Random;

import org.json.simple.JSONObject;

import opencraft.event.world.chunk.EventOnChunkLoad;
import opencraft.event.world.chunk.EventOnChunkUnload;
import opencraft.lib.entity.Entity;
import opencraft.lib.entity.IEntity;
import opencraft.lib.entity.data.IntXYZ;
import opencraft.lib.entity.storage.EntityStorageIntXYZ;
import opencraft.lib.entity.storage.EntityStorageList;
import opencraft.lib.entity.storage.IEntityStorage;
import opencraft.lib.event.IEvent;
import opencraft.lib.event.IEventListener;
import opencraft.lib.tick.ITickable;
import opencraft.lib.tick.TickManager;
import opencraft.server.OpenCraftServer;
import opencraft.world.block.Block;
import opencraft.world.block.IBlock;
import opencraft.world.block.entity.EntityBlock;
import opencraft.world.object.EntityObject;

public class EntityChunk extends Entity implements ITickable {
	
	static {
		ENTITY_ID = "world|OpenCraft|entityChunk";
	}
	
	String world = "";
	IntXYZ coord = new IntXYZ((JSONObject)null);
	char[][][] block = new char[32][32][32];
	EntityStorageIntXYZ entityBlocks = new EntityStorageIntXYZ();
	EntityStorageList entityObjects = new EntityStorageList();
	
	public EntityChunk() {
		super();
		event().addListener(new EventListenerOnChunkLoad(this, entityBlocks, entityObjects));
		event().addListener(new EventListenerOnChunkUnload(this, entityBlocks, entityObjects));
	}
	
	public EntityChunk(String world, IntXYZ coord) {
		this();
		this.world = world;
		this.coord = coord;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public JSONObject toJSON(JSONObject json) {
		super.toJSON(json);
		json.put("w", world);
		json.put("co", coord.toJSON(new JSONObject()));
		json.put("b", encode(block));
		json.put("eb", entityBlocks.toJSON(new JSONObject()));
		json.put("eo", entityObjects.toJSON(new JSONObject()));
		return json;
	}
	
	@Override
	public IEntity fromJSON(JSONObject json) {
		super.fromJSON(json);
		this.world = (String) json.get("w");
		this.coord = (IntXYZ) Entity.registry.getEntity((JSONObject) json.get("co"));
		this.block = decode((String) json.get("b"));
		this.entityBlocks = (EntityStorageIntXYZ) Entity.registry.getEntity((JSONObject) json.get("eb"));
		this.entityObjects = (EntityStorageList) Entity.registry.getEntity((JSONObject) json.get("eo"));
		return this;
	}
	
	public static String encode(char[][][] block) {
		StringBuilder sb = new StringBuilder();
		for (int i=0;i<block.length;i++) {
			for (int j=0;j<block[i].length;j++) {
				for (int k=0;k<block[i][j].length;k++) {
					sb.append(block[i][j][k]);
				}
			}
		}
		return sb.toString();
	}
	
	public static char[][][] decode(String str) {
		if (str.length() < 32*32*32) return null;
		char[][][] result = new char[32][32][32];
		int index = 0;
		for (int i=0;i<32;i++) {
			for (int j=0;j<32;j++) {
				for (int k=0;k<32;k++) {
					result[i][j][k] = str.charAt(index++);
				}
			}
		}
		return result;
	}

	@Override
	public void tick() {
		Random ran = new Random();
		int x = ran.nextInt(32);
		int y = ran.nextInt(32);
		int z = ran.nextInt(32);
		IBlock b = Block.registry.getBlock(this.block[x][y][z]);
		b.onChunkTick(OpenCraftServer.instance().getWorldManager().getWorld(world), x + (this.coord.x * 32), y + (this.coord.y * 32), z + (this.coord.z * 32));
	}
	
	class EventListenerOnChunkLoad implements IEventListener {
		
		private EntityChunk chunk;
		private IEntityStorage entityBlocks;
		private IEntityStorage entityObjects;
		
		public EventListenerOnChunkLoad(EntityChunk chunk, IEntityStorage blocks, IEntityStorage objects) {
			this.chunk = chunk;
			this.entityBlocks = blocks;
			this.entityObjects = objects;
		}

		@Override
		public Class<? extends IEvent> getEventClass() {
			return EventOnChunkLoad.class;
		}

		@Override
		public IEvent handleEvent(IEvent event) {
			TickManager ticks = OpenCraftServer.instance().getTickManager();
			ticks.addTick(chunk);
			for (IEntity entityBlock : entityBlocks.values()) {
				entityBlock.event().emit(event);
				ticks.addTick((EntityBlock)entityBlock);
			}
			for (IEntity entityObject : entityObjects.values()) {
				entityObject.event().emit(event);
				ticks.addTick((EntityObject)entityObject);
			}
			return event;
		}
		
	}
	
	class EventListenerOnChunkUnload implements IEventListener {
		
		private EntityChunk chunk;
		private IEntityStorage entityBlocks;
		private IEntityStorage entityObjects;
		
		public EventListenerOnChunkUnload(EntityChunk chunk, IEntityStorage blocks, IEntityStorage objects) {
			this.chunk = chunk;
			this.entityBlocks = blocks;
			this.entityObjects = objects;
		}

		@Override
		public Class<? extends IEvent> getEventClass() {
			return EventOnChunkUnload.class;
		}

		@Override
		public IEvent handleEvent(IEvent event) {
			TickManager ticks = OpenCraftServer.instance().getTickManager();
			ticks.removeTick(chunk);
			for (IEntity entityBlock : entityBlocks.values()) {
				entityBlock.event().emit(event);
				ticks.removeTick((EntityBlock)entityBlock);
			}
			for (IEntity entityObject : entityObjects.values()) {
				entityObject.event().emit(event);
				ticks.removeTick((EntityObject)entityObject);
			}
			return event;
		}
		
	}
}
