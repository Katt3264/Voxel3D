package voxel3d.entity.all;


import voxel3d.entity.Entity;
import voxel3d.entity.BasicHostileEntity;
import voxel3d.entity.context.EntityRenderContext;
import voxel3d.entity.context.EntityUpdateContext;
import voxel3d.global.Objects;
import voxel3d.global.Time;
import voxel3d.graphics.GeometryUtility;
import voxel3d.physics.AABB;
import voxel3d.utility.Vector3d;

public class Jumper extends Entity {
	
	
	private float waitTimer = 0;
	private static final float jumpDelay = 3;
	private static final float jumpHeight = 10;
	private static final float jumpSpeed = 3;
	private static final float friction = 10;
	
	static {
		Entity.setEntityDeserializerForType(new Jumper());
	}
	
	public Jumper()
	{
		
	}
	
	@Override
	public void update(EntityUpdateContext context)
	{
		super.update(context);
		
		if(grounded)
		{
			waitTimer -= Time.deltaTime;
			
			velocity.x -= velocity.x*Time.deltaTime*friction;
			velocity.y -= velocity.y*Time.deltaTime*friction;
			velocity.z -= velocity.z*Time.deltaTime*friction;
			
		}
		
		velocity.y += -gravity * Time.deltaTime;
	}
	
	@Override
	public void render(EntityRenderContext context)
	{
		super.render(context);
		double size = 2;
		Vector3d right = new Vector3d();
		Vector3d up = new Vector3d();
		Vector3d forward = new Vector3d();
		right.set(size, 0, 0);
		up.set(0, size, 0);
		forward.set(0, 0, size);
		GeometryUtility.drawBox(new Vector3d(0, 0, 0), right, up, forward, Objects.slime);
	}
	
	@Override
	public boolean getAABB(AABB writeback)
	{
		double size = 1;
		writeback.set(position.x - size, position.y - size, position.z - size, position.x + size, position.y + size, position.z + size);
		return true;
	}

	@Override
	public String getType()
	{
		return super.getType() + ":Jumper";
	}

	@Override
	public Entity getDataStreamableInstance() 
	{
		return new Jumper();
	}

}
