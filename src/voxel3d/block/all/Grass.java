package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.XBlock;
import voxel3d.block.context.BlockOnBreakContext;
import voxel3d.block.utility.BlockBreakTimeUtility;
import voxel3d.item.Item;
import voxel3d.item.all.WheatSeedItem;
import voxel3d.utility.Vector2f;

public class Grass extends XBlock {
	
	private static Grass sharedInstance = new Grass();
	private static Vector2f[] uv = XBlock.getUVFromName("Grass");
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static Grass getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public int getLegacyID()
	{
		return 0xd2fdfa8c;
	}
	
	@Override
	public String getName()
	{
		return "Grass";
	}
	
	@Override
	public void onBreak(BlockOnBreakContext context)
	{
		context.dropItem(WheatSeedItem.getInstance());
	}
	
	@Override
	public double getBreakTime(Item tool)
	{
		return BlockBreakTimeUtility.getBreakTimeFoliage(tool);
	}

	@Override
	protected Vector2f[] getUV() 
	{
		return uv;
	}


}
