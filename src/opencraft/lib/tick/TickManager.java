/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage sandbox style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * class TickManager
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft.lib.tick;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import opencraft.OpenCraft;

import org.apache.log4j.Logger;

public class TickManager implements ICallable {
	
	public Logger log;
	
	private TickThread[] threadPool;
	private TickTimer timer;
	
	List<ITickable> listWork = Collections.synchronizedList(new LinkedList<ITickable>());
	List<ITickable> listDone = Collections.synchronizedList(new LinkedList<ITickable>());
	List<ITickable> listAdd = Collections.synchronizedList(new LinkedList<ITickable>());
	List<ITickable> listRemove = Collections.synchronizedList(new LinkedList<ITickable>());
	
	public TickManager(int sizeThreads, int tickTime) {
		
		this.log = OpenCraft.log;
		log.info("Starting TickManager");
		
		this.threadPool = new TickThread[sizeThreads];
		log.info("Creating " + sizeThreads + " TickThreads");
		for (int i=0; i<sizeThreads; i++) {
			threadPool[i] = new TickThread(this);
		}
		log.info("TickTimer is setted to " + tickTime);
		this.timer = new TickTimer(tickTime, this);
	}
	
	public void start() {
		log.info("Start ticking!");
		for (int i=0; i<this.threadPool.length; i++) {
			this.threadPool[i].start();
		}
		this.timer.start();
	}
	
	public void addTick(ITickable ticker) {
		log.debug("Adding tick work " + ticker.getClass().getName());
		if (ticker != null)
			synchronized(listAdd) {
				listAdd.add(ticker);
			}
	}
	
	public void removeTick(ITickable ticker) {
		log.debug("Removing tick work " + ticker.getClass().getName());
		if (ticker != null)
			synchronized(listRemove) {
				listRemove.add(ticker);
			}
	}
	
	synchronized ITickable getWork(ITickable work) {
		if (work != null)
			listDone.add(work);
		return listWork.size() > 0 ? listWork.remove(0) : null;
	}
	
	synchronized void doneWork() {
		boolean doneTicking = true;
		if (timer.isRunning) doneTicking = false;
		for (TickThread thread : this.threadPool) {
			if (thread.isWorking) doneTicking = false;
		}
		if (!doneTicking) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			synchronized(listRemove) {
				for (ITickable tick : this.listRemove) {
					this.listDone.remove(tick);
				}
			}
			synchronized(listAdd) {
				for (ITickable tick : this.listAdd) {
					if (tick != null)
						this.listDone.add(tick);
				}
			}
			listRemove.clear();
			listAdd.clear();
			
			listWork.clear();
			synchronized(listDone) {
				for (ITickable tick : this.listDone) {
					this.listWork.add(tick);
				}
			}
			
			listDone.clear();
			
			notifyAll();
		}
	}

	@Override
	public synchronized void call() {
		doneWork();
	}
}
