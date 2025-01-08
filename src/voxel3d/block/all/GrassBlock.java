package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.DirtSpreadable;
import voxel3d.block.SolidCubeBlock;
import voxel3d.block.context.BlockOnBreakContext;
import voxel3d.block.context.BlockOnSimulateContext;
import voxel3d.block.utility.BlockBreakTimeUtility;
import voxel3d.item.Item;
import voxel3d.utility.Vector2f;

public class GrassBlock extends SolidCubeBlock implements DirtSpreadable {
	
	private static GrassBlock sharedInstance = new GrassBlock();
	private static Vector2f[][] uvs = SolidCubeBlock.getUVsFromTopSideBottom("Grass block top", "Grass block", "Dirt");
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static GrassBlock getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public int getLegacyID()
	{
		return 0x99150126;
	}
	
	@Override
	public String getName()
	{
		return "Grass block";
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
	
	@Override
	public void onRandomUpdate(BlockOnSimulateContext context)
	{
		if(context.getLocalBlock(0, 1, 0).isSolidBlock())
			context.replaceSelf(DirtBlock.getInstance());
	}

}
