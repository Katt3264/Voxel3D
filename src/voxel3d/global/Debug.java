package voxel3d.global;


public class Debug {
	
	public static int triangles = 0;
	
	public static float fps;
	public static float worstfps;
	//public static float load;
	public static long memory;
	
	public static long updateTime;
	public static long drawDispatchTime;
	//public static long drawTime;
	//public static long waitForSwap;
	//public static long frameTime;
	
	/*public static double t1;
	public static double t2;
	public static double t3;
	public static double t4;
	public static double t5;*/
	
	public static long chunkGens = 0;
	public static long chunkBuilds = 0;
	public static long chunkLights = 0;
	public static long chunkTicks = 0;
	
	//public static long chunkBuildTime = 0;
	//public static long chunkLightTime = 0;
	
	public static void log(String string)
	{
		System.out.println("[DEBUG] " + string);
	}
	
	public static void assetLoadLog(String string)
	{
		//System.out.println("[ASSET] " + string);
	}
	
	public static void ioLog(String string)
	{
		//System.out.println("[I/O] " + string);
	}
	
	public static void timerLog(String string)
	{
		System.out.println("[TIME] " + string);
	}
	
	public static void unimplemented(String string)
	{
		System.err.println("[UNIMPLEMENTED] " + string);
	}
	
	public static void err(String string)
	{
		System.err.println("[ERROR] " + string);
	}
	

}
