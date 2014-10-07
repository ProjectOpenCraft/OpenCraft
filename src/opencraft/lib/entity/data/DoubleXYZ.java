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

import opencraft.lib.entity.Entity;

public class DoubleXYZ extends Entity {
	
	
	public final double x, y, z;
	
	public DoubleXYZ() {
		this.x = Double.MAX_VALUE;
		this.y = Double.MAX_VALUE;
		this.z = Double.MAX_VALUE;
	}
	
	public DoubleXYZ(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public DoubleXYZ(String str) {
		if (str.indexOf("(") != 0 || str.indexOf(")") != str.length() -1) {
			this.x = Double.MAX_VALUE;
			this.y = Double.MAX_VALUE;
			this.z = Double.MAX_VALUE;
		} else {
			String[] arr = str.replaceAll("(", "").replaceAll(")", "").split(",");
			if (arr.length < 3) {
				this.x = Double.MAX_VALUE;
				this.y = Double.MAX_VALUE;
				this.z = Double.MAX_VALUE;
			} else {
				double valX = Double.MAX_VALUE;
				double valY = Double.MAX_VALUE;
				double valZ = Double.MAX_VALUE;
				try {
					valX = Double.parseDouble(arr[0]);
					valY = Double.parseDouble(arr[1]);
					valZ = Double.parseDouble(arr[2]);
				} catch(NumberFormatException e) {
					this.x = Double.MAX_VALUE;
					this.y = Double.MAX_VALUE;
					this.z = Double.MAX_VALUE;
					return;
				}
				x=valX;
				y=valY;
				z=valZ;
			}
		}
	}
	
	@Override
	public String toString() {
		return new StringBuffer("(").append(x).append(",").append(y).append(",").append(z).append(")").toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DoubleXYZ) {
			DoubleXYZ d = (DoubleXYZ) obj;
			return this.x == d.x && this.y == d.y && this.z == d.z;
		} else return false;
	}
	
	public DoubleXYZ add(DoubleXYZ adder) {
		return new DoubleXYZ(this.x + adder.x, this.y + adder.y, this.z + adder.z);
	}
	
	public DoubleXYZ add(IntXYZ adder) {
		return new DoubleXYZ(this.x + (double)adder.x, this.y + (double)adder.y, this.z + (double)adder.z);
	}
	
	public DoubleXYZ multiply(double num) {
		return new DoubleXYZ(this.x * num, this.y * num, this.z * num);
	}
	
	public IntXYZ toInt() {
		return new IntXYZ((int)Math.floor(this.x), (int)Math.floor(this.y), (int)Math.floor(this.z));
	}
}
