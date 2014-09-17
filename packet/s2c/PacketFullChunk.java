/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage minecraft style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * class PacketFullChunk
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft.packet.s2c;

import org.json.simple.JSONObject;

import opencraft.lib.entity.Entity;
import opencraft.lib.entity.IEntity;
import opencraft.lib.entity.storage.EntityStorageList;
import opencraft.lib.event.packet.Packet;

public class PacketFullChunk extends Packet {

	@Override
	public String getId() {
		return "packet|OpenCraft|fullChunk";
	}
	
	public final String blocks;
	public final EntityStorageList objects;
	
	public PacketFullChunk() {
		this.blocks = null;
		this.objects = null;
	}
	
	public PacketFullChunk(String blocks, EntityStorageList objects) {
		this.blocks = blocks;
		this.objects = objects;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJSON(JSONObject json) {
		super.toJSON(json);
		json.put("blocks", blocks);
		json.put("objects", this.objects.toJSON(new JSONObject()));
		return json;
	}
	
	@Override
	public IEntity fromJSON(JSONObject json) {
		super.fromJSON(json);
		return new PacketFullChunk((String) json.get("blocks"), (EntityStorageList) Entity.registry.getEntity((JSONObject) json.get("objects")));
	}
}
