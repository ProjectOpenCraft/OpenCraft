/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage minecraft style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * class Player
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft.world.object.living.player;

import org.json.simple.JSONObject;

import opencraft.event.object.living.EventOnAttack;
import opencraft.item.EntityItem;
import opencraft.lib.entity.Entity;
import opencraft.lib.entity.IEntity;
import opencraft.server.client.Client;
import opencraft.server.client.ClientInfo;
import opencraft.world.block.IBlockInteractor;
import opencraft.world.object.IObjectInteractor;
import opencraft.world.object.living.EntityObjectLiving;
import opencraft.world.object.living.IAttacker;

public class Player extends EntityObjectLiving implements IBlockInteractor, IObjectInteractor, IAttacker {
	
	private ClientInfo info;
	private Client client;
	
	public Player() {
		this.info = null;
	}
	
	public Player(ClientInfo info) {
		this.info = info;
	}

	@Override
	public String getName() {
		return info.name;
	}
	
	public boolean isMatchingClient(String id) {
		return info != null && id != null && id.equals(info.clientId);
	}
	
	public boolean isValidClient(String secret) {
		return info != null && secret != null && secret.equals(info.clientSecret);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public JSONObject toJSON(JSONObject json) {
		super.toJSON(json);
		json.put("info", info.toJSON(new JSONObject()));
		return json;
	}
	
	@Override
	public IEntity fromJSON(JSONObject json) {
		super.fromJSON(json);
		this.info = (ClientInfo) Entity.registry.getEntity((JSONObject) json.get("info"));
		return this;
	}
	
	public Client getClient() {
		return this.client;
	}
	
	public void setClient(Client c) {
		this.client = c;
	}

	@Override
	public int getAttackDamage(EntityObjectLiving target, EntityItem weapon) {
		EventOnAttack event = (EventOnAttack) event().emit(new EventOnAttack(this, weapon, target, weapon.getDamage()));
		return event.damage;
	}

	@Override
	public void tick() {
		
	}

	@Override
	public String getId() {
		return "object|OpenCraft|player";
	}
}
