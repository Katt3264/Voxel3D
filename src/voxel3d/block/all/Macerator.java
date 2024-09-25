package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.SolidCubeBlock;
import voxel3d.global.Objects;
import voxel3d.gui.HUDInteractable;
import voxel3d.utility.Vector2f;

public class Macerator extends SolidCubeBlock {

	private static Macerator sharedInstance = new Macerator();
	private static Vector2f[][] uvs = SolidCubeBlock.getUVsFromTopSide("Machine block", "Macerator");
	
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static Macerator getInstance()
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
		return 0xf22da459;
	}

	@Override
	public String getName() 
	{
		return "Macerator";
	}
	
	@Override
	public HUDInteractable getHUD()
	{
		return Objects.craftingHUD;
	}

}
