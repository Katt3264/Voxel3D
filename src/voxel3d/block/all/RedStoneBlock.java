package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.SolidCubeBlock;
import voxel3d.block.utility.BlockBreakTimeUtility;
import voxel3d.item.Item;
import voxel3d.utility.Vector2f;

public class RedStoneBlock extends SolidCubeBlock {

	private static RedStoneBlock sharedInstance = new RedStoneBlock();
	private static Vector2f[] uvs = SolidCubeBlock.getUVFromName("Red stone");
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static RedStoneBlock getInstance()
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
		return 0xb51882e9;
	}

	@Override
	public String getName() {
		return "Red stone";
	}
	
	@Override
	public double getBreakTime(Item tool)
	{
		return BlockBreakTimeUtility.getBreakTimeStone(tool);
	}

}
