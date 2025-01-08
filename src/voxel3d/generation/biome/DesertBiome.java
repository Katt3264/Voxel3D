package voxel3d.generation.biome;

import java.util.Random;

import voxel3d.block.Block;
import voxel3d.block.all.*;
import voxel3d.generation.Fields;

public class DesertBiome extends Biome{
	
	private double size;
	public DesertBiome(double size)
	{
		this.size = size;
	}
	
	@Override
	public Block getBlock(int x, int y, int z)
	{
		int height = (int) Math.floor(getHeight(x, z));
		
		Block ret = AirBlock.getInstance();
		
		if(y > height + 1)
		{
			ret = AirBlock.getInstance();
		}
		else if(y == height + 1)
		{
			ret = foliageBlock(x,y,z);
		}
		else if(y <= height)
		{
			ret = SandBlock.getInstance();
		}
		
		return ret;
	}
	
	private static Block foliageBlock(int x, int y, int z)
	{
		Random random = new Random();
		random.setSeed(getPositionSeed(x, y, z));
		
		if(random.nextInt(16) == 0)
			return DryGrass.getInstance();
		else
			return AirBlock.getInstance();
	}
	
	private static int getPositionSeed(int x, int y, int z)
	{
		return (x * 74317 + y * 2345897 + z * 4216754);
	}
	
	private static double getHeight(int x, int z)
	{
		return Fields.OctaveMap2D(x, z, 64) * 5d;
	}

	@Override
	public double getSize() 
	{
		return size;
	}
}
