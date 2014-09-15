/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage minecraft style modding.
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

public class TickManager implements ICallable {
	
	private TickThread[] threadPool;
	private TickTimer timer;
	
	List<ITickable> listWork = Collections.synchronizedList(new LinkedList<ITickable>());
	List<ITickable> listDone = Collections.synchronizedList(new LinkedList<ITickable>());
	List<ITickable> listAdd = Collections.synchronizedList(new LinkedList<ITickable>());
	List<ITickable> listRemove = Collections.synchronizedList(new LinkedList<ITickable>());
	
	public TickManager(int sizeThreads, int tickTime) {
		this.threadPool = new TickThread[sizeThreads];
		for (int i=0; i<sizeThreads; i++) {
			threadPool[i] = new TickThread(this);
		}
		this.timer = new TickTimer(tickTime, this);
	}
	
	public void start() {
		for (int i=0; i<this.threadPool.length; i++) {
			this.threadPool[i].start();
		}
	}
	
	public void addTick(ITickable ticker) {
		listAdd.add(ticker);
	}
	
	public void removeTick(ITickable ticker) {
		listRemove.add(ticker);
	}
	
	synchronized ITickable getWork(ITickable work) {
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
			listDone.removeAll(listRemove);
			listDone.addAll(listAdd);
			listRemove.clear();
			listAdd.clear();
			
			listWork.clear();
			listWork.addAll(listDone);
			
			listDone.clear();
			
			notifyAll();
		}
	}

	@Override
	public synchronized void call() {
		try {
			wait();
		} catch (InterruptedException e) {}
	}
}
