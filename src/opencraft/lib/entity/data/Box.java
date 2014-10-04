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

package opencraft.lib.entity.data;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import opencraft.lib.entity.Entity;
import opencraft.lib.entity.IEntity;

public class Box extends Entity {
	
	
	public final double mX, mY, mZ, MX, MY, MZ;
	
	public Box() {
		this.mX = Double.MAX_VALUE;
		this.mY = Double.MAX_VALUE;
		this.mZ = Double.MAX_VALUE;
		this.MX = Double.MAX_VALUE;
		this.MY = Double.MAX_VALUE;
		this.MZ = Double.MAX_VALUE;
	}
	
	public Box(double mx, double my, double mz, double Mx, double My, double Mz) {
		this.mX = mx;
		this.mY = my;
		this.mZ = mz;
		this.MX = Mx;
		this.MY = My;
		this.MZ = Mz;
	}
	
	public Box(DoubleXYZ p1, DoubleXYZ p2) {
		this.mX = Math.min(p1.x, p2.x);
		this.mY = Math.min(p1.y, p2.y);
		this.mZ = Math.min(p1.z, p2.z);
		this.MX = Math.max(p1.x, p2.x);
		this.MY = Math.max(p1.y, p2.y);
		this.MZ = Math.max(p1.z, p2.z);
	}
	
	public Box(JSONObject json) {
		JSONArray jarr = (JSONArray) json.get("pp");
		
		if (jarr.size() >= 2) {
			DoubleXYZ p1 = new DoubleXYZ((JSONObject)jarr.get(0));
			DoubleXYZ p2 = new DoubleXYZ((JSONObject)jarr.get(1));
			this.mX = Math.min(p1.x, p2.x);
			this.mY = Math.min(p1.y, p2.y);
			this.mZ = Math.min(p1.z, p2.z);
			this.MX = Math.max(p1.x, p2.x);
			this.MY = Math.max(p1.y, p2.y);
			this.MZ = Math.max(p1.z, p2.z);
		} else {
			this.mX = 0;
			this.mY = 0;
			this.mZ = 0;
			this.MX = 0;
			this.MY = 0;
			this.MZ = 0;
		}
	}
	
	@Override
	public String toString() {
		return new StringBuffer("(").append(mX).append(",").append(mY).append(",").append(mZ).append(",").append(MX).append(",").append(MY).append(",").append(MZ).append(")").toString();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJSON(JSONObject json) {
		super.toJSON(json);
		JSONArray jarr = new JSONArray();
		DoubleXYZ p1 = new DoubleXYZ(mX, mY, mZ);
		DoubleXYZ p2 = new DoubleXYZ(MX, MY, MZ);
		jarr.add(p1.toJSON(new JSONObject()));
		jarr.add(p2.toJSON(new JSONObject()));
		json.put("pp", jarr);
		return json;
	}
	
	@Override
	public IEntity fromJSON(JSONObject json) {
		super.fromJSON(json);
		return new Box(json);
	}

	@Override
	public String getId() {
		return "data|OpenCraft|Box";
	}
}

