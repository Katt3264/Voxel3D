package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.TransparentCubeBlock;
import voxel3d.block.context.BlockOnBreakContext;
import voxel3d.block.utility.BlockBreakTimeUtility;
import voxel3d.item.Item;
import voxel3d.item.all.StickItem;
import voxel3d.utility.Vector2f;

public class OakLeavesBlock extends TransparentCubeBlock {

	private static OakLeavesBlock sharedInstance = new OakLeavesBlock();
	private static Vector2f[] uvs = TransparentCubeBlock.getUVFromName("Oak leaves");
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static OakLeavesBlock getInstance()
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
		return 0xc7905d4a;
	}

	@Override
	public String getName() 
	{
		return "Oak leaves";
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
