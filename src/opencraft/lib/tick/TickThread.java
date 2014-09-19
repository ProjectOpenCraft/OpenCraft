/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage sandbox style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * class TickThread
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft.lib.tick;

public class TickThread extends Thread {
	
	boolean isWorking = true;
	ITickable curWork = null;
	
	TickManager manager;
	
	TickThread(TickManager m) {
		this.manager = m;
		this.setName("TickThread");
	}
	
	public void run() {
		while (!this.isInterrupted()) {
			this.isWorking = true;
			curWork = manager.getWork(curWork);
			if (curWork == null) {
				this.isWorking = false;
				manager.doneWork();
			} else {
				curWork.tick();
			}
		}
	}
}
