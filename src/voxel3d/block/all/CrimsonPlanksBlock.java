package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.SolidCubeBlock;
import voxel3d.block.utility.BlockBreakTimeUtility;
import voxel3d.item.Item;
import voxel3d.utility.Vector2f;

public class CrimsonPlanksBlock extends SolidCubeBlock {

	private static CrimsonPlanksBlock sharedInstance = new CrimsonPlanksBlock();
	private static Vector2f[] uvs = SolidCubeBlock.getUVFromName("Crimson planks");
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static CrimsonPlanksBlock getInstance()
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
		return 0x914c259f;
	}

	@Override
	public String getName() {
		return "Crimson planks";
	}
	
	@Override
	public double getBreakTime(Item tool)
	{
		return BlockBreakTimeUtility.getBreakTimeWood(tool);
	}

}
