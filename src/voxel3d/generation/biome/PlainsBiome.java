package voxel3d.generation.biome;

import voxel3d.block.Block;
import voxel3d.block.all.*;
import voxel3d.generation.Fields;
import voxel3d.generation.OreGen;
import voxel3d.generation.SimplexNoise;

public class PlainsBiome extends Biome {
	
	
	@Override
	public Block getBlock(int x, int y, int z)
	{
		int l = y - getHeight(x, z);
		
		if(l > 1)
			return AirBlock.getInstance();
		else if(l == 1)
		{
			if(getRandom(x,y,z,10) == 0)
				return Grass.getInstance();
			else
				return AirBlock.getInstance();
		}
		else if(l == 0)
		{
			if(SimplexNoise.noise(x / 32d, z / 32d) < -0.8)
				return GravelBlock.getInstance();
			else if(SimplexNoise.noise(x / 32d, z / 32d) > 0.8)
				return SandBlock.getInstance();
			else
				return GrassBlock.getInstance();
		}
		else if(l >= -4)
			return DirtBlock.getInstance();
		else
			return OreGen.getBlock(x, y, z);
	}
	
	private static int getHeight(int x, int z)
	{
		return (int) Fields.OctaveMap2D(x, z, 128);
	}

}
