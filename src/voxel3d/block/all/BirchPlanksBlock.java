package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.SolidCubeBlock;
import voxel3d.block.utility.BlockBreakTimeUtility;
import voxel3d.item.Item;
import voxel3d.utility.Vector2f;

public class BirchPlanksBlock extends SolidCubeBlock {

	private static BirchPlanksBlock sharedInstance = new BirchPlanksBlock();
	private static Vector2f[] uvs = SolidCubeBlock.getUVFromName("Birch planks");
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static BirchPlanksBlock getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public Vector2f[] getUV(int index) 
	{
		return uvs;
	}

	@Override
	public int getLegacyID() {
		return 0xb10dd06a;
	}

	@Override
	public String getName() {
		return "Birch planks";
	}
	
	@Override
	public double getBreakTime(Item tool)
	{
		return BlockBreakTimeUtility.getBreakTimeWood(tool);
	}

}
