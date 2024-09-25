package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.SolidCubeBlock;
import voxel3d.block.utility.BlockBreakTimeUtility;
import voxel3d.item.Item;
import voxel3d.utility.Vector2f;

public class EmeraldOreBlock extends SolidCubeBlock {

	private static EmeraldOreBlock sharedInstance = new EmeraldOreBlock();
	private static Vector2f[] uvs = SolidCubeBlock.getUVFromName("Emerald ore");
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static EmeraldOreBlock getInstance()
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
		return 0xdfabdc52;
	}

	@Override
	public String getName() {
		return "Emerald ore";
	}
	
	@Override
	public float getGreenLight()
	{
		return 0.5f;
	}
	
	@Override
	public double getBreakTime(Item tool)
	{
		return BlockBreakTimeUtility.getBreakTimeStone(tool);
	}

}
