package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.SolidCubeBlock;
import voxel3d.block.utility.BlockBreakTimeUtility;
import voxel3d.item.Item;
import voxel3d.utility.Vector2f;

public class CherryLogBlock extends SolidCubeBlock {
	
	private static CherryLogBlock sharedInstance = new CherryLogBlock();
	private static Vector2f[][] uvs = SolidCubeBlock.getUVsFromTopSide("Cherry log top", "Cherry log");
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static CherryLogBlock getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public int getLegacyID()
	{
		return 0x8171c405;
	}
	
	@Override
	public String getName()
	{
		return "Cherry log";
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
