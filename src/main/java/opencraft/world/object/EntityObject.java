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

package opencraft.world.object;

import java.util.UUID;

import opencraft.lib.INamed;
import opencraft.lib.entity.Entity;
import opencraft.lib.entity.data.DoubleXYZ;
import opencraft.lib.tick.ITickable;
import opencraft.packet.s2c.PacketUpdateObject;
import opencraft.server.OpenCraftServer;
import opencraft.server.client.Ocan;
import opencraft.world.EntityWorld;
import opencraft.world.chunk.EntityChunk;
import opencraft.world.chunk.IChunkComponent;

public abstract class EntityObject extends Entity implements ITickable, INamed, IChunkComponent {
	
	private DoubleXYZ prvCoord;
	private double prvAngle;
	private Ocan prvRenderType;
	
	protected DoubleXYZ coord;
	protected double angle;
	protected Ocan renderType;
	
	protected String world;
	public final String uuid;
	
	public EntityObject() {
		this.uuid = UUID.randomUUID().toString();
		this.world = null;
		this.coord = null;
	}
	
	public EntityObject(String world, DoubleXYZ coord, Ocan type) {
		this();
		this.world = world;
		this.coord = coord;
		this.angle = 0d;
		this.renderType = type;
	}
	
	public String getUUID() {
		return this.uuid;
	}
	
	public EntityWorld getWorld() {
		return OpenCraftServer.instance().getWorldManager().getWorld(world);
	}
	
	public EntityChunk getChunk() {
		return getWorld().getChunkByCoord(coord);
	}
	
	public DoubleXYZ getCoord() {
		return this.coord;
	}
	
	@Override
	public void tick() {
		if (!(this.prvCoord.equals(this.coord) && this.prvAngle == this.angle && this.prvRenderType.equals(this.renderType))) {
			getChunk().event().emit(new PacketUpdateObject(this.uuid, this.coord, this.angle, this.renderType));
		}
	}
}
