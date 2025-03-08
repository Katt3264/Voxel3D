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

public class MainMenu {
	
	private final Button playButton;
	private final Button exitButton;
	
	private static final float cellSize = 0.2f;
	
	public MainMenu()
	{
		playButton = new Button(-cellSize*0.5f, cellSize*0.5f, cellSize, cellSize);
		playButton.mouseOff = Objects.resumeButton;
		playButton.mouseOn = Objects.resumeButtonSelected;
		
		exitButton = new Button(-cellSize*0.5f, -cellSize*1.5f, cellSize, cellSize);
		exitButton.mouseOff = Objects.exitButton;
		exitButton.mouseOn = Objects.exitButtonSelected;
	}
	
	public void update()
	{
		playButton.update();
		exitButton.update();
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
		
		playButton.draw();
		exitButton.draw();
	}
	
	public boolean exit()
	{
		return exitButton.selected && Input.hit.isButtonRelease();
	}
	
	public boolean play()
	{
		return playButton.selected && Input.hit.isButtonRelease();
	}

}
