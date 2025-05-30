package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.SolidCubeBlock;
import voxel3d.block.utility.BlockBreakTimeUtility;
import voxel3d.item.Item;
import voxel3d.utility.Vector2f;

public class RubyOreBlock extends SolidCubeBlock {

	private static RubyOreBlock sharedInstance = new RubyOreBlock();
	private static Vector2f[] uvs = SolidCubeBlock.getUVFromName("Ruby ore");
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static RubyOreBlock getInstance()
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
		return 0x856ced5b;
	}

	@Override
	public String getName() {
		return "Ruby ore";
	}
	
	@Override
	public float getRedLight()
	{
		return 0.5f;
	}
	
	@Override
	public double getBreakTime(Item tool)
	{
		return BlockBreakTimeUtility.getBreakTimeStone(tool);
	}

}
