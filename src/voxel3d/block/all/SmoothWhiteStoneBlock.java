package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.SolidCubeBlock;
import voxel3d.block.utility.BlockBreakTimeUtility;
import voxel3d.item.Item;
import voxel3d.utility.Vector2f;

public class SmoothWhiteStoneBlock extends SolidCubeBlock {

	private static SmoothWhiteStoneBlock sharedInstance = new SmoothWhiteStoneBlock();
	private static Vector2f[] uvs = SolidCubeBlock.getUVFromName("Smooth white stone");
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static SmoothWhiteStoneBlock getInstance()
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
		return 0xbec08a74;
	}

	@Override
	public String getName() {
		return "Smooth white stone";
	}
	
	@Override
	public double getBreakTime(Item tool)
	{
		return BlockBreakTimeUtility.getBreakTimeStone(tool);
	}

}
