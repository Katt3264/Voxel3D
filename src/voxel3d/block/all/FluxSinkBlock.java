package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.FluxTransferBlock;
import voxel3d.block.TransparentCubeBlock;
import voxel3d.utility.TextUtill;
import voxel3d.utility.Vector2f;

public class FluxSinkBlock extends TransparentCubeBlock implements FluxTransferBlock {
	
	private static FluxSinkBlock sharedInstance = new FluxSinkBlock();
	private static Vector2f[] uvs = TransparentCubeBlock.getUVFromName("Flux sink");
	
	static {
		Block.setBlockDeserializerForLegacyID(getInstance());
	}
	
	public static FluxSinkBlock getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public Block getDataStreamableInstance()
	{
		return getInstance();
	}
	
	@Override
	public int getLegacyID()
	{
		return 0xc51efe4d;
	}
	
	@Override
	public String getName()
	{
		return "Flux sink";
	}

	@Override
	protected Vector2f[] getUV(int index)
	{
		return uvs;
	}
	
	@Override
	public String getInfo()
	{
		return super.getInfo() 
				+ " \nFlux:" + TextUtill.toUnit(getFlux());
	}

	@Override
	public double getFlux()
	{
		return 0;
	}

	@Override
	public void addFlux(double value) 
	{
	}

	@Override
	public double getFluxConductance() 
	{
		return 1;
	}
}
