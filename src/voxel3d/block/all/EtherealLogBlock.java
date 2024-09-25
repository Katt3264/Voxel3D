package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.SolidCubeBlock;
import voxel3d.block.utility.BlockBreakTimeUtility;
import voxel3d.item.Item;
import voxel3d.utility.Vector2f;

public class EtherealLogBlock extends SolidCubeBlock {
	
	private static EtherealLogBlock sharedInstance = new EtherealLogBlock();
	private static Vector2f[][] uvs = SolidCubeBlock.getUVsFromTopSide("Ethereal log top", "Ethereal log");
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static EtherealLogBlock getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public int getLegacyID()
	{
		return 0x73675dbc;
	}
	
	@Override
	public String getName()
	{
		return "Ethereal log";
	}

	@Override
	public Vector2f[] getUV(int index)
	{
		return uvs[index];
	}
	
	@Override
	public double getBreakTime(Item tool)
	{
		return BlockBreakTimeUtility.getBreakTimeWood(tool);
	}

}
