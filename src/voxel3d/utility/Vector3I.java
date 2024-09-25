package voxel3d.utility;

public class Vector3I implements Comparable<Vector3I> {
	
	public int x;
	public int y;
	public int z;
	
	public Vector3I()
	{
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	
	public Vector3I(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void set(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public int squareDistance(Vector3I pos)
	{
		return Math.max(Math.max(Math.abs(x - pos.x), Math.abs(y - pos.y)), Math.abs(z - pos.z));
	}

	@Override
	public int compareTo(Vector3I other) {
		
		if(this.x > other.x) {return 1;}
		if(this.x < other.x) {return -1;}
		
		if(this.y > other.y) {return 1;}
		if(this.y < other.y) {return -1;}
		
		if(this.z > other.z) {return 1;}
		if(this.z < other.z) {return -1;}
		
		return 0;
	}
	
	@Override
    public int hashCode() {
		return 11*x + 19*y + 31*z;
    }
        
    @Override
    public boolean equals(Object o) 
    {
    	if(o.getClass() != this.getClass()) {return false;}
    	
    	Vector3I vec = (Vector3I) o;
    		
    	return this.x == vec.x && this.y == vec.y && this.z == vec.z;
    }

}
