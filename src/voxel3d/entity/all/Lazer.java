package voxel3d.entity.all;

import voxel3d.audio.AudioSource;
import voxel3d.entity.Entity;
import voxel3d.entity.Strikeable;
import voxel3d.entity.context.EntityRenderContext;
import voxel3d.entity.context.EntityUpdateContext;
import voxel3d.global.Objects;
import voxel3d.global.Time;
import voxel3d.physics.AABB;
import voxel3d.physics.Ray;
import voxel3d.utility.GeometryUtility;
import voxel3d.utility.Vector3d;

public class Lazer extends Entity {
	

	static {
		Entity.setEntityDeserializerForType(new Lazer());
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
				dir.multiply(1);
				hit.strike(dir, 50);
				explode = true;
			}
		}
		
		if(explode)
			explode();
	}
	
	private void explode()
	{
		this.alive = false;
		//AudioSource as = Objects.audioManager.playSound(position, Objects.explosion);
		//as.setGain(0.05f);
		
	}
	
	@Override
	public void draw(EntityRenderContext context)
	{
		//super.draw(context);
		double size = 0.2;
		GeometryUtility.drawBox(new Vector3d(0, 0, 0), new Vector3d(size, 0, 0), new Vector3d(0, size, 0), new Vector3d(0, 0, size), Objects.lazer);
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
		return super.getType() + ":Lazer";
	}

	@Override
	public Entity getDataStreamableInstance() 
	{
		return new Lazer();
	}

}
