package voxel3d.gui;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glLoadMatrixd;
import static org.lwjgl.opengl.GL11.glMatrixMode;

import voxel3d.global.Input;
import voxel3d.global.Objects;
import voxel3d.utility.GUIUtill;

public class WorldSelectMenu {
	
	private final Button[] playWorldButtons = new Button[7];
	private final Button exitButton;
	
	private static final float cellSize = 0.2f;
	
	public WorldSelectMenu()
	{
		for(int i = 0; i < playWorldButtons.length; i++)
		{
			Button button = new Button(-cellSize*0.5f, cellSize*2.5f - i*cellSize, cellSize, cellSize);
			button.mouseOff = Objects.resumeButton;
			button.mouseOn = Objects.resumeButtonSelected;
			playWorldButtons[i] = button;
		}
		
		exitButton = new Button(-cellSize*3.5f, -cellSize*1.5f, cellSize, cellSize);
		exitButton.mouseOff = Objects.exitButton;
		exitButton.mouseOn = Objects.exitButtonSelected;
	}
	
	public void update()
	{
		exitButton.update();
		for(int i = 0; i < playWorldButtons.length; i++)
		{
			playWorldButtons[i].update();
		}
	}
	
	public void draw()
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
		
		exitButton.draw();
		for(int i = 0; i < playWorldButtons.length; i++)
		{
			GUIUtill.drawString("World " + i, cellSize*0.5f, cellSize*2.5f - i*cellSize, cellSize);
			playWorldButtons[i].draw();
		}
	}
	
	public boolean exit()
	{
		return exitButton.selected && Input.hit.isButtonRelease();
	}
	
	public int worldSelect()
	{
		int w = -1;
		for(int i = 0; i < playWorldButtons.length; i++)
		{
			if(playWorldButtons[i].selected &&  Input.hit.isButtonRelease())
				w = i;
		}
		return w;
	}

}
