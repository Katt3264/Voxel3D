package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.SolidCubeBlock;
import voxel3d.block.utility.BlockBreakTimeUtility;
import voxel3d.item.Item;
import voxel3d.utility.Vector2f;

public class StoneBricksBlock extends SolidCubeBlock {

	private static StoneBricksBlock sharedInstance = new StoneBricksBlock();
	private static Vector2f[] uvs = SolidCubeBlock.getUVFromName("Stone bricks");
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static StoneBricksBlock getInstance()
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
		return 0x6963b121;
	}

	@Override
	public String getName() {
		return "Stone bricks";
	}
	
	@Override
	public double getBreakTime(Item tool)
	{
		return BlockBreakTimeUtility.getBreakTimeStone(tool);
	}

}
