package voxel3d.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import voxel3d.global.Debug;
import voxel3d.global.Settings;
import voxel3d.level.world.World;

public class DataLoader{
	
	
	public static void loadLevel(World world)
	{
		if(!Settings.loadEnable)
			return;
		
		try {
			world.player.read(getInPath("store/worlds/" + world.name + "/player_data"));
		} catch (FileNotFoundException e) {
		} catch (IOException e) {e.printStackTrace();}
		
		try {
			world.read(getInPath("store/worlds/" + world.name + "/world_data"));
		} catch (FileNotFoundException e) {
		} catch (IOException e) {e.printStackTrace();}
		
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
		if(!Settings.saveEnable)
			return;
		
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
	}
	
	
	public static void saveFileSmart(String path, DataOutputStream dos) throws IOException
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
