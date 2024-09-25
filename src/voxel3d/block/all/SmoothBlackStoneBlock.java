package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.SolidCubeBlock;
import voxel3d.block.utility.BlockBreakTimeUtility;
import voxel3d.item.Item;
import voxel3d.utility.Vector2f;

public class SmoothBlackStoneBlock extends SolidCubeBlock {

	private static SmoothBlackStoneBlock sharedInstance = new SmoothBlackStoneBlock();
	private static Vector2f[] uvs = SolidCubeBlock.getUVFromName("Smooth black stone");
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static SmoothBlackStoneBlock getInstance()
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
		return 0x033c7ea6;
	}

	@Override
	public String getName() {
		return "Smooth black stone";
	}
	
	@Override
	public double getBreakTime(Item tool)
	{
		return BlockBreakTimeUtility.getBreakTimeStone(tool);
	}

}
