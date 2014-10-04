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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import opencraft.OpenCraft;
import opencraft.event.mod.EventLoadMod;
import opencraft.event.mod.EventPostLoadMod;
import opencraft.event.mod.EventPreLoadMod;
import opencraft.event.mod.EventReceiveModMessage;
import opencraft.event.mod.EventSendModMessage;
import opencraft.lib.event.EventDispatcher;
import opencraft.lib.event.IEvent;
import opencraft.lib.event.IEventDispatcher;
import opencraft.lib.event.IEventHandler;
import opencraft.lib.event.IEventListener;

public class ModManager implements IEventHandler {
	
	Logger log;
	Map<String, IMod> listMod;
	
	IEventDispatcher ed = new EventDispatcher();
	
	public ModManager() {
		this.listMod = new HashMap<String, IMod>();
		this.log = OpenCraft.log;
		
		this.event().addListener(new ModMessageListener(this.listMod));
	}
	
	public void start() {
		log.info("Starting ModManager");
		
		log.info("Searching mod directory");
		File[] mods = OpenCraft.modDir.listFiles();
		for (File modFile : mods) {
			IMod mod = ClassLoader.loadMod(modFile);
			if (mod != null) {
				this.listMod.put(mod.getName(), mod);
			}
		}
		log.info("Found " + this.listMod.size() + " mods");
		
		log.info("Initializing mods");
		for (IMod mod : this.listMod.values()) {
			mod.init();
		}
		
		List<String> listModName = new ArrayList<String>();
		for (IMod mod : this.listMod.values()) {
			listModName.add(mod.getName());
		}
		
		log.info("Start pre-load phase");
		for (IMod mod : this.listMod.values()) {
			mod.event().emit(new EventPreLoadMod(listModName));
		}
		
		log.info("Start load phase");
		for (IMod mod : this.listMod.values()) {
			mod.event().emit(new EventLoadMod());
		}
		
		log.info("Start post-load phase");
		for (IMod mod : this.listMod.values()) {
			mod.event().emit(new EventPostLoadMod());
		}
	}

	@Override
	public IEventDispatcher event() {
		return ed;
	}
	
	class ModMessageListener implements IEventListener {
		
		Map<String, IMod> listMod;
		
		public ModMessageListener(Map<String, IMod> listMod) {
			this.listMod = listMod;
		}

		@Override
		public Class<? extends IEvent> getEventClass() {
			return EventSendModMessage.class;
		}

		@Override
		public IEvent handleEvent(IEvent event) {
			EventSendModMessage e = (EventSendModMessage) event;
			this.listMod.get(e.target).event().emit(new EventReceiveModMessage(e.name, e.cargo));
			return e;
		}
	}
}
