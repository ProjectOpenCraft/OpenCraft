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

package opencraft.world.object.living.player;

import java.util.ArrayList;
import java.util.List;

import opencraft.event.object.living.EventOnAttack;
import opencraft.event.object.living.player.EventPlayerJoinWorld;
import opencraft.event.object.living.player.EventPlayerPartWorld;
import opencraft.item.EntityItem;
import opencraft.lib.entity.data.DoubleXYZ;
import opencraft.lib.entity.data.IntXYZ;
import opencraft.lib.event.EnumEventOrder;
import opencraft.lib.event.IEvent;
import opencraft.lib.event.IEventListener;
import opencraft.packet.s2c.PacketFullChunk;
import opencraft.packet.s2c.PacketUpdateBlock;
import opencraft.packet.s2c.PacketUpdateObject;
import opencraft.server.client.Client;
import opencraft.server.client.ClientInfo;
import opencraft.server.client.Ocan;
import opencraft.world.chunk.ChunkAddress;
import opencraft.world.chunk.EntityChunk;
import opencraft.world.chunk.IChunkComponent;
import opencraft.world.object.living.DamageTypePhysical;
import opencraft.world.object.living.EntityObjectLiving;
import opencraft.world.object.living.IAttacker;
import opencraft.world.object.living.IDamageType;

public class Player extends EntityObjectLiving implements IAttacker {
	
	private ClientInfo info;
	private Client client;
	
	private ChunkAddress prvAddress;
	
	public IEventListener blockListener;
	public IEventListener objectListener;
	
	public Player() {
		this.info = null;
		this.event().addListener(new PlayerJoinWorldListener(this));
		this.event().addListener(new PlayerPartWorldListener(this));
	}
	
	public Player(String world, DoubleXYZ coord, ClientInfo info) {
		super(world, coord, new Ocan("OpenCraft", "player|stand", null), 0d, 0d, 100, 100);
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
		EventOnAttack event = (EventOnAttack) event().emit(new EventOnAttack(this, weapon, target, weapon == null ? 1 : weapon.getAttackDamage(), this.getDamageType(target, weapon)));
		return event.damage;
	}
	
	@Override
	public IDamageType getDamageType(EntityObjectLiving target, EntityItem weapon) {
		return weapon == null ? DamageTypePhysical.instance() : weapon.getDamageType();
	}

	@Override
	public void tick() {
		for (int i=-5; i<=5; i++) {
			for (int j=-5; j<=5; j++) {
				for (int k=-5; k<=5; k++) {
					getWorld().chunkManager.loadChunk(new ChunkAddress(this.world, new IntXYZ(((Double)(getCoord().x /32d)).intValue() +i, ((Double)(getCoord().y /32d)).intValue() +j, ((Double)(getCoord().z /32d)).intValue() +k)));
					if (!(this.world == this.prvAddress.world && this.getChunk().getAddress().equals(this.prvAddress))) {
						if (this.prvAddress.coord.x +i < this.getChunk().getAddress().coord.x -5 || this.prvAddress.coord.x +i > this.getChunk().getAddress().coord.x +5 || this.prvAddress.coord.y +j < this.getChunk().getAddress().coord.y -5 || this.prvAddress.coord.y +j > this.getChunk().getAddress().coord.y +5 || this.prvAddress.coord.z +k < this.getChunk().getAddress().coord.z -5 || this.prvAddress.coord.z +k > this.getChunk().getAddress().coord.z +5) {
							EntityChunk oldChunk = getWorld().getChunkManager().getChunk(new ChunkAddress(this.prvAddress.world, new IntXYZ(this.prvAddress.coord.x +i, this.prvAddress.coord.y +j, this.prvAddress.coord.z +k)));
							oldChunk.event().removeListener(this.blockListener);
							oldChunk.event().removeListener(this.objectListener);
						}
						if (this.getChunk().getAddress().coord.x +i < this.prvAddress.coord.x -5 || this.getChunk().getAddress().coord.x +i > this.prvAddress.coord.x +5 || this.getChunk().getAddress().coord.y +j < this.prvAddress.coord.y -5 || this.getChunk().getAddress().coord.y +j > this.prvAddress.coord.y +5 || this.getChunk().getAddress().coord.z +k < this.prvAddress.coord.z -5 || this.getChunk().getAddress().coord.z +k > this.prvAddress.coord.z +5) {
							EntityChunk newChunk = getWorld().getChunkManager().getChunk(new ChunkAddress(this.prvAddress.world, new IntXYZ(getChunk().getAddress().coord.x +i, getChunk().getAddress().coord.y +j, getChunk().getAddress().coord.z +k)));
							newChunk.event().addListener(this.blockListener);
							newChunk.event().addListener(this.objectListener);
							List<IChunkComponent> list = new ArrayList<IChunkComponent>();
							list.addAll(newChunk.getObjectList());
							list.addAll(newChunk.getEntityBlockList());
							this.client.sender().emit(new PacketFullChunk(newChunk.getChunkBlockData(), list));
						}
					}
				}
			}
		}
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

		@Override
		public EnumEventOrder getOrder() {
			return EnumEventOrder.lowest;
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

		@Override
		public EnumEventOrder getOrder() {
			return EnumEventOrder.lowest;
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
						player.getWorld().getChunkManager().loadChunk(new ChunkAddress(player.world, new IntXYZ(((Double)Math.floor(coord.x /32)).intValue(), ((Double)Math.floor(coord.y /32)).intValue(), ((Double)Math.floor(coord.z /32)).intValue())));
						EntityChunk cnk = player.getWorld().getChunkManager().forceLoadChunk(new ChunkAddress(player.world, new IntXYZ(((Double)Math.floor(coord.x /32)).intValue(), ((Double)Math.floor(coord.y /32)).intValue(), ((Double)Math.floor(coord.z /32)).intValue())));
						if (cnk != null) {
							cnk.event().addListener(player.blockListener);
							cnk.event().addListener(player.objectListener);
						}
					}
				}
			}
			return event;
		}

		@Override
		public EnumEventOrder getOrder() {
			return EnumEventOrder.lowest;
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
						EntityChunk cnk = player.getWorld().getChunkByCoord(player.getCoord());
						if (cnk != null) {
							cnk.event().removeListener(player.blockListener);
							cnk.event().removeListener(player.objectListener);
						}
					}
				}
			}
			return event;
		}

		@Override
		public EnumEventOrder getOrder() {
			return EnumEventOrder.lowest;
		}
	}
}
