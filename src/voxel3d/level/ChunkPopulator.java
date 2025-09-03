package voxel3d.level;

import java.util.ArrayList;
import java.util.List;

import voxel3d.block.Block;
import voxel3d.generation.biome.Biome;
import voxel3d.generation.structures.*;
import voxel3d.global.Debug;
import voxel3d.global.Settings;
import voxel3d.level.world.Chunk;
import voxel3d.utility.Executable;
import voxel3d.utility.MathX;

public class ChunkPopulator implements Executable {
	
	private static final List<Structure> structures;
	
	static
	{
		structures = new ArrayList<Structure>();
		structures.add(new OakTree());
		structures.add(new BirchTree());
		structures.add(new EtherealTree());
		structures.add(new CrimsonTree());
		structures.add(new CherryTree());
		//structures.add(new HouseSmal());
	}
	
	private final int chunkX, chunkY, chunkZ;
	private final Chunk chunk;
	
	public ChunkPopulator(int chunkX, int chunkY, int chunkZ, Chunk chunk)
	{
		this.chunkX = chunkX;
		this.chunkY = chunkY;
		this.chunkZ = chunkZ;
		this.chunk = chunk;
		chunk.isBeingPopulated = true;
	}
	
	public void execute()
	{	
		Block[] blocks = new Block[Settings.CHUNK_SIZE3];
		
		for(int xp = 0; xp < Settings.CHUNK_SIZE; xp++)
		{
			for(int yp = 0; yp < Settings.CHUNK_SIZE; yp++)
			{
				for(int zp = 0; zp < Settings.CHUNK_SIZE; zp++)
				{
					int index = MathX.getXYZ(xp, yp, zp);
					
					int x = chunkX * Settings.CHUNK_SIZE + xp;
					int y = chunkY * Settings.CHUNK_SIZE + yp;
					int z = chunkZ * Settings.CHUNK_SIZE + zp;
					
					blocks[index] = getBlock(x, y, z);
				}
			}
		}
		
		for(Structure structure : structures)
			for(int xp = -1; xp <=1 ; xp++)
				for(int yp = -1; yp <= 1; yp++)
					for(int zp = -1; zp <= 1; zp++)
						structure.placeExistingOffset(chunkX + xp, chunkY + yp, chunkZ + zp, xp, yp, zp, blocks);
		
		chunk.setAllBlocks(blocks);
		chunk.isPopulated = true;
		chunk.isBeingPopulated = false;
		
		Debug.chunkGens++;
	}
	
	public static Block getBlock(int x, int y, int z)
	{
		return Biome.staticGetBlock(x, y, z);
	}
	
}
