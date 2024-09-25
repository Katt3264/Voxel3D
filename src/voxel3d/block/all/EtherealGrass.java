package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.XBlock;
import voxel3d.block.context.BlockOnBreakContext;
import voxel3d.block.utility.BlockBreakTimeUtility;
import voxel3d.item.Item;
import voxel3d.item.all.PlantSeedItem;
import voxel3d.utility.Vector2f;

public class EtherealGrass extends XBlock {
	
	private static EtherealGrass sharedInstance = new EtherealGrass();
	private static Vector2f[] uv = XBlock.getUVFromName("Ethereal grass");
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static EtherealGrass getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public int getLegacyID()
	{
		return 0x4817a8af;
	}
	
	@Override
	public String getName()
	{
		return "Ethereal grass";
	}
	
	@Override
	public void onBreak(BlockOnBreakContext context)
	{
		context.dropItem(PlantSeedItem.getInstance());
	}

	@Override
	protected Vector2f[] getUV() 
	{
		return uv;
	}
	
	@Override
	public double getBreakTime(Item tool)
	{
		return BlockBreakTimeUtility.getBreakTimeFoliage(tool);
	}

}
