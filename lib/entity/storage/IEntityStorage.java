/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage minecraft style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * interface IEntityStorage
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft.lib.entity.storage;

import java.util.Collection;
import opencraft.lib.entity.IEntity;

public interface IEntityStorage extends IEntity {
	
	boolean put(Object key, IEntity value);
	IEntity get(Object key);
	boolean removeKey(Object key);
	boolean removeValue(IEntity value);
	Collection<IEntity> values();
	IEntityStorage copy();
}
