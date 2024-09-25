package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.SolidCubeBlock;
import voxel3d.block.utility.BlockBreakTimeUtility;
import voxel3d.item.Item;
import voxel3d.utility.Vector2f;

public class SandBlock extends SolidCubeBlock {
	
	private static SandBlock sharedInstance = new SandBlock();
	private static Vector2f[] uvs = SolidCubeBlock.getUVFromName("Sand");
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static SandBlock getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public int getLegacyID()
	{
		return 0x78053081;
	}
	
	@Override
	public String getName()
	{
		return "Sand";
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
