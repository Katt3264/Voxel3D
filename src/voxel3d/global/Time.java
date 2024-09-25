package voxel3d.global;

import java.util.concurrent.atomic.AtomicLong;

public class Time {
	
	public static double deltaTime = 0;
	
	private static AtomicLong atomTime = new AtomicLong();
	public static long getAtomTime()
	{
		return atomTime.getAndAdd(1);
	}
	
	
	//public static float frameTime;
	//public static final float maxDeltaTime = 0.05f;

}
