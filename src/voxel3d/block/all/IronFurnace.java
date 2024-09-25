package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.SolidCubeBlock;
import voxel3d.global.Objects;
import voxel3d.gui.HUDInteractable;
import voxel3d.utility.Vector2f;

public class IronFurnace extends SolidCubeBlock {

	private static IronFurnace sharedInstance = new IronFurnace();
	private static Vector2f[][] uvs = SolidCubeBlock.getUVsFromTopSide("Machine block", "Iron furnace");
	
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static IronFurnace getInstance()
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
		return 0x3f2d526f;
	}

	@Override
	public String getName() 
	{
		return "Iron furnace";
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
	public HUDInteractable getHUD()
	{
		return Objects.craftingHUD;
	}

}
