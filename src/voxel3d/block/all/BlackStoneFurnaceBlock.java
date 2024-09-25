package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.SolidCubeBlock;
import voxel3d.block.utility.BlockBreakTimeUtility;
import voxel3d.global.Objects;
import voxel3d.gui.HUDInteractable;
import voxel3d.item.Item;
import voxel3d.utility.Vector2f;

public class BlackStoneFurnaceBlock extends SolidCubeBlock {

	private static BlackStoneFurnaceBlock sharedInstance = new BlackStoneFurnaceBlock();
	private static Vector2f[][] uvs = SolidCubeBlock.getUVsFromTopSideBottom("Smooth black stone", "Black stone furnace", "Smooth black stone");
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static BlackStoneFurnaceBlock getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public Vector2f[] getUV(int index) 
	{
		return uvs[index];
	}

	@Override
	public int getLegacyID() 
	{
		return 0x6764c8a0;
	}

	@Override
	public String getName() 
	{
		return "Black stone furnace";
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
	
	@Override
	public HUDInteractable getHUD()
	{
		return Objects.craftingHUD;
	}

}
