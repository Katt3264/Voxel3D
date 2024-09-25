package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.BlockSimulable;
import voxel3d.block.FluidAcceptingBlock;
import voxel3d.block.FluidContainer;
import voxel3d.block.TransparentCubeBlock;
import voxel3d.block.context.BlockOnSimulateContext;
import voxel3d.block.utility.FluidBlockUtility;
import voxel3d.fluid.all.AirFluid;
import voxel3d.utility.FaceDirection;
import voxel3d.utility.Vector2f;

public class FanBlock extends TransparentCubeBlock implements BlockSimulable, FluidAcceptingBlock {
	
	private static Vector2f[] uvs = TransparentCubeBlock.getUVFromName("Fan");
	
	private FluidContainer container = new FluidContainer(1);
	
	static {
		Block.setBlockDeserializerForLegacyID(getInstance());
	}
	
	public static FanBlock getInstance()
	{
		return new FanBlock();
	}
	
	@Override
	public Block getDataStreamableInstance()
	{
		return getInstance();
	}
	
	@Override
	public int getLegacyID()
	{
		return 0x4607dbb2;
	}
	
	@Override
	public String getName()
	{
		return "Fan";
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
		double maxAcc = container.size - container.getMass();
		double acc = Math.min(maxAcc, context.getDeltaTime() * 0.01);
		container.addFluid(AirFluid.getInstance(), acc);
		
		FluidBlockUtility.exposeAllInterface(this, context);
	}

	@Override
	public FluidContainer getInterface(FaceDirection side) 
	{
		return container;
	}

}
