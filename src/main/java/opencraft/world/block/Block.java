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

package opencraft.world.block;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import opencraft.event.object.living.EventOnAttacked;
import opencraft.lib.CubeDirection;
import opencraft.lib.entity.data.Box;
import opencraft.lib.entity.data.IntXYZ;
import opencraft.world.EntityWorld;
import opencraft.world.object.EntityObject;
import opencraft.world.object.collision.SuffocateDamage;
import opencraft.world.object.living.EntityObjectLiving;

public abstract class Block implements IBlock {
	
	public static BlockRegistry registry = new BlockRegistry();
	
	private List<Box> listAABB = new LinkedList<>();
	
	public Block() {
		if (isFullCube()) this.addAABB(new Box(0, 0, 0, 1, 1, 1));
	}
	
	protected boolean isFullCube() {
		return false;
	}
	
	protected boolean isSolid() {
		return true;
	}
	
	public IBlock onChunkTick(EntityWorld world, IntXYZ coord) {
		return this;
	}
	
	public void onNeighborChanged(EntityWorld world, IntXYZ coord, CubeDirection side) {
		
	}
	
	public void onObjectCollide(EntityWorld world, IntXYZ coord, EntityObject object) {
		if (object instanceof EntityObjectLiving && isSolid()) {
			EntityObjectLiving living = (EntityObjectLiving) object;
			living.event().emit(new EventOnAttacked(SuffocateDamage.instance(), null, living, SuffocateDamage.instance().getAttackDamage(living, null)));
		}
	}
	
	public Collection<Box> getAABB() {
		return this.listAABB;
	}
	
	protected void addAABB(Box box) {
		this.listAABB.add(box);
	}
	
	public boolean isAir() {
		return false;
	}
	
	public boolean isTransparent() {
		return false;
	}
	
	public boolean isReplaceable() {
		return false;
	}
	
	public boolean isFluid() {
		return false;
	}
}
