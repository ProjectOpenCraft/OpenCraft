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

import opencraft.event.world.chunk.EventBlockChanged;
import opencraft.event.world.chunk.EventLoadChunk;
import opencraft.event.world.chunk.EventUnloadChunk;
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
import opencraft.packet.s2c.PacketUpdateBlock;
import opencraft.server.OpenCraftServer;
import opencraft.world.block.Block;
import opencraft.world.block.IBlock;
import opencraft.world.block.entity.EntityBlock;
import opencraft.world.object.EntityObject;

public class EntityChunk extends Entity implements ITickable {
	
	String world = "";
	IntXYZ address = new IntXYZ((JSONObject)null);
	char[][][] block = new char[32][32][32];
	EntityStorageIntXYZ entityBlocks = new EntityStorageIntXYZ();
	EntityStorageList entityObjects = new EntityStorageList();
	
	public EntityChunk() {
		super();
		event().addListener(new EventListenerOnChunkLoad(this, entityBlocks, entityObjects));
		event().addListener(new EventListenerOnChunkUnload(this, entityBlocks, entityObjects));
	}
	
	public EntityChunk(String world, IntXYZ address) {
		this();
		this.world = world;
		this.address = address;
	}
	
	public IntXYZ getBlockCoord(IntXYZ chunkCoord) {
		int x = this.address.x * 32 + chunkCoord.x;
		int y = this.address.y * 32 + chunkCoord.y;
		int z = this.address.z * 32 + chunkCoord.z;
		return new IntXYZ(x, y, z);
	}
	
	public IBlock getBlock(IntXYZ coord) {
		if (coord.x < 0 || coord.y < 0 || coord.z < 0 || coord.x >= 32 || coord.y >= 32 || coord.z >= 32) return null;
		char symbol = this.block[coord.x][coord.y][coord.z];
		return Block.registry.getBlock(symbol);
	}
	
	public boolean setBlock(IBlock block, IntXYZ coord) {
		if (coord.x < 0 || coord.y < 0 || coord.z < 0 || coord.x >= 32 || coord.y >= 32 || coord.z >= 32) return false;
		
		IBlock oldBlock = getBlock(coord);
		EventBlockChanged event = (EventBlockChanged) event().emit(new EventBlockChanged(getBlockCoord(coord), oldBlock, block));
		block = event.newBlock;
		
		char symbol = Block.registry.getCode(block);
		this.block[coord.x][coord.y][coord.z] = symbol;
		event().emit(new PacketUpdateBlock(getBlockCoord(coord), symbol));
		return true;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public JSONObject toJSON(JSONObject json) {
		super.toJSON(json);
		json.put("world", world);
		json.put("address", address.toJSON(new JSONObject()));
		json.put("block", encode(block));
		json.put("entityBlock", entityBlocks.toJSON(new JSONObject()));
		json.put("entityObject", entityObjects.toJSON(new JSONObject()));
		return json;
	}
	
	@Override
	public IEntity fromJSON(JSONObject json) {
		super.fromJSON(json);
		this.world = (String) json.get("world");
		this.address = (IntXYZ) Entity.registry.getEntity((JSONObject) json.get("address"));
		this.block = decode((String) json.get("block"));
		this.entityBlocks = (EntityStorageIntXYZ) Entity.registry.getEntity((JSONObject) json.get("entityBlock"));
		this.entityObjects = (EntityStorageList) Entity.registry.getEntity((JSONObject) json.get("entityObject"));
		return this;
	}
	
	public String getChunkBlockData() {
		return encode(this.block);
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
		IBlock nb = b.onChunkTick(OpenCraftServer.instance().getWorldManager().getWorld(world), x + (this.address.x * 32), y + (this.address.y * 32), z + (this.address.z * 32));
		event().emit(new PacketUpdateBlock(getBlockCoord(new IntXYZ(x, y, z)), Block.registry.getCode(nb)));
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
			return EventLoadChunk.class;
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
			return EventUnloadChunk.class;
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

	@Override
	public String getId() {
		return "world|OpenCraft|chunk";
	}
}
