package opencraft.world.chunk;

import opencraft.lib.entity.data.IntXYZ;

public class ChunkAddress {
	
	public String world;
	public IntXYZ coord;
	
	public ChunkAddress(String world, IntXYZ coord) {
		this.world = world;
		this.coord = coord;
	}
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof ChunkAddress)) return false;
		ChunkAddress adr = (ChunkAddress) other;
		return this.world.equals(adr.world) && this.coord.equals(adr.coord);
	}
}
