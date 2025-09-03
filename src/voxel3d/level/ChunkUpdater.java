package voxel3d.level;

import java.util.Map.Entry;
import java.util.Random;

import voxel3d.block.BlockSimulable;
import voxel3d.block.context.BlockOnSimulateContext;
import voxel3d.global.Settings;
import voxel3d.level.world.Chunk;
import voxel3d.utility.Executable;
import voxel3d.utility.Vector3I;

public class ChunkUpdater implements Executable {
	
	private Chunk[][][] chunks;
	//private Vector3I pos;
	
	private double deltaTime;
	
	public ChunkUpdater(Vector3I pos, Chunk[][][] chunks, double deltaTime)
	{
		//this.pos = pos;
		this.chunks = chunks;
		this.deltaTime = deltaTime;
	}


	//TODO: suport this
	@Override
	public void execute() 
	{
	/*	BlockOnSimulateContext context = new BlockOnSimulateContext(deltaTime, chunks);
		
		//Random block update
		Random random = new Random();
		int rpx = random.nextInt(Settings.CHUNK_SIZE);
		int rpy = random.nextInt(Settings.CHUNK_SIZE);
		int rpz = random.nextInt(Settings.CHUNK_SIZE);
		
		context.setLocalPos(rpx, rpy, rpz);
		chunks[1][1][1].getBlock(rpx, rpy, rpz).onRandomUpdate(context);
		
		for(Entry<Vector3I, BlockSimulable> entry : chunks[1][1][1].getSimulableBlocks())
		{
			context.setLocalPos(entry.getKey().x, entry.getKey().y, entry.getKey().z);
			entry.getValue().onSimulate(context);
		}
		 
		if(context.updateMesh)
		{
			chunks[1][1][1].blockInternalStateChanged();
		}*/
	}

}
