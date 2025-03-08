package voxel3d;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL40.*;

import voxel3d.global.Debug;
import voxel3d.global.Input;
import voxel3d.global.Objects;
import voxel3d.global.Settings;
import voxel3d.global.Time;
import voxel3d.global.Timer;
import voxel3d.gui.*;
import voxel3d.level.Level;


public class GameInstance {
	
	private static Level_state state = Level_state.MAIN_MENU;
	private static Level level;
	private static MainMenu mainMenu;
	private static WorldSelectMenu worldSelectMenu;
	
	public static void start()
	{
		try
		{
			Objects.create();
			mainMenu = new MainMenu();
			worldSelectMenu = new WorldSelectMenu();
			
			Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
			
			while(Objects.window.isOpen() && state != Level_state.QUIT)
			{
				frameTimer.start();
				loop();
				frameTimer.stop();
			}
		}
		catch(Throwable e)
		{
			e.printStackTrace();
		}
		
		Objects.destroy();
		if(level != null) {level.destroy();}
	}
	
	private static final Timer frameTimer = new Timer(60);
	private static final Timer updateTimer = new Timer(60);
	private static final Timer renderDisbatchTimer = new Timer(60);
	private static final Timer waitForRenderTimer = new Timer(10);
	private static final Timer waitForSwapTimer = new Timer(60);
	private static final Timer loadTimer = new Timer(10);
	private static long prevTime;
	private static long frame = 0;
	private static void loop() throws Exception
	{
		frame++;
		boolean poolFrame = (frame % 10) == 0;
		long timeStart = System.nanoTime();
		long elapse = timeStart - prevTime;
		prevTime = timeStart;
		Time.deltaTime = Math.min(Settings.maxFrameDeltaTime, ((float)(elapse) / 1E9f));
		
		if(poolFrame)
			loadTimer.start();
		
		updateTimer.start();
		glfwPollEvents();
		update();
		updateTimer.stop();
		
		renderDisbatchTimer.start();
		draw();
		glFlush();
		renderDisbatchTimer.stop();
		
		if(poolFrame) {
			waitForRenderTimer.start();
			glFinish();
			waitForRenderTimer.stop();
			loadTimer.stop();
		}
		
		Thread.sleep((long) Math.max(0, (Settings.targetFrameDeltaTime * 1000)-((System.nanoTime() - timeStart) / 1E6)-1));
		
		waitForSwapTimer.start();
		glfwSwapBuffers(Objects.window.getID());
		waitForSwapTimer.stop();
		
		Debug.memory   			= ((Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()));
		Debug.fps      			= 1E9f / (float)(frameTimer.getAverage());
		Debug.worstfps 			= 1E9f / (float)(frameTimer.getWorst());
		
		Debug.load 			    = (loadTimer.getAverage() / (Settings.targetFrameDeltaTime * 1E9f) * 100f);
		Debug.updateTime 		= (updateTimer.getAverage());
		Debug.drawDispatchTime 	= (renderDisbatchTimer.getAverage());
		Debug.waitForDrawTime 	= (waitForRenderTimer.getAverage());
		Debug.waitForSwapTime 	= (waitForSwapTimer.getAverage());
		
		/*Debug.load 			    = (loadTimer.getWorst() / (Settings.targetFrameDeltaTime * 1E9f) * 100f);
		Debug.updateTime 		= (updateTimer.getWorst());
		Debug.drawDispatchTime 	= (renderDisbatchTimer.getWorst());
		Debug.waitForDrawTime 	= (waitForRenderTimer.getWorst());
		Debug.waitForSwapTime 	= (waitForSwapTimer.getWorst());*/
	}
	
	private static void draw()
	{
		if(state == Level_state.MAIN_MENU)
		{
			mainMenu.draw();
		}
		else if(state == Level_state.WORLD_SELECT)
		{
			worldSelectMenu.draw();
		}
		else if(state == Level_state.LEVEL_PLAY)
		{
			level.draw();
		}
	}
	
	private static void update()
	{
		Input.update();
		
		if(state == Level_state.MAIN_MENU)
		{
			mainMenu.update();
			
			if(mainMenu.play())
			{
				state = Level_state.WORLD_SELECT;
			}
			else if(mainMenu.exit())
			{
				state = Level_state.QUIT;
			}
		}
		else if(state == Level_state.WORLD_SELECT)
		{
			worldSelectMenu.update();
			if(worldSelectMenu.worldSelect() != -1)
			{
				level = new Level("world " + worldSelectMenu.worldSelect());
				state = Level_state.LEVEL_PLAY;
			}
			else if(worldSelectMenu.exit())
			{
				state = Level_state.MAIN_MENU;
			}
			
		}
		else if(state == Level_state.LEVEL_PLAY)
		{
			if(level.isAlive())
			{
				level.update();
			}
			else
			{
				level.destroy();
				level = null;
				state = Level_state.MAIN_MENU;
			}
		}
	}

	private enum Level_state 
	{
		MAIN_MENU,
		WORLD_SELECT,
		//LEVEL_LOAD,
		LEVEL_PLAY,
		//LEVEL_SAVE,
		QUIT,
	}
}
