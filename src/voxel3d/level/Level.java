package voxel3d.level;

/**
* This class represents a space where there is a player, one save file, one playthrough
*/
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glLoadMatrixd;
import static org.lwjgl.opengl.GL11.glMatrixMode;

import java.text.NumberFormat;
import java.util.Locale;

import voxel3d.global.Debug;
import voxel3d.global.Input;
import voxel3d.global.Objects;
import voxel3d.global.Settings;
import voxel3d.graphics.Mesh;
import voxel3d.gui.HUD;
import voxel3d.gui.HUDRenderContext;
import voxel3d.gui.HUDUpdateContext;
import voxel3d.gui.PauseMenu;
import voxel3d.level.world.World;
import voxel3d.utility.GUIUtill;

public class Level {
	
	private final World world;
	
	private HUD hud;
	private PauseMenu pauesMenu;
	
	
	public Level(String name)
	{
		hud = new HUD();
		pauesMenu = new PauseMenu();
		this.world  = new World(Settings.renderDistance, -Settings.renderDistance, -Settings.renderDistance, -Settings.renderDistance, name);
		world.start();
	}
	
	public void draw() 
	{
		Mesh.cleanup();
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glClearColor(0, 0, 1, 1.0f);
		
		if(!world.isLoading())
		{
			world.render();
			
			HUDRenderContext hudRenderContext = new HUDRenderContext(world);
			hud.draw(hudRenderContext);
			if(world.isPaused())
			{
				pauesMenu.draw();
			}
			//glDisable(GL_ALPHA_TEST);
			
			if(Settings.debugScreen)
			{	
				GUIUtill.drawString("Memory use:" + NumberFormat.getNumberInstance(Locale.US).format(Debug.memory) + " B", 	-1f, 0.90f, 0.05f);
				GUIUtill.drawString("fps        " + ((int)(Debug.fps*10f))/10f, 			-1f, 0.85f, 0.05f);
				GUIUtill.drawString("fps low    " + ((int)(Debug.worstfps*10f))/10f, 			-1f, 0.80f, 0.05f);
				
				GUIUtill.drawString("update          " + String.format("%.4G ", (Debug.updateTime / 1E6)) 		+ " ms", 	0f, 0.75f, 0.05f);
				GUIUtill.drawString("render disbatch " + String.format("%.4G ", (Debug.drawDispatchTime / 1E6)) + " ms", 	0f, 0.70f, 0.05f);
				GUIUtill.drawString("wait for render " + String.format("%.4G ", (Debug.waitForDrawTime /  1E6)) + " ms", 	0f, 0.65f, 0.05f);
				GUIUtill.drawString("wait for swap   " + String.format("%.4G ", (Debug.waitForSwapTime /  1E6)) + " ms", 	0f, 0.60f, 0.05f);
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
			glDisable(GL_CULL_FACE);
			glMatrixMode(GL_PROJECTION);
			glLoadIdentity();
			glLoadMatrixd(Objects.window.getNormalMatrix());
			
			glDisable(GL_DEPTH_TEST);
			glMatrixMode(GL_MODELVIEW);
			glLoadIdentity();
			
			glClearColor(0, 0, 0, 1.0f);
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			
			glColor3f(1f, 1f, 1f);
			
			GUIUtill.drawString("loading", -1.0f, 0.0f, 0.4f);
			GUIUtill.drawString("chunks " + world.loadProgress, -1.0f, -0.2f, 0.2f);
		}
	}

	public void update() 
	{
		if(!isRunning())
			return;
		
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
		
		if(Input.esc.isButtonPress() && !world.player.inventoryOpen)
		{
			if(world.isPaused())
			{
				Input.hideMouse();
				world.resume();
			}
			else
			{
				Input.showMouse();
				world.pause();
			}
		}
		
		world.update();
		
		if(!world.isPaused())
		{
			//TODO: this can move in to world
			HUDUpdateContext hudUpdateContext = new HUDUpdateContext(world);
			hud.update(hudUpdateContext);
		}
		else
		{
			pauesMenu.update(world);
			
			if(pauesMenu.exit())
			{
				world.stop();
			}
			if(pauesMenu.resume())
			{
				Input.hideMouse();
				world.resume();
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
}
