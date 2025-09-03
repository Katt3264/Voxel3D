package voxel3d.entity;

import voxel3d.level.world.World;

public interface Spawnable {
	
	public boolean trySpawn(int x, int y, int z, World world);

}
