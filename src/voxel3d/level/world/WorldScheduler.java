package voxel3d.level.world;

import voxel3d.data.DataLoader;
import voxel3d.global.Debug;
import voxel3d.global.Objects;
import voxel3d.global.Settings;
import voxel3d.utility.MainThreadExecutable;
import voxel3d.utility.TaskWorker;

public class WorldScheduler {
	

	private final World world;
	private final WorldScheduler scheduler;
	private final Runner runner;
	
	public final TaskWorker workerChunkGen;
	public final TaskWorker workerChunkLight;
	public final TaskWorker workerChunkMesh;
	public final TaskWorker workerChunkSimulate;
	
	public boolean loadingInProgress = true;
	private boolean alive = true;
	
	public WorldScheduler(World world)
	{
		scheduler = this;
		this.world = world;
		workerChunkGen = new TaskWorker(1);
		workerChunkLight = new TaskWorker(1);
		workerChunkMesh = new TaskWorker(1);
		workerChunkSimulate = new TaskWorker(1);
		runner = new Runner();
	}
	
	//TODO: potential lagg spikes
	public void mainThreadFrameEntry()
	{
		while(Objects.mainQueue.size() != 0)
		{
			MainThreadExecutable executable = Objects.mainQueue.poll();
			if(executable != null) 
			{
				executable.executeOnMainThread();
			}
		}
	}
	
	public void start()
	{
		workerChunkGen.start();
		workerChunkLight.start();
		workerChunkMesh.start();
		workerChunkSimulate.start();
		runner.start();
	}
	
	public void pause()
	{
		workerChunkGen.pause();
		workerChunkLight.pause();
		workerChunkMesh.pause();
		workerChunkSimulate.pause();
	}
	
	public void resume()
	{
		workerChunkGen.resume();
		workerChunkLight.resume();
		workerChunkMesh.resume();
		workerChunkSimulate.resume();
	}
	
	public boolean isRunning()
	{
		return alive;
	}
	
	public void stop()
	{
		loadingInProgress = true;
		runner.terminate();
		workerChunkGen.stop();
		workerChunkLight.stop();
		workerChunkMesh.stop();
		workerChunkSimulate.stop();
	}
	
	
	private class Runner extends Thread {
		
		private boolean running = true;
		private WorldTask task = new WorldTask();
		
		
		public void run() 
		{
			if(Settings.loadEnable)
			{
				DataLoader.loadLevel(world);
			}
			loadingInProgress = false;
			
			while(running) 
			{
				try 
				{
					task.performAction(world, scheduler);
					Thread.sleep(Settings.worldTaskSleep);
				}
				catch (Exception e)
				{
					e.printStackTrace();
					terminate();
				}
			}
			
			if(Settings.saveEnable)
			{
				Debug.log("saving level");
				DataLoader.saveLevel(world);
		        Debug.log("saving complete");
		        
			}
			loadingInProgress = false;
			alive = false;
		}
		
		public void terminate()
		{
			running = false;
		}
	}
}
