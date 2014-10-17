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

package opencraft.world.object.living;

import opencraft.event.object.living.EventOnAttacked;
import opencraft.event.object.living.EventOnDeath;
import opencraft.lib.entity.data.DoubleXYZ;
import opencraft.lib.event.EnumEventOrder;
import opencraft.lib.event.IEvent;
import opencraft.lib.event.IEventListener;
import opencraft.server.client.Ocan;
import opencraft.world.object.EntityObject;

public abstract class EntityObjectLiving extends EntityObject {
	
	protected double headPitch;
	protected double headYaw;
	
	protected int maxHealth;
	protected int curHealth;
	
	public EntityObjectLiving() {
		super();
		this.headPitch = 0d;
		this.headYaw = 0d;
		this.maxHealth = 0;
		this.curHealth = 0;
		this.addListeners();
	}
	
	public EntityObjectLiving(String world, DoubleXYZ coord, Ocan type, double pitch, double yaw, int maxHealth, int curHealth) {
		super(world, coord, type);
		this.headPitch = pitch;
		this.headYaw = yaw;
		this.maxHealth = maxHealth;
		this.curHealth = curHealth;
		this.addListeners();
	}
	
	private void addListeners() {
		this.event().addListener(new AttackedListener(this));
		this.event().addListener(new DeathListener(this));
	}
	
	class AttackedListener implements IEventListener {
		
		EntityObjectLiving living;
		
		public AttackedListener(EntityObjectLiving living) {
			this.living = living;
		}

		@Override
		public Class<? extends IEvent> getEventClass() {
			return EventOnAttacked.class;
		}

		@Override
		public IEvent handleEvent(IEvent event) {
			EventOnAttacked e = (EventOnAttacked) event;
			this.living.curHealth -= e.damage;
			if (this.living.curHealth <= 0) {
				this.living.curHealth = 0;
				this.living.event().emit(new EventOnDeath(this.living));
			}
			
			
			return event;
		}

		@Override
		public EnumEventOrder getOrder() {
			return EnumEventOrder.lowest;
		}
	}
	
	class DeathListener implements IEventListener {
		
		EntityObjectLiving living;
		
		public DeathListener(EntityObjectLiving living) {
			this.living = living;
		}

		@Override
		public Class<? extends IEvent> getEventClass() {
			return EventOnDeath.class;
		}

		@Override
		public IEvent handleEvent(IEvent event) {
			return event;
		}

		@Override
		public EnumEventOrder getOrder() {
			return EnumEventOrder.lowest;
		}
	}
}
