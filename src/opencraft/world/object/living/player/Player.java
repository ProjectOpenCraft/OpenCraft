/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage sandbox style modding.
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
import opencraft.event.object.living.player.EventPlayerJoinWorld;
import opencraft.event.object.living.player.EventPlayerPartWorld;
import opencraft.item.EntityItem;
import opencraft.lib.entity.Entity;
import opencraft.lib.entity.IEntity;
import opencraft.lib.entity.data.DoubleXYZ;
import opencraft.lib.entity.data.IntXYZ;
import opencraft.lib.event.IEvent;
import opencraft.lib.event.IEventListener;
import opencraft.packet.s2c.PacketUpdateBlock;
import opencraft.packet.s2c.PacketUpdateObject;
import opencraft.server.OpenCraftServer;
import opencraft.server.client.Client;
import opencraft.server.client.ClientInfo;
import opencraft.world.EntityWorld;
import opencraft.world.block.IBlockInteractor;
import opencraft.world.object.living.EntityObjectLiving;
import opencraft.world.object.living.IAttacker;

public class Player extends EntityObjectLiving implements IBlockInteractor, IAttacker {
	
	private ClientInfo info;
	private Client client;
	
	private String prvWorld;
	private IntXYZ prvChunk;
	
	public IEventListener blockListener;
	public IEventListener objectListener;
	
	public Player() {
		this.info = null;
		this.event().addListener(new PlayerJoinWorldListener(this));
		this.event().addListener(new PlayerPartWorldListener(this));
	}
	
	public Player(String world, DoubleXYZ coord, ClientInfo info) {
		super(world, coord, "player|OpenCraft|stand", 0d, 0d, 100, 100);
		this.info = info;
		this.event().addListener(new PlayerJoinWorldListener(this));
		this.event().addListener(new PlayerPartWorldListener(this));
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
		this.prvWorld = (String) json.get("world");
		this.prvChunk = (IntXYZ) Entity.registry.getEntity((JSONObject) json.get("chunk"));
		return this;
	}
	
	public Client getClient() {
		return this.client;
	}
	
	public void setClient(Client c) {
		this.client = c;
		this.blockListener = new BlockUpdateListener(client);
		this.objectListener = new ObjectUpdateListener(client);
	}

	@Override
	public int getAttackDamage(EntityObjectLiving target, EntityItem weapon) {
		EventOnAttack event = (EventOnAttack) event().emit(new EventOnAttack(this, weapon, target, weapon.getDamage()));
		return event.damage;
	}

	@Override
	public void tick() {
		if (!(this.world == this.prvWorld && this.getChunk().getAddress().equals(this.prvChunk))) {
			EntityWorld w = OpenCraftServer.instance().getWorldManager().getWorld(this.world);
			for (int i=-5; i<=5; i++) {
				for (int j=-5; j<=5; j++) {
					for (int k=-5; k<=5; k++) {
						w.chunkManager.loadChunk(new IntXYZ(getChunk().getAddress().x +i, getChunk().getAddress().y +j, getChunk().getAddress().z +k));
						if (this.prvChunk.x +i < this.getChunk().getAddress().x -5 || this.prvChunk.x +i > this.getChunk().getAddress().x +5 || this.prvChunk.y +j < this.getChunk().getAddress().y -5 || this.prvChunk.y +j > this.getChunk().getAddress().y +5 || this.prvChunk.z +k < this.getChunk().getAddress().z -5 || this.prvChunk.z +k > this.getChunk().getAddress().z +5) {
							w.getChunkManager().getChunk(new IntXYZ(prvChunk.x +i, prvChunk.y +j, prvChunk.z +k)).event().removeListener(this.blockListener);
							w.getChunkManager().getChunk(new IntXYZ(prvChunk.x +i, prvChunk.y +j, prvChunk.z +k)).event().removeListener(this.objectListener);
						}
						if (this.getChunk().getAddress().x +i < this.prvChunk.x -5 || this.getChunk().getAddress().x +i > this.prvChunk.x +5 || this.getChunk().getAddress().y +j < this.prvChunk.y -5 || this.getChunk().getAddress().y +j > this.prvChunk.y +5 || this.getChunk().getAddress().z +k < this.prvChunk.z -5 || this.getChunk().getAddress().z +k > this.prvChunk.z +5) {
							this.getChunk().event().addListener(this.blockListener);
							this.getChunk().event().addListener(this.objectListener);
						}
					}
				}
			}
		}
	}

	@Override
	public String getId() {
		return "object|OpenCraft|player";
	}
	
	class BlockUpdateListener implements IEventListener {
		
		Client client;
		
		public BlockUpdateListener(Client c) {
			this.client = c;
		}

		@Override
		public Class<? extends IEvent> getEventClass() {
			return PacketUpdateBlock.class;
		}

		@Override
		public IEvent handleEvent(IEvent event) {
			client.sender().emit(event);
			return event;
		}
	}
	
	class ObjectUpdateListener implements IEventListener {
		
		Client client;
		
		public ObjectUpdateListener(Client c) {
			this.client = c;
		}

		@Override
		public Class<? extends IEvent> getEventClass() {
			return PacketUpdateObject.class;
		}

		@Override
		public IEvent handleEvent(IEvent event) {
			client.sender().emit(event);
			return event;
		}
	}
	
	class PlayerJoinWorldListener implements IEventListener {
		
		Player player;
		
		public PlayerJoinWorldListener(Player p) {
			this.player = p;
		}

		@Override
		public Class<? extends IEvent> getEventClass() {
			return EventPlayerJoinWorld.class;
		}

		@Override
		public IEvent handleEvent(IEvent event) {
			for (int i=-5; i<=5; i++) {
				for (int j=-5; j<=5; j++) {
					for (int k=-5; k<=5; k++) {
						player.getChunk().event().addListener(player.blockListener);
						player.getChunk().event().addListener(player.objectListener);
					}
				}
			}
			return event;
		}
	}
	
class PlayerPartWorldListener implements IEventListener {
		
		Player player;
		
		public PlayerPartWorldListener(Player p) {
			this.player = p;
		}

		@Override
		public Class<? extends IEvent> getEventClass() {
			return EventPlayerPartWorld.class;
		}

		@Override
		public IEvent handleEvent(IEvent event) {
			for (int i=-5; i<=5; i++) {
				for (int j=-5; j<=5; j++) {
					for (int k=-5; k<=5; k++) {
						player.getChunk().event().removeListener(player.blockListener);
						player.getChunk().event().removeListener(player.objectListener);
					}
				}
			}
			return event;
		}
	}
}
