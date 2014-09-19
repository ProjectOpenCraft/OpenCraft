/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage sandbox style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * class ModManager
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft.mod;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import opencraft.OpenCraft;
import opencraft.event.mod.EventLoadMod;
import opencraft.event.mod.EventPostLoadMod;
import opencraft.event.mod.EventPreLoadMod;

public class ModManager {
	
	Logger log;
	List<IMod> listMod;
	
	public ModManager() {
		this.listMod = new ArrayList<IMod>();
		this.log = OpenCraft.log;
	}
	
	public void start() {
		log.info("Starting ModManager");
		
		log.info("Searching mod directory");
		File[] mods = OpenCraft.modDir.listFiles();
		for (File modFile : mods) {
			IMod mod = ClassLoader.loadMod(modFile);
			if (mod != null) {
				this.listMod.add(mod);
			}
		}
		log.info("Found " + this.listMod.size() + " mods");
		
		log.info("Initializing mods");
		for (IMod mod : this.listMod) {
			mod.init();
		}
		
		List<String> listModName = new ArrayList<String>();
		for (IMod mod : this.listMod) {
			listModName.add(mod.getName());
		}
		
		log.info("Start pre-load phase");
		for (IMod mod : this.listMod) {
			mod.event().emit(new EventPreLoadMod(listModName));
		}
		
		log.info("Start load phase");
		for (IMod mod : this.listMod) {
			mod.event().emit(new EventLoadMod());
		}
		
		log.info("Start post-load phase");
		for (IMod mod : this.listMod) {
			mod.event().emit(new EventPostLoadMod());
		}
	}
}
