package voxel3d.global;

public class Settings {
	
	public static final float targetFrameDeltaTime = 1f / 60f;
	public static final float maxFrameDeltaTime = 1f / 30f;
	
	public static final int renderDistance = 6;
	public static final int lightDistance = 16;
	
	public static final int CHUNK_SIZE_LOG = 4;
	public static final int CHUNK_SIZE = 1 << CHUNK_SIZE_LOG;
	public static final int CHUNK_SIZE2 = CHUNK_SIZE*CHUNK_SIZE;
	public static final int CHUNK_SIZE3 = CHUNK_SIZE*CHUNK_SIZE*CHUNK_SIZE;
	
	public static final double randomUpdateInterval = 1;
	public static final int randomUpdatesPerChunk = 10;

	public static final int spawnableLimit = 5;
	public static final double minSpawnRadius = 32;
	public static final double maxSpawnRadius = 64;
	
	public static final boolean loadEnable = true;
	public static final boolean saveEnable = true;
	
	public static final int floatPoolBufferSize = 256;
	public static final int floatPoolArraySize = 256*256;
	
	public static final int IOThreads = 16;
	//public static final int threadCount = 4;
	public static final int taskSaturation = 16;
	public static final int taskWorkerNoTaskSleep = 16;
	public static final int worldTaskSleep = 10;
	
	
	//public static final String[] resourceOrder = new String[]{"Minecraft", "Core"};
	public static final String[] resourceOrder = new String[]{"Core"};
	
	
	public static float brightness = 0.1f;
	
	public static boolean debugScreen = false;
	public static boolean showHud = true;
	public static boolean godMode = false;
	
	public static double mouseSensitivity = 1.0;
	public static double fov = 120;
}
