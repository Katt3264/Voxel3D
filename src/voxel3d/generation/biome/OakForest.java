package voxel3d.generation.biome;

import voxel3d.block.Block;
import voxel3d.block.all.*;
import voxel3d.generation.Fields;
import voxel3d.generation.OreGen;

public class OakForest extends Biome{

	
	@Override
	public Block getBlock(int x, int y, int z)
	{
		int l = y - getHeight(x, z);
		
		if(l > 1)
			return AirBlock.getInstance();
		else if(l == 1)
		{
			if(getRandom(x,y,z,2) == 0)
				return Grass.getInstance();
			else
				return AirBlock.getInstance();
		}
		else if(l == 0)
			return GrassBlock.getInstance();
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
