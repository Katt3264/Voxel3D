package voxel3d.utility;

public class Vector3d {
	
	/*public static final Vector3d right = new Vector3d(1,0,0);
	public static final Vector3d left = new Vector3d(-1,0,0);
	public static final Vector3d up = new Vector3d(0,1,0);
	public static final Vector3d down = new Vector3d(0,-1,0);
	public static final Vector3d forward = new Vector3d(0,0,1);
	public static final Vector3d back = new Vector3d(0,0,-1);
	
	public static final Vector3d zero = new Vector3d(0,0,0);*/
	
	public double x, y, z;
	
	public Vector3d()
	{
	}
	
	public Vector3d(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void set(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void set(Vector3d v)
	{
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
	}
	
	public void setCross(Vector3d a, Vector3d b)
	{
		this.x = a.y*b.z - a.z*b.y;
		this.y = a.z*b.x - a.x*b.z;
		this.z = a.x*b.y - a.y*b.x;
	}
	
	public double sqrMagnitude()
	{
		return x*x + y*y + z*z;
	}
	
	public double magnitude()
	{
		return Math.sqrt(sqrMagnitude());
	}
	
	//public Vector3d
	
	public void add(Vector3d v)
	{
		x += v.x;
		y += v.y;
		z += v.z;
	}
	
	public void add(double x, double y, double z)
	{
		this.x += x;
		this.y += y;
		this.z += z;
	}
	
	public void subtract(Vector3d v)
	{
		x -= v.x;
		y -= v.y;
		z -= v.z;
	}
	
	public void multiply(double d)
	{
		x *= d;
		y *= d;
		z *= d;
	}
	
	public double dot(Vector3d v)
	{
		return x*v.x + y*v.y + z*v.z;
	}
	
	public void normalize()
	{
		this.multiply(1d / (this.magnitude() + 0.000001d));
	}

}
