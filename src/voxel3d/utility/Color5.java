package voxel3d.utility;

public class Color5 {
	
	public float r, g, b, sky, norm;

	public Color5(float r, float g, float b, float sky)
	{
		this.r = r;
		this.b = b;
		this.g = g;
		this.sky = sky;
		this.norm = 1.0f;
	}
	
	public void set(float r, float g, float b, float sky)
	{
		this.r = r;
		this.b = b;
		this.g = g;
		this.sky = sky;
		this.norm = 1.0f;
	}
	
	public Color5 setNormal(float f)
	{
		norm = f;
		return this;
	}
	
}
