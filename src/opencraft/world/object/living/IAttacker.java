/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage sandbox style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * interface IAttacker
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft.world.object.living;

import opencraft.item.EntityItem;
import opencraft.lib.INamed;

public interface IAttacker extends INamed {
	
	public int getAttackDamage(EntityObjectLiving target, EntityItem weapon);
}
