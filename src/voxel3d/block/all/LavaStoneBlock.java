package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.SolidCubeBlock;
import voxel3d.block.utility.BlockBreakTimeUtility;
import voxel3d.item.Item;
import voxel3d.utility.Vector2f;

public class LavaStoneBlock extends SolidCubeBlock {

	private static LavaStoneBlock sharedInstance = new LavaStoneBlock();
	private static Vector2f[] uvs = SolidCubeBlock.getUVFromName("Lava stone");
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static LavaStoneBlock getInstance()
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
		return 0x3690c3bd;
	}

	@Override
	public String getName() {
		return "Lava stone";
	}
	
	@Override
	public float getRedLight()
	{
		return 1;
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
