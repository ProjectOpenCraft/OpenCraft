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


package opencraft.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import opencraft.OpenCraft;
import opencraft.lib.tick.TickManager;
import opencraft.mod.ModManager;
import opencraft.server.client.ClientManager;
import opencraft.world.WorldManager;

public class OpenCraftServer {
	
	private static OpenCraftServer instance;
	
	public Logger log;
	
	public static OpenCraftServer instance() {
		if (instance == null)
			instance = new OpenCraftServer();
		return instance;
	}
	
	private ClientManager clientManager;
	private WorldManager worldManager;
	private TickManager tickManager;
	private ModManager modManager;
	
	private OpenCraftServer() {
		
	}
	
	public void start() {
		this.log = OpenCraft.log;
		log.info("Starting OpenCraft Server");
		try {
			Properties props = new Properties();
			File propsF = new File(OpenCraft.runDir, "server.properties");
			if (propsF.exists()) {
				props.load(new FileInputStream(propsF));
			} else {
				props.setProperty("server_name", "OpenCraft Server");
				props.setProperty("port", "39372");
				props.setProperty("tick_threads", "100");
				props.setProperty("tick_time_ms", "50");
				props.setProperty("chunk_load_per_tick", "100");
				props.setProperty("chunk_save_delay", "2.0");
				props.store(new FileOutputStream(propsF), "");
			}
			
			this.clientManager = new ClientManager(Integer.parseInt(props.getProperty("port", "39372")));
			this.worldManager = new WorldManager(Integer.parseInt(props.getProperty("chunk_load_per_tick", "100")), Double.parseDouble(props.getProperty("chunk_save_delay", "2.0")));
			this.tickManager = new TickManager(Integer.parseInt(props.getProperty("tick_threads", "100")), Integer.parseInt(props.getProperty("tick_time_ms", "50")));
			this.modManager = new ModManager();
			
			this.modManager.start();
			this.tickManager.start();
			this.clientManager.start();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
	
	public ClientManager getClientManager() {
		return this.clientManager;
	}
	
	public WorldManager getWorldManager() {
		return this.worldManager;
	}
	
	public TickManager getTickManager() {
		return this.tickManager;
	}
	
	public ModManager getModManager() {
		return this.modManager;
	}
}
