package voxel3d.level.containers;

import voxel3d.global.Settings;
import voxel3d.global.Time;
import voxel3d.utility.Color;
import voxel3d.utility.MathX;
import voxel3d.utility.Color5;

public class LightContainer {
	
	private byte[] red;
	private byte[] green;
	private byte[] blue;
	private byte[] sky;
    
	private long lastModified;
	private long consistentWith;
	
	public boolean wip = false;
	public boolean stable = false;
	public long lastChange = System.currentTimeMillis();
	
	public LightContainer(float skyLight, float redLight, float greenLight, float blueLight) 
	{
        red = new byte[Settings.CHUNK_SIZE3];
        green = new byte[Settings.CHUNK_SIZE3];
        blue = new byte[Settings.CHUNK_SIZE3];
        sky = new byte[Settings.CHUNK_SIZE3];
        
        for(int i = 0; i < Settings.CHUNK_SIZE3; i++) 
        {
            red[i] = (byte) (redLight * Settings.lightDistance);
            green[i] = (byte) (greenLight * Settings.lightDistance);
            blue[i] = (byte) (blueLight * Settings.lightDistance);
            sky[i] = (byte) (skyLight * Settings.lightDistance);
        }
        
        lastModified = Time.getAtomTime();
        consistentWith = 0;
    }
    
    public void setConsistentWith(long consistentWith)
    {
    	this.consistentWith = consistentWith;
    }
    
    public long getConsistentWith()
    {
    	return this.consistentWith;
    }
    
    public void setLastModified(long lastModified)
    {
    	this.lastModified = lastModified;
    }
    
    public long getLastModified()
    {
    	return this.lastModified;
    }
    
    public byte[] getRed() 
    {        
        return red;
    }
    
    public byte[] getGreen() 
    {
        return green;
    }
    
    public byte[] getBlue() 
    {
        return blue;
    }
    
    public byte[] getSky() 
    {
        return sky;
    }
    
    public void getColor(int x, int y, int z, Color skyColor, Color writeback)
	{
		int intRed   = red[MathX.getXYZ(x, y, z)];
		int intGreen = green[MathX.getXYZ(x, y, z)];
		int intBlue  = blue[MathX.getXYZ(x, y, z)];
		int intSky   = sky[MathX.getXYZ(x, y, z)];
		
		float lightRed 	 = (float)intRed   / (float)Settings.lightDistance;
		float lightGreen = (float)intGreen / (float)Settings.lightDistance;
		float lightBlue  = (float)intBlue  / (float)Settings.lightDistance;
		
		float skyRed   = (skyColor.r * (float)intSky) / (float)Settings.lightDistance;
		float skyGreen = (skyColor.g * (float)intSky) / (float)Settings.lightDistance;
		float skyBlue  = (skyColor.b * (float)intSky) / (float)Settings.lightDistance;
		
		float finalRed   = Math.max(lightRed,   skyRed);
		float finalGreen = Math.max(lightGreen, skyGreen);
		float finalBlue  = Math.max(lightBlue,  skyBlue);
		
		float compositRed   = Settings.brightness + finalRed   * (1f - Settings.brightness);
		float compositGreen = Settings.brightness + finalGreen * (1f - Settings.brightness);
		float compositBlue  = Settings.brightness + finalBlue  * (1f - Settings.brightness);
		
		writeback.set(compositRed, compositGreen, compositBlue);
	}
    
    public void getColor(int x, int y, int z, Color5 result)
	{
		int intRed   = red  [MathX.getXYZ(x, y, z)];
		int intGreen = green[MathX.getXYZ(x, y, z)];
		int intBlue  = blue [MathX.getXYZ(x, y, z)];
		int intSky   = sky  [MathX.getXYZ(x, y, z)];
		
		float lightRed 	 = (float)intRed   / (float)Settings.lightDistance;
		float lightGreen = (float)intGreen / (float)Settings.lightDistance;
		float lightBlue  = (float)intBlue  / (float)Settings.lightDistance;
		float lightSky   = (float)intSky   / (float)Settings.lightDistance;
		
		result.set(lightRed, lightGreen, lightBlue, lightSky);
	}

}
