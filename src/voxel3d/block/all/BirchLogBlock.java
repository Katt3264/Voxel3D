package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.SolidCubeBlock;
import voxel3d.block.utility.BlockBreakTimeUtility;
import voxel3d.item.Item;
import voxel3d.utility.Vector2f;

public class BirchLogBlock extends SolidCubeBlock {
	
	private static BirchLogBlock sharedInstance = new BirchLogBlock();
	private static Vector2f[][] uvs = SolidCubeBlock.getUVsFromTopSide("Birch log top", "Birch log");
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static BirchLogBlock getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public int getLegacyID()
	{
		return 0xf584f210;
	}
	
	@Override
	public String getName()
	{
		return "Birch log";
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
