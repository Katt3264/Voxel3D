package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.SolidCubeBlock;
import voxel3d.block.utility.BlockBreakTimeUtility;
import voxel3d.item.Item;
import voxel3d.utility.Vector2f;

public class CrimsonLogBlock extends SolidCubeBlock {
	
	private static CrimsonLogBlock sharedInstance = new CrimsonLogBlock();
	private static Vector2f[][] uvs = SolidCubeBlock.getUVsFromTopSide("Crimson log top", "Crimson log");
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static CrimsonLogBlock getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public int getLegacyID()
	{
		return 0x9fe83839;
	}
	
	@Override
	public String getName()
	{
		return "Crimson log";
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
