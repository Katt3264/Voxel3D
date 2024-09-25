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

}
