/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage minecraft style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * class EntityItem
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft.item;

import opencraft.lib.INamed;
import opencraft.lib.entity.Entity;

public abstract class EntityItem extends Entity implements INamed {
	
	static {
		ENTITY_ID = "item|OpenCraft|base";
	}
	
	public abstract int getDamage();
}
