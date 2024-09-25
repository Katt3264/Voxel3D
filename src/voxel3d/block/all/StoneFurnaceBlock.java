package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.SolidCubeBlock;
import voxel3d.block.utility.BlockBreakTimeUtility;
import voxel3d.global.Objects;
import voxel3d.gui.HUDInteractable;
import voxel3d.item.Item;
import voxel3d.utility.Vector2f;

public class StoneFurnaceBlock extends SolidCubeBlock {

	private static StoneFurnaceBlock sharedInstance = new StoneFurnaceBlock();
	private static Vector2f[][] uvs = SolidCubeBlock.getUVsFromTopSideBottom("Smooth stone", "Stone furnace", "Smooth stone");
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static StoneFurnaceBlock getInstance()
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
		return 0xe5c0b5a7;
	}

	@Override
	public String getName() 
	{
		return "Stone furnace";
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
