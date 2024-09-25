package voxel3d.entity.all;

import voxel3d.audio.AudioSource;
import voxel3d.entity.Entity;
import voxel3d.entity.context.EntityRenderContext;
import voxel3d.entity.context.EntityUpdateContext;
import voxel3d.global.Objects;
import voxel3d.physics.AABB;
import voxel3d.utility.GeometryUtility;
import voxel3d.utility.Vector3d;

public class Rocket extends Entity {
	
	public AudioSource audioSource;

	static {
		Entity.setEntityDeserializerForType(new Rocket());
	}
	
	
	
	@Override
	public void update(EntityUpdateContext context)
	{
		super.update(context);
		
		if(contacted)
		{
			this.alive = false;
			Objects.audioManager.playSound(position, Objects.explosion);
			if(audioSource != null)
			{
				audioSource.stop();
			}
		}
		
		if(audioSource != null)
		{
			audioSource.setPosition((float)position.x, (float)position.y, (float)position.z);
			audioSource.setVelocity((float)velocity.x, (float)velocity.y, (float)velocity.z);
		}
	}
	
	
	@Override
	public void draw(EntityRenderContext context)
	{
		super.draw(context);
		double size = 0.2;
		GeometryUtility.drawBox(new Vector3d(0, 0, 0), new Vector3d(size, 0, 0), new Vector3d(0, size, 0), new Vector3d(0, 0, size), Objects.slime);
	}
	
	@Override
	public boolean getAABB(AABB writeback)
	{
		double size = 0.1;
		writeback.set(position.x - size, position.y - size, position.z - size, position.x + size, position.y + size, position.z + size);
		return true;
	}

	@Override
	public String getType()
	{
		return super.getType() + ":Rocket";
	}

	@Override
	public Entity getDataStreamableInstance() 
	{
		return new Rocket();
	}

}
