/*
 * OpenCraft - Build your open world!
 * 
 * OpenCraft is a open source game platform to encourage sandbox style modding.
 * All code is written by it's own author, from zero-based.
 * This project is distributed under MIT license.
 * 
 * ------------------------------------------------------------------------------
 * 
 * class Packet
 * 
 * author - Moonrise1275
 * All rights reserved.
 */

package opencraft.lib.event.packet;

import java.io.BufferedOutputStream;
import opencraft.lib.entity.Entity;
import opencraft.lib.event.IEvent;

public abstract class Packet extends Entity implements IEvent {
	public void sendBinary(BufferedOutputStream out) {}
}
