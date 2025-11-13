package voxel3d.block.all;

import java.util.Random;

import voxel3d.block.Block;
import voxel3d.block.DirtSpreadable;
import voxel3d.block.SolidCubeBlock;
import voxel3d.block.context.BlockOnSimulateContext;
import voxel3d.block.utility.BlockBreakTimeUtility;
import voxel3d.item.Item;
import voxel3d.utility.Vector2f;

public class DirtBlock extends SolidCubeBlock {
	
	private static DirtBlock sharedInstance = new DirtBlock();
	private static Vector2f[] uvs = SolidCubeBlock.getUVFromName("Dirt");
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static DirtBlock getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public int getLegacyID()
	{
		return 0x3d9864c6;
	}
	
	@Override
	public String getName()
	{
		return "Dirt";
	}

	@Override
	public Vector2f[] getUV(int index)
	{
		return uvs;
	}
	
	@Override
	public double getBreakTime(Item tool)
	{
		return BlockBreakTimeUtility.getBreakTimeSoil(tool);
	}
	
	public void onRandomUpdate(BlockOnSimulateContext context)
	{
		if(context.getLocalBlock(0, 1, 0).isSolidBlock())
			return;
		
		Random random = new Random();
		int rx = random.nextInt(3) - 1;
		int ry = random.nextInt(3) - 1;
		int rz = random.nextInt(3) - 1;
		Block lBlock = context.getLocalBlock(rx, ry, rz);
		
		if(lBlock instanceof DirtSpreadable)
			context.replaceSelf(lBlock.getBlockInstance());
	}

}
