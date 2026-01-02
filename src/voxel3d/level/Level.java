package voxel3d.level;


import java.text.NumberFormat;
import java.util.Locale;

import voxel3d.global.Debug;
import voxel3d.global.Input;
import voxel3d.global.Settings;
import voxel3d.graphics.GUIUtill;
import voxel3d.graphics.Mesh;
import voxel3d.graphics.GraphicsWrapper;
import voxel3d.gui.DeathMenu;
import voxel3d.gui.HUD;
import voxel3d.gui.HUDRenderContext;
import voxel3d.gui.HUDUpdateContext;
import voxel3d.gui.PauseMenu;
import voxel3d.level.world.World;

/**
* This class represents a space where there is a player, one save file, one playthrough
*/
public class Level {
	
	private final World world;
	
	private Level_state state = Level_state.PLAYING;
	private HUD hud;
	private PauseMenu pauesMenu;
	private DeathMenu deathMenu;
	
	public Level(String name)
	{
		hud = new HUD();
		pauesMenu = new PauseMenu();
		deathMenu = new DeathMenu();
		this.world  = new World(Settings.renderDistance, -Settings.renderDistance, -Settings.renderDistance, -Settings.renderDistance, name);
		world.start();
	}
	
	public void draw() 
	{
		Mesh.cleanup();
		
		if(state == Level_state.PLAYING)
		{
			if(!world.isLoading())
			{
				world.render();
				
				GraphicsWrapper.setRenderModeGUI();
				HUDRenderContext hudRenderContext = new HUDRenderContext(world);
				hud.draw(hudRenderContext);
				
				// TODO: make this a menu
				if(Settings.debugScreen)
				{	
					GUIUtill.drawString("Memory use:" + NumberFormat.getNumberInstance(Locale.US).format(Debug.memory) + " B", 	-1f, 0.90f, 0.05f);
					GUIUtill.drawString("fps        " + ((int)(Debug.fps*10f))/10f, 			-1f, 0.85f, 0.05f);
					GUIUtill.drawString("fps low    " + ((int)(Debug.worstfps*10f))/10f, 			-1f, 0.80f, 0.05f);
					
					GUIUtill.drawString("update          " + String.format("%.4G ", (Debug.updateTime / 1E6)) 		+ " ms", 	0f, 0.75f, 0.05f);
					GUIUtill.drawString("render disbatch " + String.format("%.4G ", (Debug.drawDispatchTime / 1E6)) + " ms", 	0f, 0.70f, 0.05f);
					GUIUtill.drawString("wait for render " + String.format("%.4G ", (Debug.waitForDrawTime /  1E6)) + " ms", 	0f, 0.65f, 0.05f);
					GUIUtill.drawString("load            " + String.format("%.4G ", (Debug.load)) + " %", 	0f, 0.50f, 0.05f);
					
					GUIUtill.drawString("gens       " + (Debug.chunkGens), 		-1f, 0.75f, 0.05f);
					GUIUtill.drawString("light      " + (Debug.chunkLights), 	-1f, 0.70f, 0.05f);
					GUIUtill.drawString("build      " + (Debug.chunkBuilds), 	-1f, 0.65f, 0.05f);
					GUIUtill.drawString("ticks      " + (Debug.chunkTicks), 	-1f, 0.60f, 0.05f);
					GUIUtill.drawString("triangles  " + (Debug.triangles), 		-1f, 0.55f, 0.05f);
					
					GUIUtill.drawString("x          " + ((int)world.player.position.x), -1f, 0.50f, 0.05f);
					GUIUtill.drawString("y          " + ((int)world.player.position.y), -1f, 0.45f, 0.05f);
					GUIUtill.drawString("z          " + ((int)world.player.position.z), -1f, 0.40f, 0.05f);
					GUIUtill.drawString("time       " +(world.getClockTime()), -1f, 0.35f, 0.05f);
					
					if(world.player.facing != null)
					{
						float start = 0.30f;
						float step = 0.05f;
						String[] lines = ("Facing:" + world.player.facing.getInfo()).split("\n");
						
						for(int i = 0; i < lines.length; i++)
						{
							GUIUtill.drawString(lines[i], -1f, start, step);
							start -= step;
						}
					}
				}
			}
			else
			{
				//TODO: add background here, use custom loading view
				GraphicsWrapper.setRenderModeGUI();
				GUIUtill.drawString("loading", -1.0f, 0.0f, 0.4f);
				GUIUtill.drawString("chunks " + world.loadProgress, -1.0f, -0.2f, 0.2f);
			}
		}
		else if (state == Level_state.PAUSED)
		{
			world.render();
			pauesMenu.draw();
		} 
		else if (state == Level_state.DEAD)
		{
			world.render();
			deathMenu.draw();
		}
	}

	public void update() 
	{
		if(!isRunning())
			return;
		
		if(state == Level_state.PLAYING)
		{
			world.update();
			HUDUpdateContext hudUpdateContext = new HUDUpdateContext(world);
			hud.update(hudUpdateContext);
			
			if(Input.hud.isButtonPress())
			{
				Settings.showHud = !Settings.showHud;
			}
			
			if(Input.log.isButtonPress())
			{
				Settings.debugScreen = !Settings.debugScreen;
			}
			
			if(Input.god.isButtonPress())
			{
				Settings.godMode = !Settings.godMode;
			}
			
			if(!world.isPlayerAlive())
			{
				Input.showMouse();
				world.pause();
				state = Level_state.DEAD;
			}
			
			if(Input.esc.isButtonPress() && !world.player.inventoryOpen)
			{
				Input.showMouse();
				world.pause();
				state = Level_state.PAUSED;
			}
		}
		else if (state == Level_state.PAUSED)
		{
			pauesMenu.update(world);
			
			if(pauesMenu.exit())
			{
				world.stop();
			}
			if(pauesMenu.resume() || Input.esc.isButtonPress())
			{
				Input.hideMouse();
				world.resume();
				state = Level_state.PLAYING;
			}
		} 
		else if (state == Level_state.DEAD)
		{
			deathMenu.update(world);
			
			if(deathMenu.exit())
			{
				world.stop();
			}
			if(deathMenu.resume())
			{
				world.player.respawn();
				Input.hideMouse();
				world.resume();
				state = Level_state.PLAYING;
			}
		}
	}
	
	public boolean isRunning()
	{
		return world.isRunning();
	}
	
	public void stop()
	{
		world.stop();
	}
	
	private enum Level_state 
	{
		PLAYING,
		PAUSED,
		DEAD,
	}
}
