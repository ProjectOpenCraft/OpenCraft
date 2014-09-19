/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage sandbox style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * class PacketUpdateBlock
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft.packet.s2c;

import org.json.simple.JSONObject;

import opencraft.lib.entity.Entity;
import opencraft.lib.entity.IEntity;
import opencraft.lib.entity.data.IntXYZ;
import opencraft.lib.event.packet.Packet;

public class PacketUpdateBlock extends Packet {

	@Override
	public String getId() {
		return "packet|OpenCraft|updateBlock";
	}
	
	public final IntXYZ coord;
	public final char block;
	
	public PacketUpdateBlock() {
		this.coord = null;
		this.block = 0;
	}
	
	public PacketUpdateBlock(IntXYZ coord, char block) {
		this.coord = coord;
		this.block = block;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJSON(JSONObject json) {
		super.toJSON(json);
		json.put("coord", this.coord.toJSON(new JSONObject()));
		json.put("block", block);
		return json;
	}
	
	@Override
	public IEntity fromJSON(JSONObject json) {
		super.fromJSON(json);
		return new PacketUpdateBlock((IntXYZ) Entity.registry.getEntity((JSONObject) json.get("coord")), json.get("block").toString().toCharArray()[0]);
	}
}
