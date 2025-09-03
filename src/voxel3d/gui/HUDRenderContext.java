package voxel3d.gui;

import voxel3d.block.Block;
import voxel3d.data.Inventory;
import voxel3d.entity.all.Player;
import voxel3d.level.world.World;

public class HUDRenderContext {
	
private World world;
	
	private String guiHint = "";
	
	public HUDRenderContext(World world)
	{
		this.world = world;
	}
	
	public Inventory getInventory()
	{
		return world.player.inventory;
	}
	
	public boolean isInventoryOpen()
	{
		return world.player.inventoryOpen;
	}
	
	public Block getPlayerFacing()
	{
		return world.player.facing;
	}
	
	public Player getPlayer()
	{
		return world.player;
	}
	
	public void setGuiHint(String hint)
	{
		guiHint = hint;
	}
	
	public String getGuiHint()
	{
		return guiHint;
	}

}
