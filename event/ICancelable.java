/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage minecraft style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * interface ICancelable
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft.event;

public interface ICancelable {
	
	public void setCanceled(boolean cancel);
	public boolean isCanceled();
}
