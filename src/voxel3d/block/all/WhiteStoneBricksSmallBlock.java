package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.SolidCubeBlock;
import voxel3d.block.utility.BlockBreakTimeUtility;
import voxel3d.item.Item;
import voxel3d.utility.Vector2f;

public class WhiteStoneBricksSmallBlock extends SolidCubeBlock {

	private static WhiteStoneBricksSmallBlock sharedInstance = new WhiteStoneBricksSmallBlock();
	private static Vector2f[] uvs = SolidCubeBlock.getUVFromName("White stone bricks small");
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static WhiteStoneBricksSmallBlock getInstance()
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
		return 0x12b4ca34;
	}

	@Override
	public String getName() {
		return "White stone bricks small";
	}
	
	@Override
	public double getBreakTime(Item tool)
	{
		return BlockBreakTimeUtility.getBreakTimeStone(tool);
	}

}
