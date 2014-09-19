/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage sandbox style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * class BlockRegistry
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft.world.block;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import opencraft.lib.entity.Entity;
import opencraft.lib.entity.IEntity;

public class BlockRegistry extends Entity {

	@Override
	public String getId() {
		return "registry|OpenCraft|blockRegistry";
	}
	
	private Map<Character, String> charMap;
	private Map<String, Character> revCharMap;
	private Map<String, IBlock> blockMap;
	
	public BlockRegistry() {
		this.charMap = new HashMap<Character, String>();
		this.revCharMap = new HashMap<String, Character>();
		this.blockMap = new HashMap<String, IBlock>();
	}
	
	public void registerBlock(IBlock block) {
		this.blockMap.put(block.getId(), block);
		
		if (this.charMap.values().contains(block.getId()) || this.revCharMap.keySet().contains(block.getId())) {
			return;
		} else {
			char simbol = (char) Math.min(this.charMap.size(), this.revCharMap.size());
			this.charMap.put(simbol, block.getId());
			this.revCharMap.put(block.getId(), simbol);
		}
	}
	
	public IBlock getBlock(char symbol) {
		return this.blockMap.get(this.charMap.get(symbol));
	}
	
	public IBlock getBlock(String id) {
		return this.blockMap.get(id);
	}
	
	public char getCode(IBlock block) {
		String id = block.getId();
		return this.revCharMap.get(id);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public JSONObject toJSON(JSONObject json) {
		super.toJSON(json);
		
		json.putAll(this.charMap);
		
		return json;
	}
	
	@Override
	public IEntity fromJSON(JSONObject json) {
		super.fromJSON(json);
		
		for (Object key : json.keySet()) {
			if (key instanceof String && json.get(key) instanceof String) {
				char simbol = ((String)json.get(key)).charAt(0);
				this.charMap.put(simbol, (String) key);
				this.revCharMap.put((String) key, simbol);
			}
		}
		
		return this;
	}
}
