package voxel3d.generation.biome;

import voxel3d.block.Block;
import voxel3d.block.all.*;
import voxel3d.generation.Fields;
import voxel3d.generation.OreGen;
import voxel3d.utility.Color;

public class CrimsonBiome extends Biome{
	
	@Override
	public Block getBlock(int x, int y, int z)
	{
		int l = y - getHeight(x, z);
		
		if(l > 1)
			return AirBlock.getInstance();
		else if(l == 1)
		{
			if(getRandom(x,y,z,25) == 0)
				return CrimsonFlower.getInstance();
			else if(getRandom(x,y,z,2) == 0)
				return CrimsonGrass.getInstance();
			else
				return AirBlock.getInstance();
		}
		else if(l == 0)
			return CrimsonGrassBlock.getInstance();
		else if(l >= -4)
			return DirtBlock.getInstance();
		else
			return OreGen.getBlock(x, y, z);
	}
	
	@Override
	public void getBiomeColorFactor(Color writeback)
	{
		writeback.set(0.5f, 0.2f, 0.2f);
	}
	
	private static int getHeight(int x, int z)
	{
		return (int) Fields.OctaveMap2D(x, z, 128);
	}

}
