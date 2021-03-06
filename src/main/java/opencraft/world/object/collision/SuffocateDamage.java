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

package opencraft.world.object.collision;

import opencraft.item.EntityItem;
import opencraft.world.object.living.DamageTypePhysical;
import opencraft.world.object.living.EntityObjectLiving;
import opencraft.world.object.living.IAttacker;
import opencraft.world.object.living.IDamageType;

public class SuffocateDamage implements IAttacker {
	
	private static SuffocateDamage inst;
	
	public static SuffocateDamage instance() {
		if (inst == null) inst = new SuffocateDamage();
		return inst;
	}
	
	private SuffocateDamage(){}

	@Override
	public String getName() {
		return "Suffocate";
	}

	@Override
	public int getAttackDamage(EntityObjectLiving target, EntityItem weapon) {
		return 1;
	}

	@Override
	public IDamageType getDamageType(EntityObjectLiving target, EntityItem weapon) {
		return DamageTypePhysical.instance();
	}
	
}
