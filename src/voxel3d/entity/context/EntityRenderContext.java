package voxel3d.entity.context;

import voxel3d.entity.Entity;
import voxel3d.graphics.GraphicsWrapper;
import voxel3d.level.world.World;
import voxel3d.utility.Color;

public class EntityRenderContext {
	
	private World world;
	
	public EntityRenderContext(World world)
	{
		this.world = world;
	}
	
	public long getTimeMilliseconds()
	{
		return System.currentTimeMillis();
	}

	public void setEntityIlluminationLight(Entity entity)
	{
		Color color = new Color();
		world.getColor((int)Math.floor(entity.position.x), (int)Math.floor(entity.position.y), (int)Math.floor(entity.position.z), color);
		GraphicsWrapper.SetEntityRenderLight(color);
	}
}
