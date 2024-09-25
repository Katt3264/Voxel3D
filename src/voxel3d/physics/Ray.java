package voxel3d.physics;

import voxel3d.utility.Vector3d;

public class Ray {
	
	public Vector3d start;
	public Vector3d direction;
	public double length;
	
	public Ray(Vector3d start, Vector3d direction, double length)
	{
		this.start = start;
		this.direction = direction;
		this.length = length;
	}

}
