package voxel3d.generation.biome;

import voxel3d.block.Block;
import voxel3d.block.all.*;
import voxel3d.generation.Fields;
import voxel3d.utility.Color;

public class BlackDesertBiome extends Biome{
	
	@Override
	public Block getBlock(int x, int y, int z)
	{
		int l = y - getHeight(x, z);
		
		if(l > 1)
		{
			return AirBlock.getInstance();
		}
		else if(l == 1)
		{
			if(getRandom(z,x,z,50) == 0)
				return DryGrass.getInstance();
			else
				return AirBlock.getInstance();
		}
		else if(l == 0)
		{
			return BlackSandBlock.getInstance();
		}
		else if(l >= -8)
		{
			return BlackSandBlock.getInstance();
		}
		else
		{
			return BlackStoneBlock.getInstance();
		}
	}
	
	@Override
	public void getBiomeColorFactor(Color writeback)
	{
		writeback.set(0.2f, 0.2f, 0.2f);
	}
	
	private static int getHeight(int x, int z)
	{
		return (int) (Fields.OctaveMap2D(x, z, 64) * 5d);
	}

}
