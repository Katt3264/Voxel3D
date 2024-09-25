package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.SolidCubeBlock;
import voxel3d.utility.Vector2f;

public class MachineBlock extends SolidCubeBlock {

	private static MachineBlock sharedInstance = new MachineBlock();
	private static Vector2f[] uvs = SolidCubeBlock.getUVFromName("Machine block");
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static MachineBlock getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public Vector2f[] getUV(int index) 
	{
		return uvs;
	}

	@Override
	public int getLegacyID() {
		return 0x75da8309;
	}

	@Override
	public String getName() {
		return "Machine block";
	}

}
