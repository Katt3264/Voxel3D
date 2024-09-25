package voxel3d.fluid.all;

import voxel3d.fluid.Fluid;

public class WaterFluid extends Fluid {
	
	private static WaterFluid sharedInstance = new WaterFluid();
	
	static {
		Fluid.setFluid(sharedInstance);
	}
	
	public static WaterFluid getInstance()
	{
		return sharedInstance;
	}

	@Override
	public int getLegacyID() 
	{
		return 0x06aed8ef;
	}

	@Override
	public String getName() 
	{
		return "Water";
	}

}
