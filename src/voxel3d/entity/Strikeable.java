package voxel3d.entity;

import voxel3d.utility.Vector3d;

public interface Strikeable {

	public void strike(Vector3d direction, double damage);
	
}
