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

public class CompressorBlock extends TransparentCubeBlock implements BlockSimulable, FluidAcceptingBlock {
	
	private static Vector2f[] uvs = TransparentCubeBlock.getUVFromName("Compressor");
	
	private FluidContainer high = new FluidContainer(0.5);
	private FluidContainer low = new FluidContainer(0.5);
	
	static {
		Block.setBlockDeserializerForLegacyID(getInstance());
	}
	
	public static CompressorBlock getInstance()
	{
		return new CompressorBlock();
	}
	
	@Override
	public Block getDataStreamableInstance()
	{
		return getInstance();
	}
	
	@Override
	public int getLegacyID()
	{
		return 0x82717659;
	}
	
	@Override
	public String getName()
	{
		return "Compressor";
	}

	@Override
	protected Vector2f[] getUV(int index)
	{
		return uvs;
	}
	
	@Override
	public String getInfo()
	{
		return super.getInfo() + "\n\nOutput:" + high.getInfo() + "\nInput:" + low.getInfo();
	}
	
	@Override
	public void onSimulate(BlockOnSimulateContext context) 
	{
		double maxAcc = Math.min(high.size - high.getMass(), low.getMass());
		double acc = Math.min(maxAcc, context.getDeltaTime() * 0.1);
		double transferFactor = (acc / (low.getMass() + 1E-9));
		for(int i = 0; i < low.elements.length; i++)
		{
			double transfer = low.elements[i] * transferFactor;
			low.elements[i] -= transfer;
			high.elements[i] += transfer;
		}
		FluidBlockUtility.exposeAllInterface(this, context);
	}

	@Override
	public FluidContainer getInterface(FaceDirection side) 
	{
		if(side == FaceDirection.YP)
		{
			return high;
		}
		else if(side == FaceDirection.YM)
		{
			return low;
		}
		else
		{
			return null;
		}
	}

}
