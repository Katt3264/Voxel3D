package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.TransparentCubeBlock;
import voxel3d.block.context.BlockOnBreakContext;
import voxel3d.block.utility.BlockBreakTimeUtility;
import voxel3d.item.Item;
import voxel3d.item.all.StickItem;
import voxel3d.utility.Vector2f;

public class CrimsonLeavesBlock extends TransparentCubeBlock {

	private static CrimsonLeavesBlock sharedInstance = new CrimsonLeavesBlock();
	private static Vector2f[] uvs = TransparentCubeBlock.getUVFromName("Crimson leaves");
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static CrimsonLeavesBlock getInstance()
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
		return 0xd973da9e;
	}

	@Override
	public String getName() 
	{
		return "Crimson leaves";
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
