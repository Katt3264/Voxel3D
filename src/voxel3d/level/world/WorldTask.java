package voxel3d.level.world;

import voxel3d.global.Settings;
import voxel3d.level.ChunkLightBuilder;
import voxel3d.level.ChunkMeshBuilder;
import voxel3d.level.ChunkPopulator;
import voxel3d.level.ChunkUnloader;
import voxel3d.level.ChunkUpdater;
import voxel3d.utility.Vector3I;

public class WorldTask {
	
	//TODO: remove
	private World world;
	private WorldScheduler worldScheduler;
	
	public WorldTask()
	{
		
	}
		
	public void performAction(World world, WorldScheduler worldScheduler) 
	{
		int halfSize = world.getRenderDistance();
		int size = 2 * halfSize + 1;
		Vector3I offset = new Vector3I();
		world.getOffset(offset);
		this.world = world;
		this.worldScheduler = worldScheduler;
		
		unloadCleanup(false);
		
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
	
	public void unloadCleanup(boolean force) 
	{
		Vector3I offset = new Vector3I();
		world.getOffset(offset);
		Vector3I center = new Vector3I();
		center.set(offset.x, offset.y, offset.z);
		center.x += world.getRenderDistance();
		center.y += world.getRenderDistance();
		center.z += world.getRenderDistance();
		
		if(worldScheduler.workerChunkUnload.getTaskCount() != 0)
			return;
		
		for(Vector3I pos : world.getAllChunkPositions())
		{
			Chunk chunk = world.tryGetChunk(pos.x, pos.y, pos.z);
			if(chunk == null)
				continue;
			
			int r = Math.max(Math.abs(pos.x - center.x), Math.max(Math.abs(pos.y - center.y), Math.abs(pos.z - center.z)));
			if(r <= world.getRenderDistance() + 0 && !force)
				continue;
			
			if(chunk.shouldSave() && !chunk.isSaving) 
			{
				//TODO: repsect settings.saveEnable
				ChunkUnloader unloader = new ChunkUnloader(chunk, world.name);
				if(force)
					unloader.execute();
				else
					worldScheduler.workerChunkUnload.addTask(unloader);
			}
			else if (!chunk.shouldSave()) 
			{
				world.removeChunk(pos.x, pos.y, pos.z);
			}
			
		}
		return;
	}
	
	private double prevTimeSimulation = 0;
	private void performSimulation() 
	{
		if((world.getTime() - prevTimeSimulation) / 1E9d < Settings.randomUpdateInterval)
			return;
		
		if(worldScheduler.workerChunkSimulate.getTaskCount() != 0)
			return;
		
		long nowTime = world.getTime();
		//double elapse = (double)(nowTime - prevTimeSimulation) / (1E9d);
		prevTimeSimulation = nowTime;
		
		
		for(Vector3I pos : world.getAllChunkPositions())
		{
			Chunk chunk = world.tryGetChunk(pos.x, pos.y, pos.z);
			if(chunk == null)
				continue;
			
			Chunk[][][] chunkN = chunk.tryGetNeibours(world, new Chunk[3][3][3]);
			if(chunkN == null)
				continue;
			
			ChunkUpdater updater = new ChunkUpdater(chunkN);
			worldScheduler.workerChunkSimulate.addTask(updater);
		}
		
		return;
	}
	
	private boolean checkGen(int x, int y, int z)
	{
		Chunk chunk = world.forceGetChunk(x,y,z);
		
		if(!chunk.isPopulated && !chunk.isBeingPopulated)
		{
			ChunkPopulator populator = new ChunkPopulator(x, y, z, world.name, chunk);
			worldScheduler.workerChunkGen.addTask(populator);
			return true;
		}
		return false;
	}
	
	private boolean checkLight(int x, int y, int z, long minElapsedMillis, boolean priority)
	{
		Chunk chunk = world.tryGetChunk(x,y,z);
		if(chunk == null)
			return false;
		
		
		Chunk[][][] chunkN = chunk.tryGetNeibours(world, new Chunk[3][3][3]);
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
		Chunk chunk = world.tryGetChunk(x,y,z);
		if(chunk == null)
			return false;
		
		Chunk[][][] chunkN = chunk.tryGetNeibours(world, new Chunk[3][3][3]);
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
