package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.SolidCubeBlock;
import voxel3d.block.context.BlockOnBreakContext;
import voxel3d.block.utility.BlockBreakTimeUtility;
import voxel3d.item.Item;
import voxel3d.utility.Vector2f;

public class EtherealGrassBlock extends SolidCubeBlock {
	
	private static EtherealGrassBlock sharedInstance = new EtherealGrassBlock();
	private static Vector2f[][] uvs = SolidCubeBlock.getUVsFromTopSideBottom("Ethereal grass block top", "Ethereal grass block", "Dirt");
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static EtherealGrassBlock getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public int getLegacyID()
	{
		return 0x44c33527;
	}
	
	@Override
	public String getName()
	{
		return "Ethereal grass block";
	}

	@Override
	public Vector2f[] getUV(int index)
	{
		return uvs[index];
	}
	
	@Override
	public void onBreak(BlockOnBreakContext context)
	{
		context.dropItem(DirtBlock.getInstance().getItem());
	}
	
	@Override
	public double getBreakTime(Item tool)
	{
		return BlockBreakTimeUtility.getBreakTimeSoil(tool);
	}

}
