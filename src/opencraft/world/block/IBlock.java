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

import opencraft.lib.CubeDirection;
import opencraft.lib.INamed;
import opencraft.lib.entity.data.Box;
import opencraft.world.EntityWorld;
import opencraft.world.block.material.IBlockMeterial;

public interface IBlock extends INamed {
	
	/**
	 * @return unique string-id for each blocks ex) OpenCraft|air
	 */
	String getId();
	
	/**
	 * every tick the chunk call single block's this method which is stored in that chunk
	 * 
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @return block that will replace this block
	 */
	IBlock onChunkTick(EntityWorld world, int x, int y, int z);
	
	/**
	 * called when neighbor block is changed(placed, breaked, etc)
	 * 
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param side
	 */
	void onNeighborChanged(EntityWorld world, int x, int y, int z, CubeDirection side);
	
	/**
	 * get block's material
	 * you can return null if isFluid is true
	 * because you cannot break fluid block by hand :)
	 * 
	 * @return block's material
	 */
	IBlockMeterial getMetarial();
	
	/**
	 * get block's bounding box
	 * used for object collision detection
	 * if your block is fluid(like water, air, etc), return null
	 * 
	 * @return Axis Aligned Bounding Box of this block
	 */
	Box getAABB();
	
	boolean isAir();
	boolean isTransparent();
	boolean isFluid();
	boolean isReplaceable();
}
