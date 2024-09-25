package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.BlockSimulable;
import voxel3d.block.FluidAcceptingBlock;
import voxel3d.block.FluidContainer;
import voxel3d.block.TransparentCubeBlock;
import voxel3d.block.context.BlockOnSimulateContext;
import voxel3d.block.utility.FluidBlockUtility;
import voxel3d.utility.FaceDirection;
import voxel3d.utility.Vector2f;

public class GasVentBlock extends TransparentCubeBlock implements BlockSimulable, FluidAcceptingBlock {
	
	private static Vector2f[] uvs = TransparentCubeBlock.getUVFromName("Gas vent");
	
	private static final double globalVolume = 4096;
	private FluidContainer container = new FluidContainer(globalVolume);
	
	static {
		Block.setBlockDeserializerForLegacyID(getInstance());
	}
	
	public static GasVentBlock getInstance()
	{
		return new GasVentBlock();
	}
	
	@Override
	public Block getDataStreamableInstance()
	{
		return getInstance();
	}
	
	@Override
	public int getLegacyID()
	{
		return 0x94afe14b;
	}
	
	@Override
	public String getName()
	{
		return "Gas vent";
	}

	@Override
	protected Vector2f[] getUV(int index)
	{
		return uvs;
	}

	@Override
	public void onSimulate(BlockOnSimulateContext context) 
	{
		container.clear();
		FluidBlockUtility.exposeAllInterface(this, context);
	}

	@Override
	public FluidContainer getInterface(FaceDirection side) 
	{
		return container;
	}

}
