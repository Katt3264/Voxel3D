package voxel3d.item.context;

import voxel3d.block.Block;
import voxel3d.entity.Entity;
import voxel3d.entity.all.Player;
import voxel3d.entity.context.EntityUpdateContext;

public class ItemUseContext {
	
	public Block placeBlock = null;
	public int hungerPoint = 0;
	
	private EntityUpdateContext context;
	public ItemUseContext(EntityUpdateContext context)
	{
		this.context = context;
	}
	
	public void placeBlock(Block block)
	{
		placeBlock = block;
	}
	
	public void consume(int hungerPoints)
	{
		this.hungerPoint = hungerPoints;
	}
	
	public Player getPlayer()
	{
		return context.getPlayer();
	}
	
	public void addEntity(Entity entity)
	{
		context.addEntity(entity);
	}

}
