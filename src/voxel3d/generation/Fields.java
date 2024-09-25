package voxel3d.generation;

import java.util.Random;

import voxel3d.block.Block;
import voxel3d.block.all.*;

public class Fields {
	
	private static Random random = new Random();
	
	/*
	 * Terrain features
	 * 
	 * flat terrain
	 * hilly terrain
	 * mountains
	 * beaches
	 * 
	 * 
	 * 
	 */
	
	static double costContinent = 0.2;
	
	public static boolean isTerrain(int x, int y, int z)
	{
		/*if(Cave.isCave(x, y, z))
		{
			return false;
		}*/
		
		double c = continental(x, z);
		double h = 0;
		// h += continentHeight(x, z);
		h += c * 16;
		//h += continentalSinksHeight(x, z);
		

		int height = (int) Math.floor(h);
		
		if(y >= height + 1)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public static boolean waterEligable(int x, int y, int z)
	{
		return y <= 0;
	}
	
	
	//1 inland -1 ocean
	private static double continental(int x, int z)
	{
		double v = OctaveMap2D(x, z, 512);
		return v;
	}
	
	//1 flat -1 hills
	private static double errosion(int x, int z)
	{
		x += 9852367;
		z += 567382;
		double v = OctaveMap2D(x, z, 512);
		return v;
	}
	
	
	static double continentHeight = 10;
	static double oceanDepth = -5;
	
	private static double continentHeight(int x, int z)
	{
		double c = continental(x, z);
		
		double cost = 0;
		if(c < costContinent && c > -0.2)
			cost = map(c, -0.2, costContinent, -1, 1);
		else
			cost = c > 0 ? 1 : -1;
		
		
		double clifFactor = OctaveMap2D(x, z, 128);
		clifFactor *= 4;
		clifFactor = Math.max(-1, Math.min(1, clifFactor));
		clifFactor = map(clifFactor, -1, 1, 0, 1);
		
		if(cost > 0)
			return map(cost, 1, 0, continentHeight, continentHeight*clifFactor);
		else
			return map(cost, -1, 0, oceanDepth, oceanDepth*clifFactor);
		
	}
	
	
	
	public static double OctaveMap2D(int x, int z, double scale)
	{
		double val = 0;
		double amp = 0.5;
		
		for(int i = 0; i < 6; i++)
		{
			x += 734852;
			z += 326745;
			val += SimplexNoise.noise(x / scale, z / scale) * amp;
			scale *= 0.5;
			amp *= 0.5;
		}
		return val;
	}
	
	public static double OctaveMap3D(int x, int y, int z, double scale)
	{
		double val = 0;
		double amp = 0.5;
		
		for(int i = 0; i < 6; i++)
		{
			val += SimplexNoise.noise(x / scale, y / scale, z / scale) * amp;
			scale *= 0.5;
			amp *= 0.5;
		}
		return val;
	}
	
	private static double map(double val, double inMin, double inMax, double outMin, double outMax)
	{
		return (val - inMin) / (inMax - inMin) * (outMax - outMin) + outMin;
	}
	
}
