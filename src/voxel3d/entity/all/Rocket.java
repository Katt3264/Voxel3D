package voxel3d.entity.all;

import voxel3d.audio.AudioSource;
import voxel3d.entity.Entity;
import voxel3d.entity.Strikeable;
import voxel3d.entity.context.EntityRenderContext;
import voxel3d.entity.context.EntityUpdateContext;
import voxel3d.global.Objects;
import voxel3d.global.Time;
import voxel3d.graphics.GeometryUtility;
import voxel3d.physics.AABB;
import voxel3d.physics.Ray;
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
		boolean explode = false;
		
		if(contacted)
		{
			explode = true;
		}
		
		if(audioSource != null)
		{
			audioSource.setPosition((float)position.x, (float)position.y, (float)position.z);
			audioSource.setVelocity((float)velocity.x, (float)velocity.y, (float)velocity.z);
		}
		
		Vector3d normalVelociy = new Vector3d();
		normalVelociy.set(velocity);
		normalVelociy.normalize();
		
		Ray ray = new Ray(position, normalVelociy, velocity.magnitude() * 2 * Time.deltaTime);
		for(Entity other : context.entityRaycast(ray))
		{
			if(other instanceof Strikeable)
			{
				Strikeable hit = (Strikeable)other;
				Vector3d dir = new Vector3d();
				dir.set(velocity);
				dir.normalize();
				dir.multiply(4);
				hit.strike(dir, 50);
				explode = true;
			}
		}
		
		if(explode)
			explode();
	}
	
	private void explode()
	{
		if(audioSource != null)
		{
			audioSource.stop();
		}
		this.alive = false;
		AudioSource as = Objects.audioManager.playSound(position, Objects.explosion);
		as.setGain(0.05f);
		
	}
	
	@Override
	public void render(EntityRenderContext context)
	{
		super.render(context);
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
