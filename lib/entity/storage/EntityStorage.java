/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage minecraft style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * class EntityStorage
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft.lib.entity.storage;

import org.json.simple.JSONObject;

import opencraft.lib.entity.Entity;

public abstract class EntityStorage extends Entity implements IEntityStorage {
	
	static {
		ENTITY_ID = "entitystorage|OpenCraft|base";
	}
	
	@Override
	public IEntityStorage copy() {
		JSONObject json = this.toJSON(new JSONObject());
		IEntityStorage newStorage = (IEntityStorage) Entity.registry.getEntity(json);
		return newStorage;
	}
}
