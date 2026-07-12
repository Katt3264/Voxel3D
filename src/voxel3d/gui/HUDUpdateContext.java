package voxel3d.gui;

import voxel3d.block.Block;
import voxel3d.data.Inventory;
import voxel3d.entity.all.ItemEntity;
import voxel3d.item.Item;
import voxel3d.level.World;

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
	
	public void droppItem(Item item) 
	{
		ItemEntity ie = new ItemEntity();
		ie.item = item;
		ie.position.set(world.player.position);
		ie.velocity.set(0,0,0);
		world.addEntity(ie);
	}

}
