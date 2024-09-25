package voxel3d.gui;

import voxel3d.global.Input;
import voxel3d.global.Objects;
import voxel3d.level.containers.World;

public class PauseMenu {
	
	private final Button resumeButton;
	private final Button exitButton;
	
	private static final float cellSize = 0.2f;
	
	public PauseMenu()
	{
		resumeButton = new Button(-cellSize*0.5f, cellSize*0.5f, cellSize, cellSize);
		resumeButton.mouseOff = Objects.resumeButton;
		resumeButton.mouseOn = Objects.resumeButtonSelected;
		
		exitButton = new Button(-cellSize*0.5f, -cellSize*1.5f, cellSize, cellSize);
		exitButton.mouseOff = Objects.exitButton;
		exitButton.mouseOn = Objects.exitButtonSelected;
	}
	
	public void update(World world)
	{
		resumeButton.update();
		exitButton.update();
	}
	
	public void draw()
	{
		resumeButton.draw();
		exitButton.draw();
	}
	
	public boolean exit()
	{
		return exitButton.selected && Input.hit.isButtonRelease();
	}
	
	public boolean resume()
	{
		return resumeButton.selected && Input.hit.isButtonRelease();
	}

}
