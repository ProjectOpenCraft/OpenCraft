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

package opencraft.world.chunk;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import opencraft.event.world.chunk.EventBlockChanged;
import opencraft.event.world.chunk.EventLoadChunk;
import opencraft.event.world.chunk.EventUnloadChunk;
import opencraft.lib.entity.Entity;
import opencraft.lib.entity.IEntity;
import opencraft.lib.entity.data.IntXYZ;
import opencraft.lib.event.EnumEventOrder;
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
	
	ChunkAddress address = new ChunkAddress("", new IntXYZ());
	char[][][] block = new char[32][32][32];
	Map<IntXYZ, EntityBlock> entityBlocks = Collections.synchronizedMap(new HashMap<IntXYZ, EntityBlock>());
	List<EntityObject> entityObjects = Collections.synchronizedList(new LinkedList<EntityObject>());
	
	public EntityChunk() {
		super();
		event().addListener(new EventListenerOnChunkLoad(this, entityBlocks, entityObjects));
		event().addListener(new EventListenerOnChunkUnload(this, entityBlocks, entityObjects));
	}
	
	public EntityChunk(ChunkAddress address) {
		this();
		this.address = address;
	}
	
	public IntXYZ getBlockCoord(IntXYZ chunkCoord) {
		int x = this.address.coord.x * 32 + chunkCoord.x;
		int y = this.address.coord.y * 32 + chunkCoord.y;
		int z = this.address.coord.z * 32 + chunkCoord.z;
		return new IntXYZ(x, y, z);
	}
	
	public ChunkAddress getAddress() {
		return this.address;
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
	
	public void addObject(EntityObject obj) {
		this.entityObjects.add(obj);
	}
	
	public void removeObject(EntityObject obj) {
		this.entityObjects.remove(obj);
	}
	
	public String getChunkBlockData() {
		return encode(this.block);
	}
	
	public List<EntityObject> getObjectList() {
		return this.entityObjects;
	}
	
	public Collection<EntityBlock> getEntityBlockList() {
		return this.entityBlocks.values();
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
		IBlock nb = b.onChunkTick(OpenCraftServer.instance().getWorldManager().getWorld(this.address.world), x + (this.address.coord.x * 32), y + (this.address.coord.y * 32), z + (this.address.coord.z * 32));
		if (b != nb) event().emit(new PacketUpdateBlock(getBlockCoord(new IntXYZ(x, y, z)), Block.registry.getCode(nb)));
		
		for (EntityObject obj : this.entityObjects) {
			IntXYZ objChunkCoord = obj.getCoord().multiply(1d/32d).toInt();
			if (!objChunkCoord.equals(this.getAddress().coord) && obj.getWorld().getChunkByCoord(obj.getCoord()) != null) {
				this.removeObject(obj);
				obj.getWorld().getChunkByCoord(obj.getCoord()).addObject(obj);
			}
		}
	}
	
	class EventListenerOnChunkLoad implements IEventListener {
		
		private EntityChunk chunk;
		private Map<IntXYZ, EntityBlock> entityBlocks;
		private List<EntityObject> entityObjects;
		
		public EventListenerOnChunkLoad(EntityChunk chunk, Map<IntXYZ, EntityBlock> blocks, List<EntityObject> objects) {
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
			for (IEntity entityObject : entityObjects) {
				entityObject.event().emit(event);
				ticks.addTick((EntityObject)entityObject);
			}
			return event;
		}

		@Override
		public EnumEventOrder getOrder() {
			return EnumEventOrder.lowest;
		}
		
	}
	
	class EventListenerOnChunkUnload implements IEventListener {
		
		private EntityChunk chunk;
		private Map<IntXYZ, EntityBlock> entityBlocks;
		private List<EntityObject> entityObjects;
		
		public EventListenerOnChunkUnload(EntityChunk chunk, Map<IntXYZ, EntityBlock> blocks, List<EntityObject> objects) {
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
			for (IEntity entityObject : entityObjects) {
				entityObject.event().emit(event);
				ticks.removeTick((EntityObject)entityObject);
			}
			return event;
		}

		@Override
		public EnumEventOrder getOrder() {
			return EnumEventOrder.lowest;
		}
		
	}
}
