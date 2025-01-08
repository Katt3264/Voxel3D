package voxel3d.generation.biome;

import java.util.Random;

import voxel3d.block.Block;
import voxel3d.block.all.*;
import voxel3d.generation.Fields;
import voxel3d.generation.OreGen;

public class MysticBiome extends Biome{
	
	private double size;
	public MysticBiome(double size)
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
		else if(y == height)
		{
			ret = surfaceBlock(x,y,z);
		}
		else if(y >= height - 4)
		{
			ret = DirtBlock.getInstance();
		}
		else if(y < height - 4)
		{
			ret = OreGen.getBlock(x, y, z);
		}
		
		return ret;
	}
	
	private static Block foliageBlock(int x, int y, int z)
	{
		Random random = new Random();
		random.setSeed(getPositionSeed(x, y, z));
		
		if(getSand(x, z))
			return AirBlock.getInstance();
		
		if(random.nextInt(4) == 0)
			if(getBiomeType(x,z) > 0)
				return CrimsonGrass.getInstance();
			else
				return EtherealGrass.getInstance();
		
		if(random.nextInt(16) == 0)
			if(getBiomeType(x,z) > 0)
				return CrimsonFlower.getInstance();
			else
				return EtherealFlower.getInstance();
		
		return AirBlock.getInstance();
	}
	
	private static Block surfaceBlock(int x, int y, int z)
	{
		Random random = new Random();
		random.setSeed(getPositionSeed(x, y, z));
		
		if(getSand(x, z))
			return BlackSandBlock.getInstance();
		else
			if(getBiomeType(x,z) > 0)
				return CrimsonGrassBlock.getInstance();
			else
				return EtherealGrassBlock.getInstance();
	}
	
	private static boolean getSand(int x, int z)
	{
		return Fields.OctaveMap2D(x + 2368915, z + 2368915, 32) > 0.25;
	}
	
	private static int getPositionSeed(int x, int y, int z)
	{
		return (x * 74317 + y * 2345897 + z * 4216754);
	}
	
	private static double getHeight(int x, int z)
	{
		double val = Fields.OctaveMap2D(x, z, 128);
		/*double height = 8;
		double var = 8;
		double range = 0.1;
		
		if(val > range)
			return val * var + height;
		
		if(val < -range)
			return val * var - height;
		
		return val * ((height / range) + var);*/
		
		return val * 16;
	}
	
	private static double getBiomeType(int x, int z)
	{
		//return Fields.OctaveMap2D(x + 92365, z + 127771, 128);
		return -getHeight(x, z);
	}

	@Override
	public double getSize() 
	{
		return size;
	}

}
