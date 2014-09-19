/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage sandbox style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * class EntityObject
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft.world.object;

import java.util.UUID;

import org.json.simple.JSONObject;

import opencraft.lib.INamed;
import opencraft.lib.entity.Entity;
import opencraft.lib.entity.IEntity;
import opencraft.lib.entity.data.DoubleXYZ;
import opencraft.lib.entity.data.IntXYZ;
import opencraft.lib.tick.ITickable;
import opencraft.packet.s2c.PacketUpdateObject;
import opencraft.server.OpenCraftServer;
import opencraft.world.EntityWorld;
import opencraft.world.chunk.EntityChunk;

public abstract class EntityObject extends Entity implements ITickable, INamed {
	
	private DoubleXYZ prvCoord;
	private double prvAngle;
	private int prvRenderType;
	
	protected DoubleXYZ coord;
	protected double angle;
	protected int renderType;
	
	protected String world;
	protected IntXYZ chunk;
	public final String uuid;
	
	public EntityObject() {
		this.uuid = UUID.randomUUID().toString();
		this.world = null;
		this.chunk = null;
		this.coord = null;
	}
	
	public String getUUID() {
		return this.uuid;
	}
	
	public EntityWorld getWorld() {
		return OpenCraftServer.instance().getWorldManager().getWorld(world);
	}
	
	public EntityChunk getChunk() {
		return getWorld().getChunkManager().getChunk(chunk);
	}
	
	public DoubleXYZ getCoord() {
		return this.coord;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJSON(JSONObject json) {
		super.toJSON(json);
		json.put("world", this.world);
		json.put("chunk", this.chunk.toJSON(new JSONObject()));
		json.put("coord", this.coord.toJSON(new JSONObject()));
		return json;
	}
	
	@Override
	public IEntity fromJSON(JSONObject json) {
		super.fromJSON(json);
		this.world = (String) json.get("world");
		this.chunk = (IntXYZ) Entity.registry.getEntity((JSONObject) json.get("chunk"));
		this.coord = (DoubleXYZ) Entity.registry.getEntity((JSONObject) json.get("coord"));
		return this;
	}
	
	@Override
	public void tick() {
		if (!(this.prvCoord.equals(this.coord) && this.prvAngle == this.angle && this.prvRenderType == this.renderType)) {
			getChunk().event().emit(new PacketUpdateObject(this.uuid, this.coord, this.angle, this.renderType));
		}
	}
}
