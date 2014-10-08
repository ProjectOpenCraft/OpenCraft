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

package opencraft.lib;

import opencraft.lib.entity.data.IntXYZ;

public enum CubeDirection {
	
	top(0, 1, 0), bottom(0, -1, 0), north(0, 0, -1), south(0, 0, 1), east(0, 0, 1), west(0, 0, -1), undefined(0, 0, 0);
	
	public static final int[] opposits = { 1, 0, 3, 2, 5, 4 };
	public static final int[] rotates  = { 1, 2, 3, 4, 5, 0 };
	
	public static IntXYZ getNextBlock(IntXYZ coord, CubeDirection side) {
		return coord.add(side.vector);
	}
	
	public final IntXYZ vector;
	
	CubeDirection(int x, int y, int z) {
		this.vector = new IntXYZ(x, y, z);
	}
	
	public CubeDirection getOpposit() {
		return CubeDirection.values()[opposits[this.ordinal()]];
	}
	
	public CubeDirection getRotate() {
		return CubeDirection.values()[rotates[this.ordinal()]];
	}
}
