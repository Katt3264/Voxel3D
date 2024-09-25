package voxel3d.generation.biome;

import voxel3d.block.Block;
import voxel3d.block.all.*;
import voxel3d.generation.Fields;
import voxel3d.generation.OreGen;
import voxel3d.utility.Color;

public class DesertBiome extends Biome{
	
	@Override
	public Block getBlock(int x, int y, int z)
	{
		int l = y - getHeight(x, z);
		
		if(l > 1)
			return AirBlock.getInstance();
		else if(l == 1)
		{
			if(getRandom(z,x,z,50) == 0)
				return DryGrass.getInstance();
			else
				return AirBlock.getInstance();
		}
		else if(l == 0)
			return SandBlock.getInstance();
		else if(l >= -8)
			return SandBlock.getInstance();
		else
			return OreGen.getBlock(x, y, z);
	}
	
	@Override
	public void getBiomeColorFactor(Color writeback)
	{
		writeback.set(1.0f, 1.0f, 0.5f);
	}
	
	private static int getHeight(int x, int z)
	{
		return (int) (Fields.OctaveMap2D(x, z, 64) * 5d);
	}
}
