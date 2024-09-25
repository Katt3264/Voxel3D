package voxel3d.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map.Entry;

import voxel3d.entity.Entity;
import voxel3d.global.Debug;
import voxel3d.global.Settings;
import voxel3d.level.containers.BlockContainer;
import voxel3d.level.containers.World;
import voxel3d.utility.Executable;
import voxel3d.utility.TaskWorker;
import voxel3d.utility.Vector3I;

public class DataLoader {
	
	
	public static void loadLevel(World level)
	{
		try {
			level.player.inventory.read(getInPath("store/worlds/" + level.name + "/player/inventory"));
		} catch (FileNotFoundException e) {
		} catch (IOException e) {e.printStackTrace();}
		
		try {
			level.player.read(getInPath("store/worlds/" + level.name + "/player/player data"));
		} catch (FileNotFoundException e) {
		} catch (IOException e) {e.printStackTrace();}
		
		try {
			level.addEntities(getInPath("store/worlds/" + level.name + "/entities/entity data").readEntities());
		} catch (FileNotFoundException e) {
		} catch (IOException e) {e.printStackTrace();}
		
		try {
			level.read(getInPath("store/worlds/" + level.name + "/world/world data"));
		} catch (FileNotFoundException e) {
		} catch (IOException e) {e.printStackTrace();}
		
		
		File[] chunks = new File("store/worlds/" + level.name + "/chunks").listFiles();
		if(chunks != null)
		{
			TaskWorker loader = new TaskWorker(Settings.IOThreads);
			
			level.loadProgress = 0;
			for(File file : chunks)
			{
				if(!file.getName().contains("chunk"))
					continue;
				
				loader.addTask(new ChunkLoader(file, level));
			}
			
			loader.start();
			loader.completeAllTasks();
			loader.stop();
		}
	}
	
	private static class ChunkLoader implements Executable 
	{
		private File file;
		private World world;
		
		public ChunkLoader(File file, World world)
		{
			this.file = file;
			this.world = world;
		}
		
		@Override
		public void execute() 
		{
			try {
				Vector3I pos = new Vector3I();
				String name = file.getName();
				name = name.replaceFirst("chunk", "");
				String[] cords = name.split(",");
				pos.set(Integer.parseInt(cords[0]), Integer.parseInt(cords[1]), Integer.parseInt(cords[2]));
				BlockContainer container = new BlockContainer();
				container.read(getInPath(file.getPath()));
				world.setBufferedBlockContainer(pos.x, pos.y, pos.z, container);
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
	
	public static void saveLevel(World level)
	{
		try {
			DataOutputStream dos = new DataOutputStream();
			level.player.inventory.write(dos);
			saveFileSmart("store/worlds/" + level.name + "/player/inventory", dos);
		} catch (IOException e) {e.printStackTrace();}
		
		try {
			DataOutputStream dos = new DataOutputStream();
			level.player.write(dos);
			saveFileSmart("store/worlds/" + level.name + "/player/player data", dos);
		} catch (IOException e) {e.printStackTrace();}
		
		try {
			Collection<Entity> entities = new ArrayList<Entity>();
			level.getEntities().forEach(entities::add);
			entities.remove(level.player);
			DataOutputStream dos = new DataOutputStream();
			dos.writeEntities(entities);
			saveFileSmart("store/worlds/" + level.name + "/entities/entity data", dos);
		} catch (IOException e) {e.printStackTrace();}
		
		try {
			DataOutputStream dos = new DataOutputStream();
			level.write(dos);
			saveFileSmart("store/worlds/" + level.name + "/world/world data", dos);
		} catch (IOException e) {e.printStackTrace();}
		
		
		TaskWorker loader = new TaskWorker(Settings.IOThreads);
		level.loadProgress = 0;
		for(Entry<Vector3I,BlockContainer> entry : level.getAllBlockContainers())
	    {
			loader.addTask(new ChunkSaver(entry, level));
	    }
		
		loader.start();
		loader.completeAllTasks();
		loader.stop();
	}
	
	private static class ChunkSaver implements Executable 
	{
		private Entry<Vector3I,BlockContainer> entry;
		private World world;
		
		public ChunkSaver(Entry<Vector3I,BlockContainer> entry, World world)
		{
			this.entry = entry;
			this.world = world;
		}
		
		@Override
		public void execute() 
		{
			try {
				Vector3I position = entry.getKey();
				BlockContainer container = entry.getValue();
				
				if(!container.isGenerated())
					return;
				
				String path = "store/worlds/" + world.name + "/chunks/chunk" + position.x + "," + position.y + "," + position.z + "";
				DataOutputStream dos = new DataOutputStream();
				container.write(dos);
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
