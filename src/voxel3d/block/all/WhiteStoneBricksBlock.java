package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.SolidCubeBlock;
import voxel3d.block.utility.BlockBreakTimeUtility;
import voxel3d.item.Item;
import voxel3d.utility.Vector2f;

public class WhiteStoneBricksBlock extends SolidCubeBlock {

	private static WhiteStoneBricksBlock sharedInstance = new WhiteStoneBricksBlock();
	private static Vector2f[] uvs = SolidCubeBlock.getUVFromName("White stone bricks");
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static WhiteStoneBricksBlock getInstance()
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
		return 0x14c13b7b;
	}

	@Override
	public String getName() {
		return "White stone bricks";
	}
	
	@Override
	public double getBreakTime(Item tool)
	{
		return BlockBreakTimeUtility.getBreakTimeStone(tool);
	}

}
