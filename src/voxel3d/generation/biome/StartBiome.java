package voxel3d.generation.biome;

import voxel3d.block.Block;
import voxel3d.block.all.AirBlock;
import voxel3d.block.all.BlackStoneBlock;
import voxel3d.block.all.DirtBlock;
import voxel3d.block.all.GrassBlock;
import voxel3d.generation.OreGen;

public class StartBiome extends Biome {
	
	
	@Override
	public Block getBlock(int x, int y, int z)
	{
		double distance = Biome.getDistanceToBiomeCenter(x, y, z);
		
		if(distance < 16 && y <= 1 && y >= -3)
		{
			return BlackStoneBlock.getInstance();
		}
		
		int l = y;
		
		if(l > 0)
			return AirBlock.getInstance();
		else if(l == 0)
			return GrassBlock.getInstance();
		else if(l >= -4)
			return DirtBlock.getInstance();
		else
			return OreGen.getBlock(x, y, z);
		
	}

}
