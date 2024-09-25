package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.SolidCubeBlock;
import voxel3d.block.utility.BlockBreakTimeUtility;
import voxel3d.item.Item;
import voxel3d.utility.Vector2f;

public class TinOreBlock extends SolidCubeBlock {

	private static TinOreBlock sharedInstance = new TinOreBlock();
	private static Vector2f[] uvs = SolidCubeBlock.getUVFromName("Tin ore");
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static TinOreBlock getInstance()
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
		return 0xaabf0a49;
	}

	@Override
	public String getName() {
		return "Tin ore";
	}
	
	@Override
	public double getBreakTime(Item tool)
	{
		return BlockBreakTimeUtility.getBreakTimeStone(tool);
	}
}
