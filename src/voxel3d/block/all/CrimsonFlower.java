package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.XBlock;
import voxel3d.block.utility.BlockBreakTimeUtility;
import voxel3d.item.Item;
import voxel3d.utility.Vector2f;

public class CrimsonFlower extends XBlock {
	
	private static CrimsonFlower sharedInstance = new CrimsonFlower();
	private static Vector2f[] uv = XBlock.getUVFromName("Crimson flower");
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static CrimsonFlower getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public int getLegacyID()
	{
		return 0x7823a4f3;
	}
	
	@Override
	public String getName()
	{
		return "Crimson flower";
	}
	
	@Override
	public float getRedLight()
	{
		return 0.5f;
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
