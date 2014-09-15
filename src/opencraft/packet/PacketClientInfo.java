/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage minecraft style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * class PacketClientInfo
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft.packet;

import org.json.simple.JSONObject;

import opencraft.lib.client.ClientInfo;
import opencraft.lib.entity.IEntity;
import opencraft.lib.event.packet.Packet;

public class PacketClientInfo extends Packet {
	
	static {
		ENTITY_ID = "packet|OpenCraft|ClientInfo";
	}
	
	public final ClientInfo info;
	
	public PacketClientInfo() {
		this.info = null;
	}
	
	public PacketClientInfo(ClientInfo i) {
		this.info = i;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public JSONObject toJSON(JSONObject json) {
		super.toJSON(json);
		json.put("info", this.info.toJSON(new JSONObject()));
		return json;
	}
	
	@Override
	public IEntity fromJSON(JSONObject json) {
		super.fromJSON(json);
		return new PacketClientInfo((ClientInfo) new ClientInfo().fromJSON((JSONObject) json.get("info")));
	}
}
