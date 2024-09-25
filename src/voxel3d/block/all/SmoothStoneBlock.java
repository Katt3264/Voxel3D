package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.SolidCubeBlock;
import voxel3d.block.utility.BlockBreakTimeUtility;
import voxel3d.item.Item;
import voxel3d.utility.Vector2f;

public class SmoothStoneBlock extends SolidCubeBlock {

	private static SmoothStoneBlock sharedInstance = new SmoothStoneBlock();
	private static Vector2f[] uvs = SolidCubeBlock.getUVFromName("Smooth stone");
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static SmoothStoneBlock getInstance()
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
		return 0xf1b9b93c;
	}

	@Override
	public String getName() {
		return "Smooth stone";
	}
	
	@Override
	public double getBreakTime(Item tool)
	{
		return BlockBreakTimeUtility.getBreakTimeStone(tool);
	}

}
