package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.SolidCubeBlock;
import voxel3d.block.utility.BlockBreakTimeUtility;
import voxel3d.item.Item;
import voxel3d.utility.Vector2f;

public class OakLogBlock extends SolidCubeBlock {
	
	private static OakLogBlock sharedInstance = new OakLogBlock();
	private static Vector2f[][] uvs = SolidCubeBlock.getUVsFromTopSide("Oak log top", "Oak log");
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static OakLogBlock getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public int getLegacyID()
	{
		return 0x66e6f818;
	}
	
	@Override
	public String getName()
	{
		return "Oak log";
	}

	@Override
	public Vector2f[] getUV(int index)
	{
		return uvs[index];
	}
	
	@Override
	public double getBreakTime(Item tool)
	{
		return BlockBreakTimeUtility.getBreakTimeWood(tool);
	}

}
