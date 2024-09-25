package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.SolidCubeBlock;
import voxel3d.block.utility.BlockBreakTimeUtility;
import voxel3d.item.Item;
import voxel3d.utility.Vector2f;

public class BlackStoneBlock extends SolidCubeBlock {

	private static BlackStoneBlock sharedInstance = new BlackStoneBlock();
	private static Vector2f[] uvs = SolidCubeBlock.getUVFromName("Black stone");
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static BlackStoneBlock getInstance()
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
		return 0x7e5f0028;
	}

	@Override
	public String getName() {
		return "Black stone";
	}
	
	@Override
	public double getBreakTime(Item tool)
	{
		return BlockBreakTimeUtility.getBreakTimeStone(tool);
	}

}
