package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.BlockSimulable;
import voxel3d.block.FluxTransferBlock;
import voxel3d.block.TransparentCubeBlock;
import voxel3d.block.context.BlockOnSimulateContext;
import voxel3d.block.utility.FluxBlockUtility;
import voxel3d.utility.TextUtill;
import voxel3d.utility.Vector2f;

public class FluxResistorBlock extends TransparentCubeBlock implements BlockSimulable, FluxTransferBlock {
	
	private static Vector2f[] uvs = TransparentCubeBlock.getUVFromName("Flux resistor");
	
	private double flux = 0;
	
	static {
		Block.setBlockDeserializerForLegacyID(getInstance());
	}
	
	public static FluxResistorBlock getInstance()
	{
		return new FluxResistorBlock();
	}
	
	@Override
	public Block getBlockInstance()
	{
		return getInstance();
	}
	
	@Override
	public int getLegacyID()
	{
		return 0x92f5a27b;
	}
	
	@Override
	public String getName()
	{
		return "Flux resistor";
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
	public void onSimulate(BlockOnSimulateContext context) 
	{
		FluxBlockUtility.ThermalInteract(this, context);
	}

	@Override
	public double getFlux()
	{
		return flux;
	}

	@Override
	public void addFlux(double value) 
	{
		flux += value;
	}

	@Override
	public double getFluxConductance() 
	{
		return 0.1;
	}
}
