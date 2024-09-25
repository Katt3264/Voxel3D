package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.SolidCubeBlock;
import voxel3d.block.utility.BlockBreakTimeUtility;
import voxel3d.item.Item;
import voxel3d.utility.Vector2f;

public class WhiteStoneBlock extends SolidCubeBlock {

	private static WhiteStoneBlock sharedInstance = new WhiteStoneBlock();
	private static Vector2f[] uvs = SolidCubeBlock.getUVFromName("White stone");
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static WhiteStoneBlock getInstance()
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
		return 0xd3ccfcf9;
	}

	@Override
	public String getName() {
		return "White stone";
	}
	
	@Override
	public float getRedLight()
	{
		return 1;
	}
	
	@Override
	public float getGreenLight()
	{
		return 1;
	}
	
	@Override
	public float getBlueLight()
	{
		return 1;
	}
	
	@Override
	public double getBreakTime(Item tool)
	{
		return BlockBreakTimeUtility.getBreakTimeStone(tool);
	}

}
