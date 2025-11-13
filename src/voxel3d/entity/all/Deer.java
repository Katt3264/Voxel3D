package voxel3d.entity.all;

import voxel3d.entity.Entity;
import voxel3d.entity.BasicPassiveEntity;
import voxel3d.entity.context.EntityRenderContext;
import voxel3d.entity.context.EntityUpdateContext;
import voxel3d.global.Objects;
import voxel3d.global.Time;
import voxel3d.model.DeerModel;
import voxel3d.physics.AABB;
import voxel3d.utility.Vector3d;

public class Deer extends BasicPassiveEntity {
	
	private final DeerModel model = new DeerModel();
	

	static {
		Entity.setEntityDeserializerForType(new Deer());
	}
	
	public Deer()
	{
		moveSpeed = 2;
	}
	
	@Override
	public void update(EntityUpdateContext context)
	{
		super.update(context);
		
		if(xzVelocity.magnitude() > 0.1)
		{
			Vector3d xzvn = new Vector3d();
			xzvn.set(xzVelocity);
			xzvn.normalize();
			model.forward = xzvn;
		}
		
		if(idle)
			model.t = 0;

		model.position.set(0,0,0);
		model.t += xzVelocity.magnitude() * 0.5 * Time.deltaTime;
		model.a = 20;
		model.resolveTransform();
	}
	
	
	@Override
	public void render(EntityRenderContext context)
	{
		super.render(context);
		model.render(Objects.deer);
	}
	
	@Override
	public boolean getAABB(AABB writeback)
	{
		double size = 0.5;
		double height = 1.8;
		writeback.set(position.x - size, position.y, position.z - size, position.x + size, position.y + height, position.z + size);
		return true;
	}

	@Override
	public String getType()
	{
		return super.getType() + ":Deer";
	}

	@Override
	public Entity getDataStreamableInstance() 
	{
		return new Deer();
	}

}
