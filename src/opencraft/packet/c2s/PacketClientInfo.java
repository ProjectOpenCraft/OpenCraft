/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage sandbox style modding.
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

package opencraft.packet.c2s;

import org.json.simple.JSONObject;

import opencraft.lib.entity.IEntity;
import opencraft.lib.event.packet.Packet;
import opencraft.server.client.ClientInfo;

public class PacketClientInfo extends Packet {
	
	
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

	@Override
	public String getId() {
		return "packet|OpenCraft|ClientInfo";
	}
}
