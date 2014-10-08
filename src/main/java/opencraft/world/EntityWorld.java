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

package opencraft.world;

import opencraft.lib.INamed;
import opencraft.lib.entity.Entity;
import opencraft.lib.entity.data.DoubleXYZ;
import opencraft.lib.entity.data.IntXYZ;
import opencraft.lib.tick.ITickable;
import opencraft.server.OpenCraftServer;
import opencraft.world.block.IBlock;
import opencraft.world.chunk.ChunkAddress;
import opencraft.world.chunk.ChunkManager;
import opencraft.world.chunk.EntityChunk;

public abstract class EntityWorld extends Entity implements ITickable, INamed {
	
	public ChunkManager chunkManager = new ChunkManager(this);
	
	public DoubleXYZ spawnPoint;
	
	public EntityWorld() {
		OpenCraftServer.instance().getTickManager().addTick(chunkManager);
	}
	
	public IntXYZ getSurface(IntXYZ coord) {
		while(!getBlock(coord).isAir()) {
			coord = new IntXYZ(coord.x, coord.y +1, coord.z);
		}
		return coord;
	}
	
	public ChunkManager getChunkManager() {
		return this.chunkManager;
	}
	
	public abstract DoubleXYZ getSpawnPoint();
	public abstract void setSpawnPoint(DoubleXYZ spawn);
	
	public IBlock getBlock(IntXYZ coord) {
		IntXYZ chunkCoord = new IntXYZ(coord.x/32, coord.y/32, coord.z/32);
		IntXYZ blockCoord = new IntXYZ(coord.x%32, coord.y%32, coord.z%32);
		EntityChunk chunk = this.chunkManager.getChunk(new ChunkAddress(this.getName(), chunkCoord));
		if (chunk == null) return null;
		return chunk.getBlock(blockCoord);
	}
	
	public boolean setBlock(IBlock block, IntXYZ coord) {
		IntXYZ chunkCoord = new IntXYZ(coord.x/32, coord.y/32, coord.z/32);
		IntXYZ blockCoord = new IntXYZ(coord.x%32, coord.y%32, coord.z%32);
		EntityChunk chunk = this.chunkManager.getChunk(new ChunkAddress(this.getName(), chunkCoord));
		if (chunk == null) return false;
		return chunk.setBlock(block, blockCoord);
	}
	
	public EntityChunk getChunkByCoord(IntXYZ coord) {
		return this.chunkManager.getChunk(new ChunkAddress(this.getName(), new IntXYZ(coord.x /32, coord.y /32, coord.z /32)));
	}
	
	public EntityChunk getChunkByCoord(DoubleXYZ coord) {
		return this.chunkManager.getChunk(new ChunkAddress(this.getName(), new IntXYZ(((Double)Math.floor(coord.x /32)).intValue(), ((Double)Math.floor(coord.y /32)).intValue(), ((Double)Math.floor(coord.z /32)).intValue())));
	}
}
