package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.SolidCubeBlock;
import voxel3d.block.utility.BlockBreakTimeUtility;
import voxel3d.global.Objects;
import voxel3d.gui.HUDInteractable;
import voxel3d.item.Item;
import voxel3d.utility.Vector2f;

public class StoneCraftBenchBlock extends SolidCubeBlock {

	private static StoneCraftBenchBlock sharedInstance = new StoneCraftBenchBlock();
	private static Vector2f[] uvs = SolidCubeBlock.getUVFromName("Stone craft bench");
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static StoneCraftBenchBlock getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public Vector2f[] getUV(int index) 
	{
		return uvs;
	}

	@Override
	public int getLegacyID() 
	{
		return 0xaad3ce88;
	}

	@Override
	public String getName() 
	{
		return "Stone craft bench";
	}
	
	@Override
	public double getBreakTime(Item tool)
	{
		return BlockBreakTimeUtility.getBreakTimeWood(tool);
	}
	
	@Override
	public HUDInteractable getHUD()
	{
		return Objects.craftingHUD;
	}

}
