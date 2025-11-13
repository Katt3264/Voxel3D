package voxel3d.level;

import voxel3d.global.Objects;
import voxel3d.graphics.GraphicsWrapper;
import voxel3d.utility.Color;
import voxel3d.utility.Vector3d;


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
		
		//float r1 = (float) Math.min(Math.max(-Math.cos(angle)*2 + 1.8, 0), 1);
		//float r2 = (float) Math.min(Math.max(-Math.cos(angle)*2 + 1.2, 0), 1);
		//float r3 = (float) Math.min(Math.max(-Math.cos(angle)*2 + 0.6, 0), 1);
		
		//writeback.set(r1, r2, r3);
		
		//5:25 first sun
		//6:35 full sun
		float r0 = (float) Math.min(Math.max((0.25-Math.cos(angle))*2, 0), 1);
		writeback.set(r0, r0, r0);
	}
	
	public void render()
	{
		double sunradAng = time*2.0*Math.PI; // 0 24:00
		
		//float skyFadeIn = (float) Math.min(1, Math.max(0, -Math.cos(sunradAng) * 4));
		float skyFadeIn = (float) Math.min(Math.max((0.25-Math.cos(sunradAng))*2, 0), 1);
		
		Vector3d upX = new Vector3d(Math.cos(sunradAng), Math.sin(sunradAng),0);
		Vector3d upY = new Vector3d(-Math.sin(sunradAng), Math.cos(sunradAng),0);
		
		Vector3d x = new Vector3d(1, 0, 0);
		Vector3d y = new Vector3d(0, 1, 0);
		Vector3d z = new Vector3d(0, 0, 1);
		
		
		getLight(skyColor);
		
		// stars
		GraphicsWrapper.renderSkybox(upX, upY, z, new Color(1,1,1), 1, Objects.skyBoxStars);
		
		// sky
		GraphicsWrapper.renderSkybox(x, y, z, new Color(1,1,1), skyFadeIn, Objects.skyBoxDay);

		// sun
		GraphicsWrapper.renderSkybox(upX, upY, z, new Color(1,1,1), 1, Objects.skyBoxSun);
		
		// moon
		GraphicsWrapper.renderSkybox(upX, upY, z, new Color(1,1,1), 1, Objects.skyBoxMoon);
		
		// horizon
		GraphicsWrapper.renderSkybox(x, y, z, new Color(0 * skyColor.r, 0.5f * skyColor.g, 0.8f * skyColor.b), 1, Objects.skyBoxHorizon);
	}
	
}
