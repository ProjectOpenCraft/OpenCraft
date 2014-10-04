/*
 *OpenCraft - Build your own open world!
 *
 *Author : HyeonuPark(Moonrise1275)
 *
 *--------------------------------------------------------------------------
 *
 *The MIT License (MIT)
 *
 *Copyright (c) <year> <copyright holders>
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


package opencraft.packet.s2c;

import org.json.simple.JSONObject;

import opencraft.lib.entity.Entity;
import opencraft.lib.entity.IEntity;
import opencraft.lib.entity.data.DoubleXYZ;
import opencraft.lib.event.packet.Packet;

public class PacketUpdateObject extends Packet {

	@Override
	public String getId() {
		return "packet|OpenCraft|updateObject";
	}
	
	public String uuid;
	public DoubleXYZ coord;
	public double angle;
	public String renderType;
	
	public PacketUpdateObject() {
		this.uuid = "";
		this.coord = null;
		this.angle = 0d;
		this.renderType = "";
	}
	
	public PacketUpdateObject(String uuid, DoubleXYZ coord, double angle, String type) {
		this.uuid = uuid;
		this.coord = coord;
		this.angle = angle;
		this.renderType = type;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJSON(JSONObject json) {
		super.toJSON(json);
		json.put("uuid", this.uuid);
		json.put("coord", this.coord.toJSON(new JSONObject()));
		json.put("angle", this.angle);
		json.put("type", this.renderType);
		return json;
	}
	
	@Override
	public IEntity fromJSON(JSONObject json) {
		super.fromJSON(json);
		this.uuid = (String) json.get("uuid");
		this.coord = (DoubleXYZ) Entity.registry.getEntity((JSONObject) json.get("coord"));
		this.angle = (double) json.get("angle");
		this.renderType = (String) json.get("type");
		return this;
	}
}
