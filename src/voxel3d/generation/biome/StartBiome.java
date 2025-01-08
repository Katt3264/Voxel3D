package voxel3d.generation.biome;

import java.util.Random;

import voxel3d.block.Block;
import voxel3d.block.all.*;
import voxel3d.generation.Fields;
import voxel3d.generation.OreGen;

public class StartBiome extends Biome {
	
	private double size;
	public StartBiome(double size)
	{
		this.size = size;
	}
	
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
	
	private static double getHeight(int x, int z)
	{
		return Fields.OctaveMap2D(x, z, 128) * 16;
	}
	
	private static double getSandGravel_(int x, int z)
	{
		return Fields.OctaveMap2D(x + 2368915, z + 2368915, 32);
	}
	
	private static boolean isGravel(int x, int z)
	{
		return getSandGravel_(x, z) > 0.5;
	}
	private static boolean isSand(int x, int z)
	{
		return getSandGravel_(x, z) < -0.5;
	}
	
	private static Block foliageBlock(int x, int y, int z)
	{
		Random random = new Random();
		random.setSeed(getPositionSeed(x, y, z));
		
		if(isSand(x, z) || isGravel(x, z))
			return AirBlock.getInstance();
		
		if(random.nextInt(4) == 0)
			return Grass.getInstance();
		
		return AirBlock.getInstance();
	}
	
	private static Block surfaceBlock(int x, int y, int z)
	{
		if(isGravel(x, z))
			return GravelBlock.getInstance();
		else if(isSand(x, z))
			return SandBlock.getInstance();
		else
			return GrassBlock.getInstance();
	}
	
	private static int getPositionSeed(int x, int y, int z)
	{
		return (x * 74317 + y * 2345897 + z * 4216754);
	}

	@Override
	public double getSize() 
	{
		return size;
	}

}
