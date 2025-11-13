package voxel3d.generation.structures;

import java.util.Random;

import voxel3d.block.Block;
import voxel3d.block.all.*;
import voxel3d.level.ChunkPopulator;
import voxel3d.global.Settings;

public class HouseSmal extends Structure {

	@Override
	public void placeBlocks(int x, int y, int z, Block[] blocks)
	{
		Block top = OakLogBlock.getInstance();
		Block side = OakPlanksBlock.getInstance();
		
		int size = 3;
		
		for(int xx = -size; xx <= size; xx++)
		{
			for(int zz = -size; zz <= size; zz++)
			{
				placeBlock(x + xx, y, z + zz, StoneBricksSmallBlock.getInstance(), blocks);
				placeBlock(x + xx, y + 4, z + zz, top.getBlockInstance(), blocks);
				
				if((xx == size || xx == -size) || (zz == size || zz == -size))
				{
					for(int yy = 1; yy <= 3; yy++)
					{
						if((xx == size || xx == -size) && (zz == size || zz == -size))
						{
							placeBlock(x + xx, y + yy, z + zz, StoneBricksSmallBlock.getInstance(), blocks);
						}
						else
						{
							placeBlock(x + xx, y + yy, z + zz, side.getBlockInstance(), blocks);
						}
					}
				}
			}
		}
	}
	
	@Override
	public void placeExistingOffset(int cx, int cy, int cz, int ox, int oy, int oz, Block[] blocks)
	{
		Random random = new Random();
		random.setSeed(cx * 9751 + cy * 2342 + cz * 8732465);
		
		int tests = (Settings.CHUNK_SIZE3) / 2000;
		for(int i = 0; i < tests; i++)
		{
			int xx = random.nextInt(Settings.CHUNK_SIZE);
			int yy = random.nextInt(Settings.CHUNK_SIZE);
			int zz = random.nextInt(Settings.CHUNK_SIZE);
			
			int x = xx + cx * Settings.CHUNK_SIZE;
			int y = yy + cy * Settings.CHUNK_SIZE;
			int z = zz + cz * Settings.CHUNK_SIZE;
			
			if(ChunkPopulator.getBlock(x, y - 1, z).isSolidBlock() && !ChunkPopulator.getBlock(x, y, z).isSolidBlock())
			{
				int px = xx + ox * Settings.CHUNK_SIZE;
				int py = yy + oy * Settings.CHUNK_SIZE;
				int pz = zz + oz * Settings.CHUNK_SIZE;
				placeBlocks(px, py, pz, blocks);
			}
		}
	}

}
