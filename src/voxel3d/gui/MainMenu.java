package voxel3d.gui;

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
		//TODO: render background
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
