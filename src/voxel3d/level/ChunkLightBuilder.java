package voxel3d.level;

import voxel3d.block.Block;
import voxel3d.global.Debug;
import voxel3d.global.Settings;
import voxel3d.global.Time;
import voxel3d.level.world.Chunk;
import voxel3d.utility.Executable;
import voxel3d.utility.MathX;

public class ChunkLightBuilder implements Executable {
	
	private static final int RED = 0;
	private static final int GREEN = 1;
	private static final int BLUE = 2;
	private static final int SKY = 3;
	
	private final Chunk[][][] chunks;
	private final Chunk chunkCenter;
	
	private final long newVer;
	
	public ChunkLightBuilder(Chunk[][][] chunks, long newVer)
	{
		this.chunks = chunks;
		this.chunkCenter = chunks[1][1][1];
		this.newVer = newVer;
		chunkCenter.buildingLight = true;
	}

	private boolean changed = false;
	public void execute()
	{
		
		
		byte[] red   = chunkCenter.getRed();
		byte[] green = chunkCenter.getGreen();
		byte[] blue  = chunkCenter.getBlue();
		byte[] sky   = chunkCenter.getSky();
		byte[][] tArr = new byte[][]{red, green, blue, sky};
		
		
		changed = false;
		for(int type = 0; type <= 3; type++)
			for(int x = 0; x < Settings.CHUNK_SIZE; x++)
				for(int y = 0; y < Settings.CHUNK_SIZE; y++)
					for(int z = 0; z < Settings.CHUNK_SIZE; z++)
						propagate(x, y, z, tArr[type], type);
		
		if(changed)
			for(int type = 0; type <= 3; type++)
				for(int x = Settings.CHUNK_SIZE - 1; x >= 0; x--)
					for(int y = Settings.CHUNK_SIZE - 1; y >= 0; y--)
						for(int z = Settings.CHUNK_SIZE - 1; z >= 0; z--)
							propagate(x, y, z, tArr[type], type);
		

		if(changed)
		{
			chunkCenter.lastModified = Time.getAtomTime();
		}
		else
		{
			chunkCenter.consistentLight = newVer;
		}
			
			
		chunkCenter.buildingLight = false;
		Debug.chunkLights++;
	}
	
	private void propagate(int x, int y, int z, byte[] arr, int type)
	{
		int oldValue = arr[MathX.getXYZ(x, y, z)];
		int newValue = getBlockLight(x, y, z, type);
		
		if(getBlock(x,y,z).isLightTransparent()) 
		{
			newValue = Math.max(newValue, get(x,    y,z, type, arr) - 1);
			newValue = Math.max(newValue, get(x + 1,y,z, type, arr) - 1);
			newValue = Math.max(newValue, get(x - 1,y,z, type, arr) - 1);
			if(get(x,y + 1,z, SKY, arr) == Settings.lightDistance && type == SKY) {newValue = Settings.lightDistance;}
			newValue = Math.max(newValue, get(x,y + 1,z, type, arr) - 1);
			newValue = Math.max(newValue, get(x,y - 1,z, type, arr) - 1);
			newValue = Math.max(newValue, get(x,y,z + 1, type, arr) - 1);
			newValue = Math.max(newValue, get(x,y,z - 1, type, arr) - 1);
		}
		
		if(newValue != oldValue) 
			changed = true;
		
		arr[MathX.getXYZ(x, y, z)] = (byte) newValue;
	}
	
	private byte get(int x, int y, int z, int type, byte[] arr)
	{
		int chunkX = MathX.chunkFloorDiv(x);
		int chunkY = MathX.chunkFloorDiv(y);
		int chunkZ = MathX.chunkFloorDiv(z);
		
		int xp = MathX.chunkMod(x);
		int yp = MathX.chunkMod(y);
		int zp = MathX.chunkMod(z);
		
		if(chunkX == 0 && chunkY == 0 && chunkZ == 0) 
			return arr[MathX.getXYZ(xp, yp, zp)];
		else
			return getLight(xp, yp, zp, type, chunks[chunkX + 1][chunkY + 1][chunkZ + 1]);
	}
	
	private byte getLight(int x, int y, int z, int type, Chunk chunk)
	{
		if(type == RED)
			return chunk.getRed()[MathX.getXYZ(x, y, z)];
		else if(type == GREEN)
			return chunk.getGreen()[MathX.getXYZ(x, y, z)];
		else if(type == BLUE)
			return chunk.getBlue()[MathX.getXYZ(x, y, z)];
		else //type == SKY
			return chunk.getSky()[MathX.getXYZ(x, y, z)];
	}
	
	private byte getBlockLight(int x, int y, int z, int type)
	{
		if(type == RED)
			return (byte) (getBlock(x, y, z).getRedLight() * Settings.lightDistance);
		else if(type == GREEN)
			return (byte) (getBlock(x, y, z).getGreenLight() * Settings.lightDistance);
		else if(type == BLUE)
			return (byte) (getBlock(x, y, z).getBlueLight() * Settings.lightDistance);
		else //type == SKY
			return (byte) (getBlock(x, y, z).getSkyLight() * Settings.lightDistance);
	}
	
	private Block getBlock(int x, int y, int z)
	{
		return chunkCenter.getBlock(x, y, z);
	}
}
