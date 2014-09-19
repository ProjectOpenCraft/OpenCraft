/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage sandbox style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * class IEntityRegistry
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft.lib.entity;

import org.json.simple.JSONObject;

public interface IEntityRegistry {
	
	void registerEntity(IEntity entity);
	IEntity getEntity(JSONObject json);
	Class<? extends IEntity> getClass(String id);
}
