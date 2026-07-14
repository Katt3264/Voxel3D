package voxel3d.generation.structures;

import java.util.Random;

import voxel3d.block.Block;
import voxel3d.block.all.*;
import voxel3d.generation.biome.OverworldTerrainGenerator;
import voxel3d.global.Settings;

public class LargeTower extends Structure {

	private static final int gridSize = 128;
	private static final int gridSizeHalf = gridSize / 2;
	private static final int spreadSize = 32;
	private static final int spreadSizeHalf = spreadSize / 2;
	
	private static final int height = 64;
	private static final int radius = 16;
	
	@Override
	public void placeStructure(int x, int y, int z, Block[] blocks)
	{
		Block block = StoneBricksBlock.getInstance();
		Block base = StoneBlock.getInstance();
		
		int size = radius;
		
		for(int yy = -5; yy < height; yy++)
		{
			int r = radius;
			
			for(int xx = -size; xx <= size; xx++)
			{
				for(int zz = -size; zz <= size; zz++)
				{
					if(xx*xx + zz*zz < r*r)
					{
						if(yy > 0)
							placeBlock(x + xx, y + yy, z + zz, block, blocks);
						else
							placeBlock(x + xx, y + yy, z + zz, base, blocks);
					}
				}
			}
		}
	}
	
	@Override
	public void placeInChunk(int cx, int cy, int cz, Block[] blocks)
	{
		int chunkX = cx * Settings.CHUNK_SIZE;
		int chunkY = cy * Settings.CHUNK_SIZE;
		int chunkZ = cz * Settings.CHUNK_SIZE;
		
		// position of closest structure
		int closestX = Math.floorDiv(chunkX + gridSizeHalf, gridSize) * gridSize;
		int closestY = 0;
		int closestZ = Math.floorDiv(chunkZ + gridSizeHalf, gridSize) * gridSize;
		
		// random offset from grid
		Random random = new Random();
		random.setSeed(closestX * 2137 + closestY * 212231 + closestZ * 736125);
		int structureX = closestX + random.nextInt(spreadSize) - spreadSizeHalf;
		int structureY = closestY;
		int structureZ = closestZ + random.nextInt(spreadSize) - spreadSizeHalf;
		
		// find y level of surface, do not place if no valid spot is found
		for(int yy = -10; yy < 64; yy++)
		{
			structureY = yy;
			if(OverworldTerrainGenerator.staticGetBlock(structureX, structureY, structureZ) instanceof GrassBlock)
			{
				placeStructure(structureX - chunkX, structureY - chunkY, structureZ - chunkZ, blocks);
				break;
			}
		}
	}
}
