package voxel3d.utility;

import voxel3d.global.Settings;

public class MathX {
	
	public static int chunkMod(int v)
	{
		return ((v % Settings.CHUNK_SIZE) + Settings.CHUNK_SIZE) % Settings.CHUNK_SIZE;
	}
	
	public static int chunkFloorDiv(int v)
	{
		return Math.floorDiv(v, Settings.CHUNK_SIZE);
	}
	
	public static int getXYZ(int x, int y, int z)
	{
		return x * Settings.CHUNK_SIZE * Settings.CHUNK_SIZE + y * Settings.CHUNK_SIZE + z;
	}
	
	public static double clamp(double val, double min, double max)
	{
		if(val > max) {return max;}
		if(val < min) {return min;}
		return val;
	}

}
