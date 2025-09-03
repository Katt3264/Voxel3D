package voxel3d.level.world;

import voxel3d.global.Settings;
import voxel3d.level.ChunkLightBuilder;
import voxel3d.level.ChunkMeshBuilder;
import voxel3d.level.ChunkPopulator;
import voxel3d.utility.Vector3I;

public class WorldTask {
	
	private Vector3I offset = new Vector3I();
	private World world;
	private WorldScheduler worldScheduler;
	
	public WorldTask()
	{
		
	}
		
	public void performAction(World world, WorldScheduler worldScheduler) 
	{
		
		int halfSize = world.getRenderDistance();
		int size = 2 * halfSize + 1;
		world.getOffset(offset);
		this.world = world;
		this.worldScheduler = worldScheduler;
		
		performSimulation();
		
		int chunkGens = Settings.taskSaturation*4 - worldScheduler.workerChunkGen.getTaskCount();
		int chunkLights = Settings.taskSaturation - worldScheduler.workerChunkLight.getTaskCount();
		int chunkBuilds = Settings.taskSaturation - worldScheduler.workerChunkMesh.getTaskCount();
		
		//Chunk population is simpler to manage this way	
		for(int r = 0; r <= world.getRenderDistance(); r++)
		{
			for(int x = 0 ; x < size; x++)
			{
				for(int y = 0 ; y < size; y++)
				{
					for(int z = 0 ; z < size; z++)
					{
						int trueR = Math.max(Math.abs(x-halfSize), Math.max(Math.abs(y-halfSize), Math.abs(z-halfSize)));
						if(trueR != r)
							continue;
						
						if(chunkGens > 0)
							if(checkGen(x + offset.x, y + offset.y, z + offset.z))
								chunkGens--;
						
						long minElapsedMillisBuild = r <= 1 ? 10 : 100;
						long minElapsedMillisLight = r <= 1 ? 10 : r*r*1000;
						boolean priority = (r <= 1);
						
						if(chunkLights > 0 || priority)
							if(checkLight(x + offset.x, y + offset.y, z + offset.z, minElapsedMillisLight, priority))
								chunkLights--;
						
						if(chunkBuilds > 0 || priority)
							if(checkMesh(x + offset.x, y + offset.y, z + offset.z, minElapsedMillisBuild, priority))
								chunkBuilds--;
					}
				}
			}
		}
		
	}
	
	//TODO: support this
	private void performSimulation() 
	{
		/*if(worldScheduler.workerChunkSimulate.getTaskCount() != 0)
			return;
		
		long nowTime = world.getTime();
		double elapse = (double)(nowTime - prevTimeSimulation) / (1E9d);
		prevTimeSimulation = nowTime;
		
		
		Iterable<Entry<Vector3I, Chunk>> allBlocks = world.getAllBlockContainers();
		
		for(Entry<Vector3I, Chunk> entry : allBlocks)
		{
			//has neibours check
			
			ChunkUpdater updater = new ChunkUpdater(entry.getKey(), entry.getValue().neibours, elapse);
			worldScheduler.workerChunkSimulate.addTask(updater);
		}
		
		return;*/
	}
	
	//TODO: priority check
	private boolean checkGen(int x, int y, int z)
	{
		Chunk chunk = world.getChunk(x,y,z);
		
		if(!chunk.isPopulated && !chunk.isBeingPopulated)
		{
			ChunkPopulator populator = new ChunkPopulator(x, y, z, chunk);
			worldScheduler.workerChunkGen.addTask(populator);
			return true;
		}
		return false;
	}
	
	//TODO: implement
	private boolean checkLight(int x, int y, int z, long minElapsedMillis, boolean priority)
	{
		Chunk chunk = world.getChunk(x,y,z);
		
		Chunk[][][] chunkN = chunk.tryGetNeibours(world);
		if(chunkN == null)
			return false;
		
		long blockVer = 0;
		for(int xx = 0; xx < 3; xx++)
		{
			for(int yy = 0; yy < 3; yy++)
			{
				for(int zz = 0; zz < 3; zz++)
				{
					if(!chunkN[xx][yy][zz].isPopulated)
						return false;
					
					if(!adj(xx,yy,zz))
						continue;
					
					blockVer = Math.max(blockVer, chunkN[xx][yy][zz].lastModified);
				}
			}
		}
		
		boolean rebuild = false;
		
		if(blockVer > chunk.consistentLight)
			rebuild = true;
		
		
		if(rebuild && !chunk.buildingLight)
		{
			ChunkLightBuilder lightBuilder = new ChunkLightBuilder(chunkN, blockVer);
			
			if(priority)
				worldScheduler.workerChunkMesh.addPriorityTask(lightBuilder);
			else
				worldScheduler.workerChunkMesh.addTask(lightBuilder);
			
			return true;
		}
		return false;
	}
	
	private boolean checkMesh(int x, int y, int z, long minElapsedMillis, boolean priority)
	{
		Chunk chunk = world.getChunk(x,y,z);
		
		Chunk[][][] chunkN = chunk.tryGetNeibours(world);
		if(chunkN == null)
			return false;
		
		long blockVer = 0;
		for(int xx = 0; xx < 3; xx++)
		{
			for(int yy = 0; yy < 3; yy++)
			{
				for(int zz = 0; zz < 3; zz++)
				{
					if(!chunkN[xx][yy][zz].isPopulated)
						return false;
					
					if(!adj(xx,yy,zz))
						continue;
					
					blockVer = Math.max(blockVer, chunkN[xx][yy][zz].lastModified);
				}
			}
		}
		
		boolean rebuild = false;
		
		if(blockVer > chunk.consistentMesh)
			rebuild = true;
		
		
		if(rebuild && !chunk.buildingMesh)
		{
			chunk.consistentMesh = blockVer;
			ChunkMeshBuilder meshBuilder = new ChunkMeshBuilder(chunkN);
			
			if(priority)
				worldScheduler.workerChunkMesh.addPriorityTask(meshBuilder);
			else
				worldScheduler.workerChunkMesh.addTask(meshBuilder);
			
			return true;
		}
		return false;
	}
	
	private boolean adj(int xx, int yy, int zz)
	{
		int num = 0;
		
		if(xx == 1)
			num++;
		
		if(yy == 1)
			num++;
		
		if(zz == 1)
			num++;
		
		return num >= 2;
	}
}
