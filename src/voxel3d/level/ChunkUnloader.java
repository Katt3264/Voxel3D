package voxel3d.level;


import voxel3d.data.DataLoader;
import voxel3d.data.DataOutputStream;
import voxel3d.global.Settings;
import voxel3d.utility.Executable;

public class ChunkUnloader implements Executable {
	
	private final Chunk chunk;
	private final String worldName;
	
	public ChunkUnloader(Chunk chunk, String worldName)
	{
		this.chunk = chunk;
		this.worldName = worldName;
		chunk.isSaving = true;
	}
	
	public void execute()
	{	
		chunk.lastSaved = chunk.lastModified;
		
		try {
			if(Settings.saveEnable)
			{
				String path = "store/worlds/" + worldName + "/chunks/chunk" + chunk.cx + "," + chunk.cy + "," + chunk.cz + "";
				DataOutputStream dos = new DataOutputStream();
				chunk.write(dos);
				DataLoader.saveFileSmart(path, dos);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		chunk.isSaving = false;
	}
	
}
