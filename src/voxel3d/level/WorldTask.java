package voxel3d.level;


import java.util.Map.Entry;

import voxel3d.global.Settings;
import voxel3d.level.containers.MeshContainer;
import voxel3d.level.containers.BlockContainer;
import voxel3d.level.containers.LightContainer;
import voxel3d.level.containers.World;
import voxel3d.utility.Vector3I;

public class WorldTask {
	
	
	private LightContainer[][][] lights = new LightContainer[3][3][3];
	private BlockContainer[][][] blocks = new BlockContainer[3][3][3];
	private long prevTimeSimulation = 0;
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
		
		for(int x = 0 ; x < size; x++)
		{
			for(int y = 0 ; y < size; y++)
			{
				for(int z = 0 ; z < size; z++)
				{
					enforceNotNull(x,y,z);
				}
			}
		}
		
		
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
							if(checkGen(x,y,z))
								chunkGens--;
						
						long minElapsedMillisBuild = r <= 1 ? 10 : 100;
						long minElapsedMillisLight = r <= 1 ? 10 : r*r*1000;
						boolean priority = (r <= 1);
						
						if(chunkLights > 0 || priority)
							if(checkLight(x,y,z,minElapsedMillisLight,priority))
								chunkLights--;
						
						if(chunkBuilds > 0 || priority)
							if(checkMesh(x,y,z,minElapsedMillisBuild,priority))
								chunkBuilds--;
					}
				}
			}
		}
		
	}
	
	private void performSimulation() 
	{
		if(worldScheduler.workerChunkSimulate.getTaskCount() != 0)
			return;
		
		long nowTime = world.getTime();
		double elapse = (double)(nowTime - prevTimeSimulation) / (1E9d);
		prevTimeSimulation = nowTime;
		
		
		Iterable<Entry<Vector3I, BlockContainer>> allBlocks = world.getAllBlockContainers();
		
		for(Entry<Vector3I, BlockContainer> entry : allBlocks)
		{
			if(!world.getBufferedBlockContainers(entry.getKey().x, entry.getKey().y, entry.getKey().z, blocks))
				continue;
			
			ChunkUpdater updater = new ChunkUpdater(entry.getKey(), blocks, elapse);
			worldScheduler.workerChunkSimulate.addTask(updater);
			
			blocks = new BlockContainer[3][3][3];
		}
		
		return;
	}
	
	private void enforceNotNull(int x, int y, int z)
	{
		if(world.getPersistentBlockContainer(offset.x + x, offset.y + y, offset.z + z) == null) 
		{
			BlockContainer blockContainer = null;
			
			if(blockContainer == null)
			{
				blockContainer = world.getBufferedBlockContainer(
						offset.x + x, offset.y + y, offset.z + z);
			}
			
			if(blockContainer == null)
			{
				blockContainer = new BlockContainer();
			}
			
			world.setBlockContainer(offset.x + x, offset.y + y, offset.z + z, blockContainer);
		}
		
		if(world.getPersistentLightContainer(offset.x + x, offset.y + y, offset.z + z) == null)
		{
			LightContainer lightContainer = ChunkPopulator.getInitialLight(offset.x + x, offset.y + y, offset.z + z);
			world.setLightContainer(offset.x + x, offset.y + y, offset.z + z, lightContainer);
		}
			
		if(world.getMeshContainer(offset.x + x, offset.y + y, offset.z + z) == null)
		{
			MeshContainer meshContainer = new MeshContainer();
			world.setMeshContainer(offset.x + x, offset.y + y, offset.z + z, meshContainer);
		}
	}
	
	//TODO: priority check
	private boolean checkGen(int x, int y, int z)
	{
		BlockContainer blockContainer = world.getPersistentBlockContainer(offset.x + x, offset.y + y, offset.z + z);
		if(blockContainer == null)
			return false;
		
		if(!blockContainer.isGenerated() && !blockContainer.wip)
		{
			ChunkPopulator populator = new ChunkPopulator(offset.x + x, offset.y + y, offset.z + z, blockContainer);
			worldScheduler.workerChunkGen.addTask(populator);
			return true;
		}
		return false;
	}
	
	private boolean checkLight(int x, int y, int z, long minElapsedMillis, boolean priority)
	{
		MeshContainer mesh = world.getMeshContainer(offset.x + x, offset.y + y, offset.z + z);
		if(mesh == null) 
			return false;
		
		if(!world.getPersistentLightContainers(offset.x + x, offset.y + y, offset.z + z, lights))
			return false;
		
		if(!world.getPersistentBlockContainers(offset.x + x, offset.y + y, offset.z + z, blocks))
			return false;
		
		if(lights[1][1][1].lastChange + minElapsedMillis > System.currentTimeMillis())
			return false;
		
		long lightVer = 0;
		long blockVer = 0;
		for(int xx = 0; xx < 3; xx++)
		{
			for(int yy = 0; yy < 3; yy++)
			{
				for(int zz = 0; zz < 3; zz++)
				{
					if(!blocks[xx][yy][zz].isGenerated())
						return false;
					
					if(!adj(xx,yy,zz))
						continue;
					
					lightVer = Math.max(lightVer, lights[xx][yy][zz].getLastModified());
					blockVer = Math.max(blockVer, blocks[xx][yy][zz].getLastModified());
				}
			}
		}
		
		boolean rebuild = false;
		
		if(blockVer > lights[1][1][1].getConsistentWith() || lightVer > lights[1][1][1].getConsistentWith())
				rebuild = true;
		
		if(rebuild && !lights[1][1][1].wip)
		{
			long newContainerConsistency = Math.max(blockVer, lightVer);
			ChunkLightBuilder lightBuilder = new ChunkLightBuilder(lights, blocks[1][1][1], newContainerConsistency);
			if(priority)
				worldScheduler.workerChunkLight.addPriorityTask(lightBuilder);
			else
				worldScheduler.workerChunkLight.addTask(lightBuilder);
			lights = new LightContainer[3][3][3];
			return true;
		}
		return false;
	}
	
	private boolean checkMesh(int x, int y, int z, long minElapsedMillis, boolean priority)
	{
		MeshContainer mesh = world.getMeshContainer(offset.x + x, offset.y + y, offset.z + z);	
		if(mesh == null) 
			return false;
		
		if(mesh.lastChange + minElapsedMillis > System.currentTimeMillis())
			return false;
		
		if(!world.getPersistentLightContainers(offset.x + x, offset.y + y, offset.z + z, lights))
			return false;
		
		if(!world.getPersistentBlockContainers(offset.x + x, offset.y + y, offset.z + z, blocks))
			return false;
		
		long lightVer = 0;
		long blockVer = 0;
		for(int xx = 0; xx < 3; xx++)
		{
			for(int yy = 0; yy < 3; yy++)
			{
				for(int zz = 0; zz < 3; zz++)
				{
					if(!blocks[xx][yy][zz].isGenerated())
						return false;
					
					if(!adj(xx,yy,zz))
						continue;
					
					lightVer = Math.max(lightVer, lights[xx][yy][zz].getLastModified());
					blockVer = Math.max(blockVer, blocks[xx][yy][zz].getLastModified());
					
					// only build when light has stabilized
					//if(!lights[xx][yy][zz].stable)
						//return false;
				}
			}
		}
		
		boolean rebuild = false;
		
		if(blockVer > mesh.getConsistentWith() || lightVer > mesh.getConsistentWith())
			rebuild = true;
		
		if(rebuild && !mesh.wip)
		{
			long newContainerConsistency = Math.max(blockVer, lightVer);
			ChunkMeshBuilder meshBuilder = new ChunkMeshBuilder(blocks, lights, mesh, newContainerConsistency);
			if(priority)
				worldScheduler.workerChunkMesh.addPriorityTask(meshBuilder);
			else
				worldScheduler.workerChunkMesh.addTask(meshBuilder);
			lights = new LightContainer[3][3][3];
			blocks = new BlockContainer[3][3][3];
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
