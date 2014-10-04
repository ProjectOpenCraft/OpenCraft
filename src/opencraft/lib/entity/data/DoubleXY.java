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


package opencraft.lib.entity.data;

import opencraft.lib.entity.Entity;
import opencraft.lib.entity.IEntity;

import org.json.simple.JSONObject;

public class DoubleXY extends Entity {
	
	
	public final double x, y;
	
	public DoubleXY() {
		this.x = Double.MAX_VALUE;
		this.y = Double.MAX_VALUE;
	}
	
	public DoubleXY(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public DoubleXY(String str) {
		if (str.indexOf("(") != 0 || str.indexOf(")") != str.length() -1) {
			this.x = Double.MAX_VALUE;
			this.y = Double.MAX_VALUE;
		} else {
			String[] arr = str.replaceAll("(", "").replaceAll(")", "").split(",");
			if (arr.length < 3) {
				this.x = Double.MAX_VALUE;
				this.y = Double.MAX_VALUE;
			} else {
				double valX = Double.MAX_VALUE;
				double valY = Double.MAX_VALUE;
				try {
					valX = Double.parseDouble(arr[0]);
					valY = Double.parseDouble(arr[1]);
				} catch(NumberFormatException e) {
					this.x = Double.MAX_VALUE;
					this.y = Double.MAX_VALUE;
					return;
				}
				x=valX;
				y=valY;
			}
		}
	}
	
	public DoubleXY(JSONObject json) {
		this.x = (double) json.get("x");
		this.y = (double) json.get("y");
	}
	
	@Override
	public String toString() {
		return new StringBuffer("(").append(x).append(",").append(y).append(")").toString();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public JSONObject toJSON(JSONObject json) {
		super.toJSON(json);
		json.put("x", x);
		json.put("y", y);
		return json;
	}
	
	@Override
	public IEntity fromJSON(JSONObject json) {
		super.fromJSON(json);
		return new DoubleXY(json);
	}

	@Override
	public String getId() {
		return "data|OpenCraft|doubleXY";
	}
}
