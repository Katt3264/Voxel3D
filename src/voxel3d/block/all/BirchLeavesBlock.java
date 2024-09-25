package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.TransparentCubeBlock;
import voxel3d.block.context.BlockOnBreakContext;
import voxel3d.block.utility.BlockBreakTimeUtility;
import voxel3d.item.Item;
import voxel3d.item.all.StickItem;
import voxel3d.utility.Vector2f;

public class BirchLeavesBlock extends TransparentCubeBlock {

	private static BirchLeavesBlock sharedInstance = new BirchLeavesBlock();
	private static Vector2f[] uvs = TransparentCubeBlock.getUVFromName("Birch leaves");
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static BirchLeavesBlock getInstance()
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
		return 0x0218bc23;
	}

	@Override
	public String getName() 
	{
		return "Birch leaves";
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
