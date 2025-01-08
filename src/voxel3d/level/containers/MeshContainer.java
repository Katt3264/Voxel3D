package voxel3d.level.containers;

import voxel3d.graphics.Mesh;

public class MeshContainer {
	
	private Mesh mesh;
	private long consistentWith;
	
	public boolean wip = false;
	public long lastChange = System.currentTimeMillis();
	
	public MeshContainer()
	{
		
	}
	
	public void set(Mesh mesh, long consistentWith)
	{
		this.mesh = mesh;
		this.consistentWith = consistentWith;
	}
	
	
	public Mesh getMesh()
	{
		return mesh;
	}
	
	public long getConsistentWith()
	{
		return consistentWith;
	}

}
