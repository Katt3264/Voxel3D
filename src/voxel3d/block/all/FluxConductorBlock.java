package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.BlockSimulable;
import voxel3d.block.FluxTransferBlock;
import voxel3d.block.TransparentCubeBlock;
import voxel3d.block.context.BlockOnSimulateContext;
import voxel3d.block.utility.FluxBlockUtility;
import voxel3d.utility.TextUtill;
import voxel3d.utility.Vector2f;

public class FluxConductorBlock extends TransparentCubeBlock implements BlockSimulable, FluxTransferBlock {
	
	private static Vector2f[] uvs = TransparentCubeBlock.getUVFromName("Flux conductor");
	
	private double thermalEnergy = 0;
	
	static {
		Block.setBlockDeserializerForLegacyID(getInstance());
	}
	
	public static FluxConductorBlock getInstance()
	{
		return new FluxConductorBlock();
	}
	
	@Override
	public Block getBlockInstance()
	{
		return getInstance();
	}
	
	
	@Override
	public int getLegacyID()
	{
		return 0x126ec4f5;
	}
	
	@Override
	public String getName()
	{
		return "Flux conductor";
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
				+ " \nFlux:" + TextUtill.toUnit(thermalEnergy);
	}

	@Override
	public void onSimulate(BlockOnSimulateContext context) 
	{
		FluxBlockUtility.ThermalInteract(this, context);
	}

	@Override
	public double getFlux()
	{
		return thermalEnergy;
	}

	@Override
	public void addFlux(double value) 
	{
		thermalEnergy += value;
	}

	@Override
	public double getFluxConductance() 
	{
		return 1;
	}

}
