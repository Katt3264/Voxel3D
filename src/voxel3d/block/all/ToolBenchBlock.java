package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.SolidCubeBlock;
import voxel3d.global.Objects;
import voxel3d.gui.HUDInteractable;
import voxel3d.utility.Vector2f;

public class ToolBenchBlock extends SolidCubeBlock {

	private static ToolBenchBlock sharedInstance = new ToolBenchBlock();
	private static Vector2f[] uvs = SolidCubeBlock.getUVFromName("Tool bench");
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static ToolBenchBlock getInstance()
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
		return 0x81265c13;
	}

	@Override
	public String getName() 
	{
		return "Tool bench";
	}
	
	@Override
	public HUDInteractable getHUD()
	{
		return Objects.craftingHUD;
	}

}
