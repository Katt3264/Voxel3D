package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.SolidCubeBlock;
import voxel3d.block.utility.BlockBreakTimeUtility;
import voxel3d.item.Item;
import voxel3d.utility.Vector2f;

public class GoldOreBlock extends SolidCubeBlock {

	private static GoldOreBlock sharedInstance = new GoldOreBlock();
	private static Vector2f[] uvs = SolidCubeBlock.getUVFromName("Gold ore");
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static GoldOreBlock getInstance()
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
		return 0x5ae88c35;
	}

	@Override
	public String getName() {
		return "Gold ore";
	}
	
	@Override
	public double getBreakTime(Item tool)
	{
		return BlockBreakTimeUtility.getBreakTimeStone(tool);
	}

}
