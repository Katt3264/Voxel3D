package voxel3d.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import voxel3d.global.Debug;
import voxel3d.global.Settings;
import voxel3d.level.world.Chunk;
import voxel3d.level.world.World;
import voxel3d.utility.Executable;
import voxel3d.utility.TaskWorker;
import voxel3d.utility.Vector3I;

public class DataLoader{
	
	
	public static void loadLevel(World world)
	{
		try {
			world.player.read(getInPath("store/worlds/" + world.name + "/player_data"));
		} catch (FileNotFoundException e) {
		} catch (IOException e) {e.printStackTrace();}
		
		try {
			world.read(getInPath("store/worlds/" + world.name + "/world_data"));
		} catch (FileNotFoundException e) {
		} catch (IOException e) {e.printStackTrace();}
		
		File[] chunks = new File("store/worlds/" + world.name + "/chunks").listFiles();
		if(chunks != null)
		{
			TaskWorker loader = new TaskWorker(Settings.IOThreads);
			
			world.loadProgress = 0;
			for(File file : chunks)
			{
				if(!file.getName().contains("chunk"))
					continue;
				
				loader.addTask(new ChunkLoader(file, world));
			}
			
			loader.start();
			loader.completeAllTasks();
			loader.stop();
		}
	}
	
	private static class ChunkLoader implements Executable 
	{
		private File chunkFile;
		private World world;
		
		public ChunkLoader(File file, World world)
		{
			this.chunkFile = file;
			this.world = world;
		}
		
		@Override
		public void execute() 
		{
			try {
				Vector3I pos = new Vector3I();
				String name = chunkFile.getName();
				
				name = name.replaceFirst("chunk", "");
				String[] cords = name.split(",");
				
				pos.set(Integer.parseInt(cords[0]), Integer.parseInt(cords[1]), Integer.parseInt(cords[2]));
				
				Chunk chunk = world.forceGetChunk(pos.x, pos.y, pos.z);
				chunk.read(getInPath(chunkFile.getPath()));
				
				world.loadProgress++;
			} catch (IOException e){e.printStackTrace();
			} catch (NumberFormatException e){e.printStackTrace();}
		}
	}
	
	
	private static DataInputStream getInPath(String path) throws IOException
	{
		Debug.ioLog("loading: " + path);
		
		FileInputStream fis = new FileInputStream(path);
		byte[] data = fis.readAllBytes();
		fis.close();
		
		return new DataInputStream(data);
	}
	
	public static void saveLevel(World world)
	{
		try {
			DataOutputStream dos = new DataOutputStream();
			world.player.write(dos);
			saveFileSmart("store/worlds/" + world.name + "/player_data", dos);
		} catch (IOException e) {e.printStackTrace();}
		
		try {
			DataOutputStream dos = new DataOutputStream();
			world.write(dos);
			saveFileSmart("store/worlds/" + world.name + "/world_data", dos);
		} catch (IOException e) {e.printStackTrace();}
		
		
		TaskWorker loader = new TaskWorker(Settings.IOThreads);
		world.loadProgress = 0;
		for(Vector3I pos : world.getAllChunkPositions())
	    {
			Chunk chunk = world.tryGetChunk(pos.x, pos.y, pos.z);
			if(chunk == null)
				continue;
			
			loader.addTask(new ChunkSaver(chunk, world));
	    }
		
		loader.start();
		loader.completeAllTasks();
		loader.stop();
	}
	
	private static class ChunkSaver implements Executable 
	{
		private Chunk chunk;
		private World world;
		
		public ChunkSaver(Chunk chunk, World world)
		{
			this.chunk = chunk;
			this.world = world;
		}
		
		@Override
		public void execute() 
		{
			try {
				if(!chunk.isPopulated)
					return;
				
				String path = "store/worlds/" + world.name + "/chunks/chunk" + chunk.cx + "," + chunk.cy + "," + chunk.cz + "";
				DataOutputStream dos = new DataOutputStream();
				chunk.write(dos);
				saveFileSmart(path, dos);
				world.loadProgress++;
			} catch (IOException e){e.printStackTrace();}
		}
	}
	
	private static void saveFileSmart(String path, DataOutputStream dos) throws IOException
	{
		Debug.ioLog("saving: " + path);
		
		File file = new File(path);
		file.getParentFile().mkdirs();
		file.createNewFile();
		
		boolean overrideExisting = false;
		byte[] data = dos.getByteArray();
		
		FileInputStream fis = new FileInputStream(path);
		byte[] existing = new byte[data.length];
		int l = fis.read(existing);
		fis.close();
		
		if(l == data.length) 
		{
			for(int i = 0; i < data.length; i++)
			{
				if(data[i] != existing[i])
					overrideExisting = true;
			}
		}
		else
		{
			overrideExisting = true;
		}
		
		if(overrideExisting)
		{
			FileOutputStream fos = new FileOutputStream(path, false);
			fos.write(data);
			fos.close();
		}
	}

}
