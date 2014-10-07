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

package opencraft;

import java.io.File;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import opencraft.server.OpenCraftServer;
import opencraft.world.block.Block;
import opencraft.world.block.BlockAir;

public class OpenCraft {
	
	public static final File runDir = new File(".");
	public static final File modDir = new File(runDir, "mods");
	public static final File worldDir = new File(runDir, "worlds");
	public static final File playerDir = new File(worldDir, "players");
	
	public static Logger log = LogManager.getLogger("[OpenCraft]");
	
	public static void main(String[] args) {
		BasicConfigurator.configure();
		createDir();
		registerBlock();
		OpenCraftServer.instance().start();
	}
	
	private static void registerBlock() {
		Block.registry.registerBlock(new BlockAir());
	}
	
	private static void createDir() {
		modDir.mkdirs();
		worldDir.mkdirs();
		playerDir.mkdirs();
	}
}
