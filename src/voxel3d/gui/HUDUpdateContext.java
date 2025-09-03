package voxel3d.gui;

import voxel3d.block.Block;
import voxel3d.data.Inventory;
import voxel3d.level.world.World;

public class HUDUpdateContext {
	
	private World world;
	
	public HUDUpdateContext(World world)
	{
		this.world = world;
	}
	
	public Inventory getInventory()
	{
		return world.player.inventory;
	}
	
	public Block getPlayerFacing()
	{
		return world.player.facing;
	}
	
	public boolean isInventoryOpen()
	{
		return world.player.inventoryOpen;
	}

}
