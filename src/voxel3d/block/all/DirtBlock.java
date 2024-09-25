package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.SolidCubeBlock;
import voxel3d.block.utility.BlockBreakTimeUtility;
import voxel3d.item.Item;
import voxel3d.utility.Vector2f;

public class DirtBlock extends SolidCubeBlock {
	
	private static DirtBlock sharedInstance = new DirtBlock();
	private static Vector2f[] uvs = SolidCubeBlock.getUVFromName("Dirt");
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static DirtBlock getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public int getLegacyID()
	{
		return 0x3d9864c6;
	}
	
	@Override
	public String getName()
	{
		return "Dirt";
	}

	@Override
	public Vector2f[] getUV(int index)
	{
		return uvs;
	}
	
	@Override
	public double getBreakTime(Item tool)
	{
		return BlockBreakTimeUtility.getBreakTimeSoil(tool);
	}

}
