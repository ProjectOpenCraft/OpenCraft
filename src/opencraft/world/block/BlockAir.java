package opencraft.world.block;

public class BlockAir extends Block {

	@Override
	public String getId() {
		return "OpenCraft|blockAir";
	}

	@Override
	public String getName() {
		return "air";
	}
	
	@Override
	public boolean isTransparent() {
		return true;
	}
	
	@Override
	public boolean isReplaceable() {
		return true;
	}
	
	@Override
	public boolean isFluid() {
		return true;
	}
}
