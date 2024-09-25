package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.XBlock;
import voxel3d.block.utility.BlockBreakTimeUtility;
import voxel3d.item.Item;
import voxel3d.utility.Vector2f;

public class EtherealFlower extends XBlock {
	
	private static EtherealFlower sharedInstance = new EtherealFlower();
	private static Vector2f[] uv = XBlock.getUVFromName("Ethereal flower");
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static EtherealFlower getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public int getLegacyID()
	{
		return 0x69215dc0;
	}
	
	@Override
	public String getName()
	{
		return "Ethereal flower";
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
	
	@Override
	public double getBreakTime(Item tool)
	{
		return BlockBreakTimeUtility.getBreakTimeFoliage(tool);
	}

}
