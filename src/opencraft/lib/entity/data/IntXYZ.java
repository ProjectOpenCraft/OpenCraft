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

public class IntXYZ extends Entity {
	
	
	public final int x, y, z;
	
	public IntXYZ() {
		this.x = Integer.MAX_VALUE;
		this.y = Integer.MAX_VALUE;
		this.z = Integer.MAX_VALUE;
	}
	
	public IntXYZ(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public IntXYZ(String str) {
		if (str.indexOf("(") != 0 || str.indexOf(")") != str.length() -1) {
			this.x = Integer.MAX_VALUE;
			this.y = Integer.MAX_VALUE;
			this.z = Integer.MAX_VALUE;
		} else {
			String[] arr = str.replaceAll("(", "").replaceAll(")", "").split(",");
			if (arr.length < 3) {
				this.x = Integer.MAX_VALUE;
				this.y = Integer.MAX_VALUE;
				this.z = Integer.MAX_VALUE;
			} else {
				int valX = Integer.MAX_VALUE;
				int valY = Integer.MAX_VALUE;
				int valZ = Integer.MAX_VALUE;
				try {
					valX = Integer.parseInt(arr[0]);
					valY = Integer.parseInt(arr[1]);
					valZ = Integer.parseInt(arr[2]);
				} catch(NumberFormatException e) {
					this.x = Integer.MAX_VALUE;
					this.y = Integer.MAX_VALUE;
					this.z = Integer.MAX_VALUE;
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
		return new StringBuilder("(").append(x).append(",").append(y).append(",").append(z).append(")").toString();
	}
	
	public IntXYZ add(IntXYZ adder) {
		return new IntXYZ(this.x + adder.x, this.y + adder.y, this.z + adder.z);
	}
	
	public DoubleXYZ add(DoubleXYZ adder) {
		return new DoubleXYZ((double)this.x + adder.x, (double)this.y + adder.y, (double)this.z + adder.z);
	}
	
	public IntXYZ multiply(int num) {
		return new IntXYZ(this.x * num, this.y * num, this.z * num);
	}
	
	public DoubleXYZ toDouble() {
		return new DoubleXYZ(this.x, this.y, this.z);
	}
}
