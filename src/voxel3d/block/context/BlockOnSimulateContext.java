package voxel3d.block.context;

import java.util.Random;

import voxel3d.block.Block;
import voxel3d.level.world.Chunk;
import voxel3d.utility.MathX;

public class BlockOnSimulateContext {
	
	public boolean updateMesh = false;
	private Chunk[][][] chunks;
	private int x, y, z;
	private Random random = new Random();
	
	public BlockOnSimulateContext(Chunk[][][] chunks)
	{
		this.chunks = chunks;
	}
	
	public void setLocalPos(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Random getRandom()
	{
		return random;
	}
	
	public void replaceSelf(Block newBlock)
	{
		chunks[1][1][1].setBlock(x, y, z, newBlock);
	}
	
	public Block getLocalBlock(int lx, int ly, int lz)
	{
		int chunkX = MathX.chunkFloorDiv(x+lx) + 1;
		int chunkY = MathX.chunkFloorDiv(y+ly) + 1;
		int chunkZ = MathX.chunkFloorDiv(z+lz) + 1;
		
		int xp = MathX.chunkMod(x+lx);
		int yp = MathX.chunkMod(y+ly);
		int zp = MathX.chunkMod(z+lz);

		return chunks[chunkX][chunkY][chunkZ].getBlock(xp, yp, zp);
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
}
