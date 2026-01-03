package voxel3d.generation.structures;

import java.util.Random;

import voxel3d.block.Block;
import voxel3d.block.all.*;
import voxel3d.level.ChunkPopulator;
import voxel3d.global.Settings;

public class CherryTree /*extends Structure*/ {
	/*
	@Override
	public void placeBlocks(int x, int y, int z, Block[] blocks)
	{
		for(int xx = -2; xx <= 2; xx++)
		{
			for(int yy = 3; yy <= 5; yy++)
			{
				for(int zz = -2; zz <= 2; zz++)
				{
					placeBlock(x + xx, y + yy, z + zz, CherryLeavesBlock.getInstance(), blocks);
				}
			}
		}
		
		for(int xx = -1; xx <= 1; xx++)
		{
			for(int yy = 5; yy <= 7; yy++)
			{
				for(int zz = -1; zz <= 1; zz++)
				{
					placeBlock(x + xx, y + yy, z + zz, CherryLeavesBlock.getInstance(), blocks);
				}
			}
		}
		
		for(int h = 0; h < 5; h++)
		{
			placeBlock(x, y + h, z, CherryLogBlock.getInstance(), blocks);
		}
		placeBlock(x, y - 1, z, DirtBlock.getInstance(), blocks);
	}
	
	@Override
	public void placeExistingOffset(int cx, int cy, int cz, int ox, int oy, int oz, Block[] blocks)
	{
		Random random = new Random();
		random.setSeed(cx * 2133 + cy * 2142767 + cz * 736329);
		
		int tests = (Settings.CHUNK_SIZE3) / 50;
		for(int i = 0; i < tests; i++)
		{
			int xx = random.nextInt(Settings.CHUNK_SIZE);
			int yy = random.nextInt(Settings.CHUNK_SIZE);
			int zz = random.nextInt(Settings.CHUNK_SIZE);
			
			int x = xx + cx * Settings.CHUNK_SIZE;
			int y = yy + cy * Settings.CHUNK_SIZE;
			int z = zz + cz * Settings.CHUNK_SIZE;
			
			if(ChunkPopulator.getBlock(x, y - 1, z) instanceof SkyGrassBlock)
			{
				int px = xx + ox * Settings.CHUNK_SIZE;
				int py = yy + oy * Settings.CHUNK_SIZE;
				int pz = zz + oz * Settings.CHUNK_SIZE;
				placeBlocks(px, py, pz, blocks);
			}
		}
	}
*/
}
