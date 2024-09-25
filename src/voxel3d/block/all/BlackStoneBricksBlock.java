package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.SolidCubeBlock;
import voxel3d.block.utility.BlockBreakTimeUtility;
import voxel3d.item.Item;
import voxel3d.utility.Vector2f;

public class BlackStoneBricksBlock extends SolidCubeBlock {

	private static BlackStoneBricksBlock sharedInstance = new BlackStoneBricksBlock();
	private static Vector2f[] uvs = SolidCubeBlock.getUVFromName("Black stone bricks");
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static BlackStoneBricksBlock getInstance()
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
		return 0x6391a35e;
	}

	@Override
	public String getName() {
		return "Black stone bricks";
	}
	
	@Override
	public double getBreakTime(Item tool)
	{
		return BlockBreakTimeUtility.getBreakTimeStone(tool);
	}

}
