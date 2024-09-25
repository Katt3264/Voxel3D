package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.SolidCubeBlock;
import voxel3d.block.utility.BlockBreakTimeUtility;
import voxel3d.item.Item;
import voxel3d.utility.Vector2f;

public class CherryPlanksBlock extends SolidCubeBlock {

	private static CherryPlanksBlock sharedInstance = new CherryPlanksBlock();
	private static Vector2f[] uvs = SolidCubeBlock.getUVFromName("Cherry planks");
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static CherryPlanksBlock getInstance()
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
		return 0xa57fa258;
	}

	@Override
	public String getName() {
		return "Cherry planks";
	}
	
	@Override
	public double getBreakTime(Item tool)
	{
		return BlockBreakTimeUtility.getBreakTimeWood(tool);
	}

}
