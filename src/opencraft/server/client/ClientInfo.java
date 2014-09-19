/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage sandbox style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * class ClientInfo
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft.server.client;

import org.json.simple.JSONObject;

import opencraft.lib.entity.Entity;
import opencraft.lib.entity.IEntity;

public class ClientInfo extends Entity {
	
	
	public String name;
	public String skinAddress;
	public final String clientId;
	public final String clientSecret;
	
	public ClientInfo() {
		this.name = "";
		this.skinAddress = "";
		this.clientId = "";
		this.clientSecret = "";
	}
	
	public ClientInfo(String name, String skin, String id, String secret) {
		this.name = name;
		this.skinAddress = skin;
		this.clientId = id;
		this.clientSecret = secret;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public JSONObject toJSON(JSONObject json) {
		super.toJSON(json);
		json.put("name", name);
		json.put("skin", skinAddress);
		json.put("id", clientId);
		json.put("secret", clientSecret);
		return json;
	}
	
	@Override
	public IEntity fromJSON(JSONObject json) {
		super.fromJSON(json);
		return new ClientInfo((String)json.get("name"), (String)json.get("skin"), (String)json.get("id"), (String)json.get("secret"));
	}

	@Override
	public String getId() {
		return "client|OpenCraft|info";
	}
}
