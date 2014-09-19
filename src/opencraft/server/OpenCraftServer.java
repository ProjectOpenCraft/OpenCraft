/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage sandbox style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * class OpenCraftServer
 * 
 * author - Moonrise1275
 * All rights reserved.
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
				props.store(new FileOutputStream(propsF), "");
			}
			
			this.clientManager = new ClientManager(Integer.parseInt(props.getProperty("port")));
			this.worldManager = new WorldManager();
			this.tickManager = new TickManager(Integer.parseInt(props.getProperty("tick_threads")), Integer.parseInt(props.getProperty("tick_time_ms")));
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
