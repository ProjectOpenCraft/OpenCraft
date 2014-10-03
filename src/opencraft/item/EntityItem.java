/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage sandbox style modding.
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import opencraft.lib.INamed;
import opencraft.lib.entity.Entity;

public abstract class EntityItem extends Entity implements INamed {
	
	public List<String> tags = new ArrayList<String>();
	
	public Collection<String> getTags() {
		return this.tags;
	}

	public int getAttackDamage() {
		return 1;
	}
}
