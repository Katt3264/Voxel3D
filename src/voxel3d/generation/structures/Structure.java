package voxel3d.generation.structures;


import voxel3d.block.Block;
import voxel3d.global.Settings;
import voxel3d.utility.MathX;

public abstract class Structure {
	
	//public abstract void placeExistingOffset(int cx, int cy, int cz, int ox, int oy, int oz, Block[] blocks);
	public abstract void placeInChunk(int cx, int cy, int cz, Block[] blocks);
	public abstract void placeStructure(int x, int y, int z, Block[] blocks);
	
	protected void placeBlock(int x, int y, int z, Block block, Block[] blocks)
	{
    	if(x < 0 || x >= Settings.CHUNK_SIZE | y < 0 || y >= Settings.CHUNK_SIZE | z < 0 || z >= Settings.CHUNK_SIZE)
    		return;
    	
    	blocks[MathX.getXYZ(x, y, z)] = block;
	}
	
	

}
