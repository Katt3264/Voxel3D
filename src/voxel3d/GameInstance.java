package voxel3d;

import voxel3d.global.Debug;
import voxel3d.global.Input;
import voxel3d.global.Objects;
import voxel3d.global.Settings;
import voxel3d.global.Time;
import voxel3d.global.Timer;
import voxel3d.graphics.GraphicsWrapper;
import voxel3d.gui.*;
import voxel3d.level.Level;


public class GameInstance {
	
	private static Game_state state = Game_state.MAIN_MENU;
	private static Level level;
	private static MainMenu mainMenu;
	private static WorldSelectMenu worldSelectMenu;
	
	public static void start()
	{
		try
		{
			GraphicsWrapper.init();
			Objects.create();
			mainMenu = new MainMenu();
			worldSelectMenu = new WorldSelectMenu();
			
			Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
			
			while(GraphicsWrapper.window.isOpen() && state != Game_state.QUIT)
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
		if(level != null) {level.stop();}
	}
	
	private static final Timer frameTimer = new Timer(60);
	private static final Timer updateTimer = new Timer(60);
	private static final Timer renderDisbatchTimer = new Timer(60);
	private static final Timer waitForRenderTimer = new Timer(10);
	private static final Timer loadTimer = new Timer(10);
	private static long prevTime;
	//private static long frame = 0;
	private static void loop() throws Exception
	{
		//frame++;
		//boolean poolFrame = (frame % 10) == 0;
		long timeStart = System.nanoTime();
		long elapse = timeStart - prevTime;
		prevTime = timeStart;
		Time.deltaTime = Math.min(Settings.maxFrameDeltaTime, ((float)(elapse) / 1E9f));
		
		//if(poolFrame)
		//	loadTimer.start();
		
		updateTimer.start();
		update();
		updateTimer.stop();
		
		renderDisbatchTimer.start();
		GraphicsWrapper.startFrame();
		draw();
		GraphicsWrapper.endFrame();
		renderDisbatchTimer.stop();
		
		/*if(poolFrame) {
			waitForRenderTimer.start();
			//GraphicsWrapper.endFrame();
			waitForRenderTimer.stop();
			loadTimer.stop();
		}*/
		
		
		//Thread.sleep((long) Math.max(0, (Settings.targetFrameDeltaTime * 1000)-((System.nanoTime() - timeStart) / 1E6)-1));
		
		Debug.memory   			= ((Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()));
		Debug.fps      			= 1E9f / (float)(frameTimer.getAverage());
		Debug.worstfps 			= 1E9f / (float)(frameTimer.getWorst());
		
		Debug.load 			    = (loadTimer.getAverage() / (Settings.targetFrameDeltaTime * 1E9f) * 100f);
		Debug.updateTime 		= (updateTimer.getAverage());
		Debug.drawDispatchTime 	= (renderDisbatchTimer.getAverage());
		Debug.waitForDrawTime 	= (waitForRenderTimer.getAverage());
		
		/*Debug.load 			    = (loadTimer.getWorst() / (Settings.targetFrameDeltaTime * 1E9f) * 100f);
		Debug.updateTime 		= (updateTimer.getWorst());
		Debug.drawDispatchTime 	= (renderDisbatchTimer.getWorst());
		Debug.waitForDrawTime 	= (waitForRenderTimer.getWorst());
		Debug.waitForSwapTime 	= (waitForSwapTimer.getWorst());*/
	}
	
	private static void draw()
	{
		if(state == Game_state.MAIN_MENU)
		{
			GraphicsWrapper.setRenderModeGUI();
			mainMenu.draw();
		}
		else if(state == Game_state.WORLD_SELECT)
		{
			GraphicsWrapper.setRenderModeGUI();
			worldSelectMenu.draw();
		}
		else if(state == Game_state.LEVEL_PLAY)
		{
			level.draw();
		}
	}
	
	private static void update()
	{
		Input.update();
		
		if(state == Game_state.MAIN_MENU)
		{
			mainMenu.update();
			
			if(mainMenu.play())
			{
				state = Game_state.WORLD_SELECT;
			}
			else if(mainMenu.exit())
			{
				state = Game_state.QUIT;
			}
		}
		else if(state == Game_state.WORLD_SELECT)
		{
			worldSelectMenu.update();
			if(worldSelectMenu.worldSelect() != -1)
			{
				level = new Level("world " + worldSelectMenu.worldSelect());
				state = Game_state.LEVEL_PLAY;
			}
			else if(worldSelectMenu.exit())
			{
				state = Game_state.MAIN_MENU;
			}
			
		}
		else if(state == Game_state.LEVEL_PLAY)
		{
			level.update();
			
			if(!level.isRunning())
			{
				level = null;
				state = Game_state.MAIN_MENU;
			}
		}
	}

	private enum Game_state 
	{
		MAIN_MENU,
		WORLD_SELECT,
		//LEVEL_LOAD,
		LEVEL_PLAY,
		//LEVEL_SAVE,
		QUIT,
	}
}
