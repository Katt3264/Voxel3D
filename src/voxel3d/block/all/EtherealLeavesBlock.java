package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.TransparentCubeBlock;
import voxel3d.block.context.BlockOnBreakContext;
import voxel3d.block.utility.BlockBreakTimeUtility;
import voxel3d.item.Item;
import voxel3d.item.all.StickItem;
import voxel3d.utility.Vector2f;

public class EtherealLeavesBlock extends TransparentCubeBlock {

	private static EtherealLeavesBlock sharedInstance = new EtherealLeavesBlock();
	private static Vector2f[] uvs = TransparentCubeBlock.getUVFromName("Ethereal leaves");
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static EtherealLeavesBlock getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	protected Vector2f[] getUV(int index) 
	{
		return uvs;
	}

	@Override
	public int getLegacyID() 
	{
		return 0xdd9cd05c;
	}

	@Override
	public String getName() 
	{
		return "Ethereal leaves";
	}
	
	@Override
	public void onBreak(BlockOnBreakContext context)
	{
		context.dropItem(StickItem.getInstance());
	}
	
	@Override
	public double getBreakTime(Item tool)
	{
		return BlockBreakTimeUtility.getBreakTimeFoliage(tool);
	}

}
