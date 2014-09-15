/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage minecraft style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * class TickTimer
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft.lib.tick;

public class TickTimer extends Thread {
	
	public final int tickTime;
	private final ICallable callback;
	
	boolean isRunning = true;
	
	public TickTimer(int tickTime, ICallable callback) {
		this.tickTime = tickTime;
		this.callback = callback;
	}
	
	public void run() {
		while (!this.isInterrupted()) {
			try {
				this.isRunning = true;
				Thread.sleep(tickTime);
				this.isRunning = false;
				callback.call();
			} catch (InterruptedException e) {}
		}
	}
}
