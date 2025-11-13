package voxel3d.gui;

import voxel3d.block.Block;
import voxel3d.global.Objects;
import voxel3d.global.Settings;
import voxel3d.graphics.GUIUtill;

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
		if(Settings.showHud)
		{
			GUIUtill.drawSquare(-0.025f, -0.025f, 0.05f, Objects.crosshair);
		
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
