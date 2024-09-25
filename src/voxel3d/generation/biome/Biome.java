package voxel3d.generation.biome;

import voxel3d.block.Block;
import voxel3d.level.containers.LightContainer;
import voxel3d.utility.Color;

public abstract class Biome {
	
	private static int BIOME_SIZE = 65;
	
	private static Biome[] biomes = new Biome[] {
			new OakForest(),
			new DesertBiome(),
			new EtherealBiome(),
			new CrimsonBiome(),
			new BlackDesertBiome(),
			//new LavaBiome(),
			new SkyLandBiome(),
			//new RainbowBiome(),
			new BirchForest(),
			new PlainsBiome()
	};
	
	private static Biome startBiome = new StartBiome();
	
	public void getBiomeColorFactor(Color writeback)
	{
		writeback.set(1.0f, 1.0f, 1.0f);
	}
	
	public void getBiomeColorAcc(Color writeback)
	{
		writeback.set(0.0f, 0.0f, 0.0f);
	}
	
	public LightContainer getInitialLight()
	{
		return new LightContainer(1f, 0, 0, 0);
	}
	
	public abstract Block getBlock(int x, int y, int z);
	
	public static Biome getBiome(int x, int y, int z)
	{
		int bx = Math.floorDiv(x + (BIOME_SIZE / 2), BIOME_SIZE);
		int bz = Math.floorDiv(z + (BIOME_SIZE / 2), BIOME_SIZE);
		
		if(bx == 0 && bz == 0)
			return startBiome;
		
		//return biomes[getRandom(bx,0,bz, biomes.length)];
		
		return startBiome;
	}
	
	public static double getDistanceToBiomeCenter(int x, int y, int z)
	{
		int bx = Math.floorDiv(x + (BIOME_SIZE / 2), BIOME_SIZE) * BIOME_SIZE;
		int bz = Math.floorDiv(z + (BIOME_SIZE / 2), BIOME_SIZE) * BIOME_SIZE;
		
		return Math.sqrt((bx - x) * (bx - x) + (bz - z) * (bz - z));
	}
	
	protected static int getRandom(int x, int y, int z, int l)
	{
		int hash = 0x12345678;
		hash ^= x;
		hash ^= 0x12345678;
		hash ^= hash * y;
		hash ^= 0x12345678;
		hash ^= hash * z;
		hash ^= 0x12345678;
		
		hash ^= hash * x >>> 16;
		hash ^= hash * y >>> 16;
		hash ^= hash * z >>> 16;
		
		hash ^= hash * x + 37;
		hash ^= hash * y + 37;
		hash ^= hash * z + 37;
		
		hash ^= hash * x * 37;
		hash ^= hash * y * 37;
		hash ^= hash * z * 37;
		
		hash ^= hash * x >>> 16;
		hash ^= hash * y >>> 16;
		hash ^= hash * z >>> 16;
		
		return (hash & 0x7fffffff) % l;
	}

}
