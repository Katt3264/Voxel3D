package voxel3d.level;

import java.util.ArrayList;
import java.util.List;

import voxel3d.block.Block;
import voxel3d.generation.*;
import voxel3d.generation.structures.*;
import voxel3d.global.Debug;
import voxel3d.global.Settings;
import voxel3d.level.containers.BlockContainer;
import voxel3d.level.containers.LightContainer;
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
	
	public static LightContainer getInitialLight(int chunkX, int chunkY, int chunkZ)
	{
		/*int x = chunkX * Settings.CHUNK_SIZE;
		int y = chunkY * Settings.CHUNK_SIZE;
		int z = chunkZ * Settings.CHUNK_SIZE;*/
		
		if(chunkY > -2)
			return new LightContainer(1f, 0, 0, 0);
		else
			return new LightContainer(0f, 0, 0, 0);		
	}
	
	private final int chunkX, chunkY, chunkZ;
	private final BlockContainer container;
	
	public ChunkPopulator(int chunkX, int chunkY, int chunkZ, BlockContainer container)
	{
		this.chunkX = chunkX;
		this.chunkY = chunkY;
		this.chunkZ = chunkZ;
		this.container = container;
		container.wip = true;
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
					
					blocks[index] = TerrainGen.getBlock(x, y, z);
				}
			}
		}
		
		for(Structure structure : structures)
			for(int xp = -1; xp <=1 ; xp++)
				for(int yp = -1; yp <= 1; yp++)
					for(int zp = -1; zp <= 1; zp++)
						structure.placeExistingOffset(chunkX + xp, chunkY + yp, chunkZ + zp, xp, yp, zp, blocks);
		
		container.set(blocks);
		
		Debug.chunkGens++;
		
		container.wip = false;
	}
	
}
