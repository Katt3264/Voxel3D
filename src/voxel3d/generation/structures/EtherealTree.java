package voxel3d.generation.structures;

import java.util.Random;

import voxel3d.block.Block;
import voxel3d.block.all.*;
import voxel3d.level.ChunkPopulator;
import voxel3d.global.Settings;

public class EtherealTree extends Structure {
	
	@Override
	public void placeBlocks(int x, int y, int z, Block[] blocks)
	{	
		for(int xx = -9; xx <= 9; xx++)
		{
			for(int yy = -3; yy <= 3; yy++)
			{
				for(int zz = -9; zz <= 9; zz++)
				{
					if(xx*xx + yy*yy*8 + zz*zz < 9*9)
						placeBlock(x + xx, y + 10 + yy, z + zz, EtherealLeavesBlock.getInstance(), blocks);
				}
			}
		}
		
		for(int h = 0; h < 12; h++)
		{
			placeBlock(x, y + h, z, EtherealLogBlock.getInstance(), blocks);
		}
		placeBlock(x, y - 1, z, DirtBlock.getInstance(), blocks);
	}
	
	@Override
	public void placeExistingOffset(int cx, int cy, int cz, int ox, int oy, int oz, Block[] blocks)
	{
		Random random = new Random();
		random.setSeed(cx * 2133 + cy * 2142767 + cz * 736329);
		
		int tests = (Settings.CHUNK_SIZE * Settings.CHUNK_SIZE * Settings.CHUNK_SIZE) / 150;
		for(int i = 0; i < tests; i++)
		{
			int xx = random.nextInt(Settings.CHUNK_SIZE);
			int yy = random.nextInt(Settings.CHUNK_SIZE);
			int zz = random.nextInt(Settings.CHUNK_SIZE);
			
			int x = xx + cx * Settings.CHUNK_SIZE;
			int y = yy + cy * Settings.CHUNK_SIZE;
			int z = zz + cz * Settings.CHUNK_SIZE;
			
			if(ChunkPopulator.getBlock(x, y - 1, z) instanceof EtherealGrassBlock)
			{
				int px = xx + ox * Settings.CHUNK_SIZE;
				int py = yy + oy * Settings.CHUNK_SIZE;
				int pz = zz + oz * Settings.CHUNK_SIZE;
				placeBlocks(px, py, pz, blocks);
			}
		}
	}
	
}
