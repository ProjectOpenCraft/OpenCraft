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
	private char lastChar = 0;
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
			char simbol = newSymbol();
			this.charMap.put(simbol, block.getId());
			this.revCharMap.put(block.getId(), simbol);
		}
	}
	
	public char newSymbol() {
		char next = (char) (this.lastChar +1);
		while (next == '{' || next == '}' || next == '"' || next == '[' || next == ']' || next == '@') {
			next += 1;
		}
		this.lastChar = next;
		return next;
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
