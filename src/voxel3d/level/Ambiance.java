package voxel3d.level;

import voxel3d.global.Objects;
import voxel3d.utility.Color;
import voxel3d.utility.GeometryUtility;
import voxel3d.utility.Vector3d;

import static org.lwjgl.opengl.GL20.*;

public class Ambiance {
	
	public double time = 0.25;
	private Color skyColor = new Color();
	
	
	public void setTime(double time)
	{
		this.time = time;
	}
	
	//one cycle is 24 minutes
	public void elapse(long nano)
	{
		time += (nano / (1000_000_000d * 60d * 24d)) * 1d;
	}
	
	public String getClockTime()
	{
		int sec = (int)Math.floor(time * 60d * 60d * 24d) % (60 * 60 * 24);
		int min = Math.floorDiv(sec, 60) % 60;
		int hour = Math.floorDiv(sec, 60 * 60) % 24;
		
		return hour + " " + min;
	}
	
	public void getLight(Color writeback)
	{
		double angle = time * Math.PI * 2d;
		
		float r1 = (float) Math.min(Math.max(-Math.cos(angle)*2 + 1.8, 0), 1);
		float r2 = (float) Math.min(Math.max(-Math.cos(angle)*2 + 1.2, 0), 1);
		float r3 = (float) Math.min(Math.max(-Math.cos(angle)*2 + 0.6, 0), 1);
		
		
		writeback.set(r1, r2, r3);
	}
	
	public void draw()
	{
		double sunradAng = time*2.0*Math.PI; // 0 24:00
		
		float skyFadeIn = (float) Math.min(1, Math.max(0, -Math.cos(sunradAng) * 4));
		
		Vector3d upX = new Vector3d(Math.cos(sunradAng), Math.sin(sunradAng),0);
		Vector3d upY = new Vector3d(-Math.sin(sunradAng), Math.cos(sunradAng),0);
		
		Vector3d x = new Vector3d(1, 0, 0);
		Vector3d y = new Vector3d(0, 1, 0);
		Vector3d z = new Vector3d(0, 0, 1);
		
		
		getLight(skyColor);
		glClearColor(skyColor.r, skyColor.g, skyColor.b, 1.0f);
		glColor3f(1,1,1);
		
		glColor4f(1,1,1,1);
		
		// stars
		GeometryUtility.drawBox(
				new Vector3d(0,0,0), upX, upY, z, 
				GeometryUtility.skyBoxMapUV,
				GeometryUtility.skyBoxMap,
				Objects.skyBoxStars);
		
		// sky
		glColor4f(1,1,1,skyFadeIn);
		GeometryUtility.drawBox(
				new Vector3d(0,0,0), x, y, z, 
				GeometryUtility.skyBoxMapUV,
				GeometryUtility.skyBoxMap,
				Objects.skyBoxDay);
		
		
		
		
		// sun
		glColor4f(1,1,1,1);
		GeometryUtility.drawBox(
			new Vector3d(0,0,0), upX, upY, z, 
			GeometryUtility.skyBoxMapUV,
			GeometryUtility.skyBoxMap,
			Objects.skyboxSun);
		
		// moon
		GeometryUtility.drawBox(
				new Vector3d(0,0,0), upX, upY, z, 
				GeometryUtility.skyBoxMapUV,
				GeometryUtility.skyBoxMap,
				Objects.skyboxMoon);
		
		
	}
	
	/*private static void drawSky(Texture[] texs, double t)
	{
		Texture primary = null;
		Texture secondary = null;
		float alpha = 0;
		if(t > 0.75)
		{
			primary = texs[3];
			secondary = texs[0];
			alpha = (float) (t*4 - 3);
			//sunset -> night
		}
		else if(t > 0.5)
		{
			primary = texs[2];
			secondary = texs[3];
			alpha = (float) (t*4 - 2);
			//day -> sunset
		}
		else if(t > 0.25)
		{
			primary = texs[1];
			secondary = texs[2];
			alpha = (float) (t*4 - 1);
			//sinrise -> day
		}
		else // t > 0
		{
			primary = texs[0];
			secondary = texs[1];
			alpha = (float) (t*4);
			//night -> sunset
		}
		
		if(primary != null)
		{
			glColor4f(1,1,1,1);
			GeometryUtility.drawBox(
				new Vector3d(0,0,0), new Vector3d(1,0,0), new Vector3d(0,1,0), new Vector3d(0,0,1),
				GeometryUtility.skyBoxMapUV,
				GeometryUtility.skyBoxMap,
				primary);
		}
		
		if(secondary != null)
		{
			glColor4f(1,1,1,alpha);
			GeometryUtility.drawBox(
				new Vector3d(0,0,0), new Vector3d(1,0,0), new Vector3d(0,1,0), new Vector3d(0,0,1),
				GeometryUtility.skyBoxMapUV,
				GeometryUtility.skyBoxMap,
				secondary);
		}
		
	}*/
}
