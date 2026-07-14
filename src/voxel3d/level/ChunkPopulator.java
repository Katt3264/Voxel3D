package voxel3d.level;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import voxel3d.block.Block;
import voxel3d.data.DataInputStream;
import voxel3d.generation.biome.OverworldTerrainGenerator;
import voxel3d.generation.structures.*;
import voxel3d.global.Debug;
import voxel3d.global.Settings;
import voxel3d.utility.Executable;
import voxel3d.utility.MathX;

public class ChunkPopulator implements Executable {
	
	private static final List<Structure> structures;
	
	static
	{
		structures = new ArrayList<Structure>();
		structures.add(new OakTree());
		//structures.add(new BirchTree());
		//structures.add(new EtherealTree());
		//structures.add(new CrimsonTree());
		//structures.add(new CherryTree());
		//structures.add(new HouseSmal());
		//structures.add(new LargeTower());
	}
	
	private final OverworldTerrainGenerator terrainGenerator;
	private final int chunkX, chunkY, chunkZ;
	private final String worldName;
	private final Chunk chunk;
	
	public ChunkPopulator(int chunkX, int chunkY, int chunkZ, String worldName, Chunk chunk)
	{
		this.chunkX = chunkX;
		this.chunkY = chunkY;
		this.chunkZ = chunkZ;
		this.worldName = worldName;
		this.chunk = chunk;
		chunk.isBeingPopulated = true;
		terrainGenerator = new OverworldTerrainGenerator();
	}
	
	public void execute()
	{
		if(Settings.loadEnable)
		{
			try {
				String chunkPath = ("store/worlds/" + worldName + "/chunks/chunk" + chunkX + "," + chunkY + "," + chunkZ);
				Debug.ioLog("loading: " + chunkPath);
				FileInputStream fis = new FileInputStream(chunkPath);
				byte[] data = fis.readAllBytes();
				fis.close();
				chunk.read(new DataInputStream(data));
			}
			catch (Exception e)
			{
				generateChunk();
			}
		}
		else
		{
			generateChunk();
		}
		
		chunk.isPopulated = true;
		chunk.isBeingPopulated = false;
		
		Debug.chunkGens++;
	}
	
	private void generateChunk()
	{
		Block[] blocks = new Block[Settings.CHUNK_SIZE3];
		
		for(int xp = 0; xp < Settings.CHUNK_SIZE; xp++){
			for(int yp = 0; yp < Settings.CHUNK_SIZE; yp++){
				for(int zp = 0; zp < Settings.CHUNK_SIZE; zp++){
					int index = MathX.getXYZ(xp, yp, zp);
					
					int x = chunkX * Settings.CHUNK_SIZE + xp;
					int y = chunkY * Settings.CHUNK_SIZE + yp;
					int z = chunkZ * Settings.CHUNK_SIZE + zp;
					
					blocks[index] = terrainGenerator.getBlock(x, y, z);
				}
			}
		}
		
		for(Structure structure : structures)
			structure.placeInChunk(chunkX, chunkY, chunkZ, blocks);
		
		chunk.setAllBlocks(blocks);
	}
	
}
