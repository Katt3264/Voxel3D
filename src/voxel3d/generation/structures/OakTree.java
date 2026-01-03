package voxel3d.generation.structures;

import java.util.Random;

import voxel3d.block.Block;
import voxel3d.block.all.*;
import voxel3d.generation.Fields;
import voxel3d.generation.biome.OverworldTerrainGenerator;
import voxel3d.global.Settings;

public class OakTree extends Structure {
	
	@Override
	public void placeStructure(int x, int y, int z, Block[] blocks)
	{
		for(int xx = -2; xx <= 2; xx++)
		{
			for(int yy = 3; yy <= 5; yy++)
			{
				for(int zz = -2; zz <= 2; zz++)
				{
					placeBlock(x + xx, y + yy, z + zz, OakLeavesBlock.getInstance(), blocks);
				}
			}
		}
		
		for(int xx = -1; xx <= 1; xx++)
		{
			for(int yy = 5; yy <= 7; yy++)
			{
				for(int zz = -1; zz <= 1; zz++)
				{
					placeBlock(x + xx, y + yy, z + zz, OakLeavesBlock.getInstance(), blocks);
				}
			}
		}
		
		for(int h = 0; h < 5; h++)
		{
			placeBlock(x, y + h, z, OakLogBlock.getInstance(), blocks);
		}
		placeBlock(x, y - 1, z, DirtBlock.getInstance(), blocks);
	}
	
	@Override
	public void placeInChunk(int cx, int cy, int cz, Block[] blocks)
	{
		for(int ox = -1; ox <=1 ; ox++) {
			for(int oy = -1; oy <= 1; oy++) {
				for(int oz = -1; oz <= 1; oz++) {
					
					// chunk that is being checked
					int rx = cx + ox;
					int ry = cy + oy;
					int rz = cz + oz;
					
					Random random = new Random();
					random.setSeed(rx * 2137 + ry * 212231 + rz * 736125);
					
					int tests = (Settings.CHUNK_SIZE * Settings.CHUNK_SIZE * Settings.CHUNK_SIZE) / 64;
					for(int i = 0; i < tests; i++)
					{
						// chunk relative point
						int xx = random.nextInt(Settings.CHUNK_SIZE);
						int yy = random.nextInt(Settings.CHUNK_SIZE);
						int zz = random.nextInt(Settings.CHUNK_SIZE);
						
						// absolute position
						int x = xx + rx * Settings.CHUNK_SIZE;
						int y = yy + ry * Settings.CHUNK_SIZE;
						int z = zz + rz * Settings.CHUNK_SIZE;
						
						if (Fields.OctaveMap2D(x, z, 128) < 0.0d)
							continue;
						
						if(OverworldTerrainGenerator.staticGetBlock(x, y - 1, z) instanceof GrassBlock)
						{
							// position relative to chunk to place in
							int px = x - cx * Settings.CHUNK_SIZE;
							int py = y - cy * Settings.CHUNK_SIZE;
							int pz = z - cz * Settings.CHUNK_SIZE;
							
							placeStructure(px, py, pz, blocks);
						}
					}
					
					
				}
			}
		}
	}
	
	/*@Override
	public void placeExistingOffset(int cx, int cy, int cz, int ox, int oy, int oz, Block[] blocks)
	{
		Random random = new Random();
		random.setSeed(cx * 2137 + cy * 212231 + cz * 736125);
		
		int tests = (Settings.CHUNK_SIZE * Settings.CHUNK_SIZE * Settings.CHUNK_SIZE) / 64;
		for(int i = 0; i < tests; i++)
		{
			int xx = random.nextInt(Settings.CHUNK_SIZE);
			int yy = random.nextInt(Settings.CHUNK_SIZE);
			int zz = random.nextInt(Settings.CHUNK_SIZE);
			
			int x = xx + cx * Settings.CHUNK_SIZE;
			int y = yy + cy * Settings.CHUNK_SIZE;
			int z = zz + cz * Settings.CHUNK_SIZE;
			
			if (Fields.OctaveMap2D(x, z, 128) < 0.0d)
				continue;
			
			if(ChunkPopulator.getBlock(x, y - 1, z) instanceof GrassBlock)
			{
				int px = xx + ox * Settings.CHUNK_SIZE;
				int py = yy + oy * Settings.CHUNK_SIZE;
				int pz = zz + oz * Settings.CHUNK_SIZE;
				placeBlocks(px, py, pz, blocks);
			}
		}
	}*/
}
