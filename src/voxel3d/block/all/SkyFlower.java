package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.XBlock;
import voxel3d.block.utility.BlockBreakTimeUtility;
import voxel3d.item.Item;
import voxel3d.utility.Vector2f;

public class SkyFlower extends XBlock {
	
	private static SkyFlower sharedInstance = new SkyFlower();
	private static Vector2f[] uv = XBlock.getUVFromName("Sky flower");
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static SkyFlower getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public int getLegacyID()
	{
		return 0x3eda2b64;
	}
	
	@Override
	public String getName()
	{
		return "Sky flower";
	}
	
	@Override
	public double getBreakTime(Item tool)
	{
		return BlockBreakTimeUtility.getBreakTimeFoliage(tool);
	}
	
	@Override
	public float getRedLight()
	{
		return 0.5f;
	}
	
	@Override
	public float getGreenLight()
	{
		return 0.5f;
	}
	
	@Override
	public float getBlueLight()
	{
		return 0.5f;
	}

	@Override
	protected Vector2f[] getUV() 
	{
		return uv;
	}

}
