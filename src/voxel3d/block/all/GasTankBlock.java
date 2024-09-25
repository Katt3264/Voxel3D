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

public class GasTankBlock extends TransparentCubeBlock implements BlockSimulable, FluidAcceptingBlock {
	
	private static Vector2f[] uvs = TransparentCubeBlock.getUVFromName("Gas tank");
	
	private static final double volume = 1;
	private FluidContainer container = new FluidContainer(volume);
	
	static {
		Block.setBlockDeserializerForLegacyID(getInstance());
	}
	
	public static GasTankBlock getInstance()
	{
		return new GasTankBlock();
	}
	
	@Override
	public Block getDataStreamableInstance()
	{
		return getInstance();
	}
	
	@Override
	public int getLegacyID()
	{
		return 0x38b69cca;
	}
	
	@Override
	public String getName()
	{
		return "Gas tank";
	}

	@Override
	protected Vector2f[] getUV(int index)
	{
		return uvs;
	}
	
	@Override
	public String getInfo()
	{
		return super.getInfo() + " " + container.getInfo();
	}

	@Override
	public void onSimulate(BlockOnSimulateContext context) 
	{
		FluidBlockUtility.exposeAllInterface(this, context);
	}

	@Override
	public FluidContainer getInterface(FaceDirection side) 
	{
		return container;
	}

}
