package voxel3d.generation;

import java.util.Random;

import voxel3d.block.Block;
import voxel3d.block.all.*;

public class TerrainGen {
	
	private static double size = 64;
	
	public static Block getBlock(int x, int y, int z)
	{
		int height = (int) Math.floor(getHeight(x, z));
		
		
		
		Block ret = AirBlock.getInstance();
		
		if(y > height + 1)
		{
			ret = AirBlock.getInstance();
		}
		else if(y == height + 1)
		{
			ret = getIfNull(ret, foliageBlock(x,y,z));
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
			//return StoneBlock.getInstance();
			ret = OreGen.getBlock(x, y, z);
		}
		
		
		return ret;
	}
	
	public static double getHeight(int x, int z)
	{
		return OctaveMap2D(x, z, 128) * 16;
	}
	
	public static double OctaveMap2D(int x, int z, double scale)
	{
		/*double val = 0;
		double amp = 0.5;
		
		for(int i = 0; i < 6; i++)
		{
			x += 734852;
			z += 326745;
			val += SimplexNoise.noise(x / scale, z / scale) * amp;
			scale *= 0.5;
			amp *= 0.5;
		}
		return val;*/
		return SimplexNoise.noise(x / scale, z / scale);
	}
	
	public static Block foliageBlock(int x, int y, int z)
	{
		Random random = new Random();
		random.setSeed(getPositionSeed(x, y, z));
		
		if(random.nextInt(4) == 0)
			return Grass.getInstance();
		else
			return null;
	}
	
	public static Block surfaceBlock(int x, int y, int z)
	{
		Random random = new Random();
		random.setSeed(getPositionSeed(x, y, z));
		
		if(random.nextInt(7) == 0)
			return GravelBlock.getInstance();
		else
			return GrassBlock.getInstance();
	}
	
	public static Block getIfNull(Block org, Block replace)
	{
		if(replace == null)
			return org;
		else
			return replace;
	}
	
	public static int getPositionSeed(int x, int y, int z)
	{
		return (x * 74317 + y * 2345897 + z * 4216754);
	}

}
