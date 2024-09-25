package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.XBlock;
import voxel3d.block.context.BlockOnBreakContext;
import voxel3d.block.utility.BlockBreakTimeUtility;
import voxel3d.item.Item;
import voxel3d.item.all.PlantSeedItem;
import voxel3d.utility.Vector2f;

public class SkyGrass extends XBlock {
	
	private static SkyGrass sharedInstance = new SkyGrass();
	private static Vector2f[] uv = XBlock.getUVFromName("Sky grass");
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static SkyGrass getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public int getLegacyID()
	{
		return 0x0ae81278;
	}
	
	@Override
	public String getName()
	{
		return "Sky grass";
	}
	
	@Override
	public void onBreak(BlockOnBreakContext context)
	{
		context.dropItem(PlantSeedItem.getInstance());
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
