package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.SolidCubeBlock;
import voxel3d.global.Objects;
import voxel3d.gui.HUDInteractable;
import voxel3d.utility.Vector2f;

public class IronCraftBenchBlock extends SolidCubeBlock {

	private static IronCraftBenchBlock sharedInstance = new IronCraftBenchBlock();
	private static Vector2f[] uvs = SolidCubeBlock.getUVFromName("Iron craft bench");
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static IronCraftBenchBlock getInstance()
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
		return 0x50a7a34b;
	}

	@Override
	public String getName() 
	{
		return "Iron craft bench";
	}
	
	@Override
	public HUDInteractable getHUD()
	{
		return Objects.craftingHUD;
	}

}
