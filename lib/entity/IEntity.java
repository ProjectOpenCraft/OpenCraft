/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage minecraft style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * class IEntity
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft.lib.entity;

import opencraft.lib.event.IEventHandler;

import org.json.simple.JSONObject;

public interface IEntity extends IEventHandler {
	
	/**
	 * Return unique id for each entity class
	 * Recommended format : entityType|yourModId|yourEntityId
	 * example : world|OpenCraft|earth
	 */
	String getId();
	
	/**
	 * JSONObject encoder function
	 */
	JSONObject toJSON(JSONObject json);
	
	/**
	 * JSONObject decoder function
	 */
	IEntity fromJSON(JSONObject json);
	
	void addSubEntity(IEntity entity);
	IEntity getSubEntity(Class<? extends IEntity> clazz);
}
