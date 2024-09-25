package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.SolidCubeBlock;
import voxel3d.block.utility.BlockBreakTimeUtility;
import voxel3d.item.Item;
import voxel3d.utility.Vector2f;

public class CopperOreBlock extends SolidCubeBlock {

	private static CopperOreBlock sharedInstance = new CopperOreBlock();
	private static Vector2f[] uvs = SolidCubeBlock.getUVFromName("Copper ore");
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static CopperOreBlock getInstance()
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
		return 0xccac02d9;
	}

	@Override
	public String getName() {
		return "Copper ore";
	}
	
	@Override
	public double getBreakTime(Item tool)
	{
		return BlockBreakTimeUtility.getBreakTimeStone(tool);
	}

}
