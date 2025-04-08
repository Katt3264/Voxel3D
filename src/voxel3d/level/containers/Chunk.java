package voxel3d.level.containers;

import voxel3d.block.Block;

public class Chunk {
	
	private final int cx, cy, cz;
	
	public Chunk(int cx, int cy, int cz)
	{
		this.cx = cx;
		this.cy = cy;
		this.cz = cz;
	}
	
	public Block getBlock(int bx, int by, int bz)
	{
		return null;
	}
	
	public void setBlock(int bx, int by, int bz, Block block)
	{
		
	}
	
}
