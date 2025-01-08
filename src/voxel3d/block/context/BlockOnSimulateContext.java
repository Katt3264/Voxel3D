package voxel3d.block.context;

import voxel3d.block.Block;
import voxel3d.level.containers.BlockContainer;
import voxel3d.utility.MathX;

public class BlockOnSimulateContext {
	
	//public boolean destroyed = false;
	public boolean updateMesh = false;
	//public boolean updateLight = false;
	private BlockContainer[][][] blocks;
	private double deltaTime;
	private int x, y, z;
	
	public BlockOnSimulateContext(double deltaTime, BlockContainer[][][] blocks)
	{
		this.blocks = blocks;
		this.deltaTime = deltaTime;
	}
	
	public void setLocalPos(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void replaceSelf(Block newBlock)
	{
		blocks[1][1][1].setBlock(x, y, z, newBlock);
	}
	
	public Block getLocalBlock(int lx, int ly, int lz)
	{
		int chunkX = MathX.chunkFloorDiv(x+lx) + 1;
		int chunkY = MathX.chunkFloorDiv(y+ly) + 1;
		int chunkZ = MathX.chunkFloorDiv(z+lz) + 1;
		
		int xp = MathX.chunkMod(x+lx);
		int yp = MathX.chunkMod(y+ly);
		int zp = MathX.chunkMod(z+lz);

		return blocks[chunkX][chunkY][chunkZ].getBlock(xp, yp, zp);
	}
	
	public void getBlocks(Block[] writeback)
	{
		writeback[0] = getLocalBlock(1, 0, 0);
		writeback[1] = getLocalBlock(-1, 0, 0);
		writeback[2] = getLocalBlock(0, 1, 0);
		writeback[3] = getLocalBlock(0, -1, 0);
		writeback[4] = getLocalBlock(0, 0, 1);
		writeback[5] = getLocalBlock(0, 0, -1);
	}
	

	public void internalStateChange()
	{
		updateMesh = true;
	}

	
	public double getDeltaTime()
	{
		return deltaTime;
	}
	
	public double getFixedTime()
	{
		return 0.01;
	}
}
