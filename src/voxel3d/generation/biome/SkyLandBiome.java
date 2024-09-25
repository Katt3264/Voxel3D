package voxel3d.generation.biome;


import voxel3d.block.Block;
import voxel3d.block.all.*;
import voxel3d.generation.Fields;

public class SkyLandBiome extends Biome{
	
	@Override
	public Block getBlock(int x, int y, int z)
	{
		int l = y - getHeight(x, z);
		
		if(l > 6)
		{
			return AirBlock.getInstance();
		}
		else if(l > 0)
		{
			if(l == 1 && getRandom(z,x,z,25) == 0 && y-1 > getStoneHeight(x, z))
				return SkyFlower.getInstance();
			else if(l == 1 && getRandom(z,x,z,3) == 0 && y-1 > getStoneHeight(x, z))
				return SkyGrass.getInstance();
			else
				return AirBlock.getInstance();
		}
		else if(l == 0 && y > getStoneHeight(x, z))
			return SkyGrassBlock.getInstance();
		else if(l >= -2 && y > getStoneHeight(x, z))
			return DirtBlock.getInstance();
		else if(l > -2)
			return WhiteStoneBlock.getInstance();
		else
			return StoneBlock.getInstance();
	}
	
	private static int getHeight(int x, int z)
	{
		return (int) (Fields.OctaveMap2D(x, z, 128) * 8d);
	}
	
	private static int getStoneHeight(int x, int z)
	{
		return (int) (Fields.OctaveMap2D(x, z, 64) * 16d - 3d);
	}
}
