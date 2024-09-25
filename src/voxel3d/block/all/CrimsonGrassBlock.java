package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.SolidCubeBlock;
import voxel3d.block.context.BlockOnBreakContext;
import voxel3d.block.utility.BlockBreakTimeUtility;
import voxel3d.item.Item;
import voxel3d.utility.Vector2f;

public class CrimsonGrassBlock extends SolidCubeBlock {
	
	private static CrimsonGrassBlock sharedInstance = new CrimsonGrassBlock();
	private static Vector2f[][] uvs = SolidCubeBlock.getUVsFromTopSideBottom("Crimson grass block top", "Crimson grass block", "Dirt");
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static CrimsonGrassBlock getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public int getLegacyID()
	{
		return 0x3ccffdee;
	}
	
	@Override
	public String getName()
	{
		return "Crimson grass block";
	}

	@Override
	public Vector2f[] getUV(int index)
	{
		return uvs[index];
	}
	
	@Override
	public void onBreak(BlockOnBreakContext context)
	{
		context.dropItem(DirtBlock.getInstance().getItem());
	}
	
	@Override
	public double getBreakTime(Item tool)
	{
		return BlockBreakTimeUtility.getBreakTimeSoil(tool);
	}

}
