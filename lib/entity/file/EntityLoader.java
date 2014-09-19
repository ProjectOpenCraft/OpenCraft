/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage minecraft style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * class EntityLoader
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft.lib.entity.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import opencraft.lib.entity.Entity;
import opencraft.lib.entity.IEntity;

public class EntityLoader {
	
	public static IEntity loadEntity(File file) {
		try {
			BufferedReader r = new BufferedReader(new FileReader(file));
			JSONObject json = (JSONObject) JSONValue.parse(r);
			r.close();
			return Entity.registry.getEntity(json);
		} catch (IOException e) {
			return null;
		}
	}
	
	public static void saveEntity(IEntity entity, File file) throws IOException {
		BufferedWriter r = new BufferedWriter(new FileWriter(file));
		JSONObject json = entity.toJSON(new JSONObject());
		json.writeJSONString(r);
	}
}
