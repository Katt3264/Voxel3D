package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.TransparentCubeBlock;
import voxel3d.utility.Vector2f;

public class GlassBlock extends TransparentCubeBlock {
	
	private static GlassBlock sharedInstance = new GlassBlock();
	private static Vector2f[] uvs = TransparentCubeBlock.getUVFromName("Glass");
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static GlassBlock getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public int getLegacyID()
	{
		return 0x86b521ef;
	}
	
	@Override
	public String getName()
	{
		return "Glass";
	}

	@Override
	protected Vector2f[] getUV(int index)
	{
		return uvs;
	}

}
