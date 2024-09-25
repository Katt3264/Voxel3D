package voxel3d.gui;

import static org.lwjgl.opengl.GL11.*;

import voxel3d.block.Block;
import voxel3d.global.Objects;
import voxel3d.global.Settings;

public class HUD {
	
	private Hotbar hotbar;
	private InventoryGUI inventoryGUI;
	private HealthBar healthBar;
	private HintGUI hintGUI;
	private BlockHintGUI blockHintGUI;
	
	public HUD()
	{
		hotbar = new Hotbar();
		inventoryGUI = new InventoryGUI();
		healthBar = new HealthBar();
		hintGUI = new HintGUI();
		blockHintGUI = new BlockHintGUI();
	}
	
	public void update(HUDUpdateContext context)
	{
		hotbar.update(context);
		blockHintGUI.update(context);
		if(context.isInventoryOpen())
		{
			inventoryGUI.update(context);
			
			Block facing = context.getPlayerFacing();
			
			HUDInteractable gui = getGUIForBlock(facing);
			gui.update(context);
			
			hintGUI.update(context);
		}
	}
	
	public void draw(HUDRenderContext context)
	{
		glDisable(GL_CULL_FACE);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glLoadMatrixd(Objects.window.getNormalMatrix());
		
		glDisable(GL_DEPTH_TEST);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		
		
		if(Settings.showHud)
		{
			glColor3f(1.0f, 1.0f, 1.0f);
			glBegin(GL_QUADS);
			glVertex2d(-0.025, -0.025);
			glVertex2d(-0.025, 0.025);
			glVertex2d(0.025, 0.025);
			glVertex2d(0.025, -0.025);
			glEnd();
		
			hotbar.draw(context);
			healthBar.draw(context);
			blockHintGUI.draw(context);
		}
		
		if(context.isInventoryOpen())
		{
			Block facing = context.getPlayerFacing();
			
			HUDInteractable gui = getGUIForBlock(facing);
			gui.draw(context);
			
			inventoryGUI.draw(context);
			
			hintGUI.draw(context);
		}
	}
	
	private HUDInteractable getGUIForBlock(Block block)
	{
		if(block.getHUD() != null)
		{
			return block.getHUD();
		}
		else
		{
			return Objects.craftingHUD;
		}
	}
}
