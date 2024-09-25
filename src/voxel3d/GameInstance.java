package voxel3d;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL20.*;

import voxel3d.global.Debug;
import voxel3d.global.Input;
import voxel3d.global.Objects;
import voxel3d.global.Settings;
import voxel3d.global.Time;
import voxel3d.global.Timer;
import voxel3d.level.Level;


public class GameInstance {
	
	private static Level level;
	
	public static void start()
	{
		try
		{
			Objects.create();
			
			level = new Level("default world");
			level.start();
			
			Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
			
			while(Objects.window.isOpen() && level.isAlive())
			{
				loop();
			}
		}
		catch(Throwable e)
		{
			e.printStackTrace();
		}
		
		Objects.destroy();
		level.destroy();
	}
	
	private static final Timer timer = new Timer(60);
	private static final Timer renderDisbatchTimer = new Timer(60);
	private static final Timer updateTimer = new Timer(60);
	private static long prevTime;
	private static void loop() throws Exception
	{
		long timeStart = System.nanoTime();
		timer.start();
		renderDisbatchTimer.start();
		draw();
		renderDisbatchTimer.stop();
		//glfwPollEvents();
		updateTimer.start();
		update();
		updateTimer.stop();
		glFinish();
		//timer.stop();
		
		glfwSwapBuffers(Objects.window.getID());
		glfwPollEvents();
		
		timer.stop();
		long elapse = timeStart - prevTime;
		
        Debug.memory   			= ((Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()));
        Debug.fps      			= 1E9f / (float)(timer.getAverage());
        Debug.worstfps 			= 1E9f / (float)(timer.getWorst());
        Debug.updateTime 		= (updateTimer.getAverage());
        Debug.drawDispatchTime 	= (renderDisbatchTimer.getAverage());
        
        Time.deltaTime = Math.min(Settings.maxFrameDeltaTime, ((float)(elapse) / 1E9f));
        
        prevTime = timeStart;
	}
	
	private static void draw()
	{
		level.draw();
	}
	
	private static void update()
	{
		Input.update();
		level.update();
	}

}
