package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.SolidCubeBlock;
import voxel3d.block.utility.BlockBreakTimeUtility;
import voxel3d.item.Item;
import voxel3d.utility.Vector2f;

public class SapphireOreBlock extends SolidCubeBlock {

	private static SapphireOreBlock sharedInstance = new SapphireOreBlock();
	private static Vector2f[] uvs = SolidCubeBlock.getUVFromName("Sapphire ore");
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static SapphireOreBlock getInstance()
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
		return 0xf5642541;
	}

	@Override
	public String getName() {
		return "Sapphire ore";
	}
	
	@Override
	public float getBlueLight()
	{
		return 0.5f;
	}
	
	@Override
	public double getBreakTime(Item tool)
	{
		return BlockBreakTimeUtility.getBreakTimeStone(tool);
	}

}
