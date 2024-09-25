package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.SolidCubeBlock;
import voxel3d.block.utility.BlockBreakTimeUtility;
import voxel3d.item.Item;
import voxel3d.utility.Vector2f;

public class StoneBricksSmallBlock extends SolidCubeBlock {

	private static StoneBricksSmallBlock sharedInstance = new StoneBricksSmallBlock();
	private static Vector2f[] uvs = SolidCubeBlock.getUVFromName("Stone bricks small");
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static StoneBricksSmallBlock getInstance()
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
		return 0xaab5b218;
	}

	@Override
	public String getName() {
		return "Stone bricks small";
	}
	
	@Override
	public double getBreakTime(Item tool)
	{
		return BlockBreakTimeUtility.getBreakTimeStone(tool);
	}

}
