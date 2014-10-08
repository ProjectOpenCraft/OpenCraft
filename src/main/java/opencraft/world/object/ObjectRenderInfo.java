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

package opencraft.world.object;

import opencraft.lib.entity.Entity;
import opencraft.lib.entity.data.DoubleXYZ;

public class ObjectRenderInfo extends Entity {
	
	public DoubleXYZ coord;
	public double angle;
	public long type;
	
	public ObjectRenderInfo() {
		this.coord = null;
		this.angle = 0d;
		this.type = 0l;
	}
	
	public ObjectRenderInfo(DoubleXYZ coord, double angle, long type) {
		this.coord = coord;
		this.angle = angle;
		this.type = type;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ObjectRenderInfo) {
			ObjectRenderInfo o = (ObjectRenderInfo)obj;
			return this.coord.equals(o) && this.angle == o.angle && this.type == o.type; 
		} else return false;
	}
}
