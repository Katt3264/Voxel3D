package voxel3d.level;

import java.util.Random;

import voxel3d.block.context.BlockOnSimulateContext;
import voxel3d.global.Settings;
import voxel3d.level.world.Chunk;
import voxel3d.utility.Executable;

public class ChunkUpdater implements Executable {
	
	private Chunk[][][] chunks;
	
	public ChunkUpdater(Chunk[][][] chunks)
	{
		this.chunks = chunks;
	}


	@Override
	public void execute() 
	{
		Chunk chunk = chunks[1][1][1];

		Random random = new Random();
		BlockOnSimulateContext randomContext = new BlockOnSimulateContext(chunks);
		for(int i = 0; i < Settings.randomUpdatesPerChunk; i++)
		{
			int rpx = random.nextInt(Settings.CHUNK_SIZE);
			int rpy = random.nextInt(Settings.CHUNK_SIZE);
			int rpz = random.nextInt(Settings.CHUNK_SIZE);
			randomContext.setLocalPos(rpx, rpy, rpz);
			chunk.getBlock(rpx, rpy, rpz).onRandomUpdate(randomContext);
		}
		
		//TODO: support simulation blocks
		/*BlockOnSimulateContext context = new BlockOnSimulateContext(deltaTime, chunks);
		for(Entry<Vector3I, BlockSimulable> entry : chunks[1][1][1].getSimulableBlocks())
		{
			context.setLocalPos(entry.getKey().x, entry.getKey().y, entry.getKey().z);
			entry.getValue().onSimulate(context);
		}*/
		 
		if(randomContext.updateMesh)
		{
			chunk.setRebuildMeshFlag();
		}
	}

}
