package voxel3d.gui;

import voxel3d.global.Input;
import voxel3d.global.Objects;
import voxel3d.graphics.GUIUtill;
import voxel3d.graphics.GraphicsWrapper;
import voxel3d.level.world.World;

public class DeathMenu {
	
	private final Button respawnButton;
	private final Button exitButton;
	
	private static final float cellSize = 0.2f;
	
	public DeathMenu()
	{
		respawnButton = new Button(-cellSize*0.5f, cellSize*0.5f, cellSize, cellSize);
		respawnButton.mouseOff = Objects.resumeButton;
		respawnButton.mouseOn = Objects.resumeButtonSelected;
		
		exitButton = new Button(-cellSize*0.5f, -cellSize*1.5f, cellSize, cellSize);
		exitButton.mouseOff = Objects.exitButton;
		exitButton.mouseOn = Objects.exitButtonSelected;
	}
	
	public void update(World world)
	{
		respawnButton.update();
		exitButton.update();
	}
	
	public void draw()
	{
		GraphicsWrapper.setRenderModeGUI();
		GUIUtill.drawString("You died", -0.6f, 0.5f, 0.2f);
		respawnButton.draw();
		exitButton.draw();
	}
	
	public boolean exit()
	{
		return exitButton.selected && Input.hit.isButtonRelease();
	}
	
	public boolean resume()
	{
		return respawnButton.selected && Input.hit.isButtonRelease();
	}

}
