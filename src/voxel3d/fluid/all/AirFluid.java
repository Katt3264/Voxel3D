package voxel3d.fluid.all;

import voxel3d.fluid.Fluid;

public class AirFluid extends Fluid {
	
	private static AirFluid sharedInstance = new AirFluid();
	
	static {
		Fluid.setFluid(sharedInstance);
	}
	
	public static AirFluid getInstance()
	{
		return sharedInstance;
	}

	@Override
	public int getLegacyID() 
	{
		return 0x5f267d7d;
	}

	@Override
	public String getName() 
	{
		return "Air";
	}
}
