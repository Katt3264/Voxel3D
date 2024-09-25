package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.SolidCubeBlock;
import voxel3d.block.utility.BlockBreakTimeUtility;
import voxel3d.item.Item;
import voxel3d.utility.Vector2f;

public class EtherealPlanksBlock extends SolidCubeBlock {

	private static EtherealPlanksBlock sharedInstance = new EtherealPlanksBlock();
	private static Vector2f[] uvs = SolidCubeBlock.getUVFromName("Ethereal planks");
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static EtherealPlanksBlock getInstance()
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
		return 0xa5caeda9;
	}

	@Override
	public String getName() {
		return "Ethereal planks";
	}
	
	@Override
	public double getBreakTime(Item tool)
	{
		return BlockBreakTimeUtility.getBreakTimeWood(tool);
	}

}
