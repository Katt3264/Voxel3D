package voxel3d.entity.context;

import java.util.Collection;

import voxel3d.block.Block;
import voxel3d.entity.Entity;
import voxel3d.entity.all.ItemEntity;
import voxel3d.entity.all.Player;
import voxel3d.item.Item;
import voxel3d.level.containers.World;
import voxel3d.physics.Ray;
import voxel3d.utility.Color;
import voxel3d.utility.Vector3I;
import voxel3d.utility.Vector3d;

public class EntityUpdateContext {
	
	private World world;
	
	public EntityUpdateContext(World world)
	{
		this.world = world;
	}
	
	public void createItemEntity(ItemEntity itemEntity)
	{
		world.addEntity(itemEntity);
	}
	
	public boolean terrainRaycast(Ray ray, Vector3I hitPoint, Vector3I hitNormal)
	{
		return world.terrainRaycast(ray, hitPoint, hitNormal);
	}
	
	public Collection<Entity> entityRaycast(Ray ray)
	{
		return world.entityRaycast(ray);
	}
	
	public Block getBlock(int x, int y, int z)
	{
		return world.getBlock(x, y, z);
	}
	
	public void getColor(int x, int y, int z, Color writeback)
	{
		world.getColor(x, y, z, writeback);
	}
	
	public void breakBlock(int x, int y, int z, Item item)
	{
		world.breakBlock(x, y, z, item);
	}
	
	public boolean placeBlock(int x, int y, int z, Block block, Vector3d faceDirection)
	{
		return world.placeBlock(x, y, z, block, faceDirection);
	}
	
	public Vector3d getPlayerPosition()
	{
		return world.player.position;
	}
	
	public Player getPlayer()
	{
		return world.player;
	}
	
	public void addEntity(Entity entity)
	{
		world.addEntity(entity);
	}
	
	/*public void exposeGUIForBlock(Block block)
	{
		guiBlock = block;
	}*/

}
