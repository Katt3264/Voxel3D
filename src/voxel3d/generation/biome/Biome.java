package voxel3d.generation.biome;

import voxel3d.block.Block;

public abstract class Biome {
	
	
	private static final Biome[] biomes = new Biome[] {
			
			new StartBiome(32),
			new DesertBiome(16),
			new SkyLandBiome(256),
			new MysticBiome(256),
			new RainbowBiome(256),
	};
	
	//private static final Biome biome = new OverworldBiome(0);
	
	private static final Biome endBiome = new DesertBiome(0);
	
	public abstract Block getBlock(int x, int y, int z);
	public abstract double getSize();
	
	/*public static Block staticGetBlock(int x, int y, int z)
	{
		//Biome biome = staticGetBiome(x, y, z);
		//return biome.getBlock(x, y, z);
		//return biome.getBlock(x, y, z);
	}*/
	
	/*public static Biome staticGetBiome(int x, int y, int z)
	{
		double distance = Math.sqrt(x*x + z*z);
		double accL = 0;
		for(Biome biome : biomes)
		{
			accL += biome.getSize();
			if(distance < accL)
			{
				return biome;
			}
			
		}
		return endBiome;
	}*/
	
	
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
