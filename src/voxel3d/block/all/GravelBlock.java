package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.SolidCubeBlock;
import voxel3d.block.context.BlockOnBreakContext;
import voxel3d.block.utility.BlockBreakTimeUtility;
import voxel3d.item.Item;
import voxel3d.item.all.FlintItem;
import voxel3d.utility.Vector2f;

public class GravelBlock extends SolidCubeBlock {
	
	private static GravelBlock sharedInstance = new GravelBlock();
	private static Vector2f[] uv = SolidCubeBlock.getUVFromName("Gravel");
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static GravelBlock getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public int getLegacyID()
	{
		return 0xbf00e2c9;
	}
	
	@Override
	public String getName()
	{
		return "Gravel";
	}

	@Override
	public Vector2f[] getUV(int index)
	{
		return uv;
	}
	
	@Override
	public void onBreak(BlockOnBreakContext context)
	{
		context.dropItem(FlintItem.getInstance());
	}
	
	@Override
	public double getBreakTime(Item tool)
	{
		return BlockBreakTimeUtility.getBreakTimeSoil(tool);
	}

}
