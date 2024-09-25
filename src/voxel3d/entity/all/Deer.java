package voxel3d.entity.all;

import voxel3d.entity.Entity;
import voxel3d.entity.MovingEntity;
import voxel3d.entity.context.EntityRenderContext;
import voxel3d.entity.context.EntityUpdateContext;
import voxel3d.global.Objects;
import voxel3d.global.Time;
import voxel3d.model.DeerModel;
import voxel3d.physics.AABB;

public class Deer extends MovingEntity{
	
	private final DeerModel model = new DeerModel();
	

	static {
		Entity.setEntityDeserializerForType(new Deer());
	}
	
	
	
	@Override
	public void update(EntityUpdateContext context)
	{
		super.update(context);

		model.position.set(0,0,0);;
		if(xzToTargetNorm.magnitude() > 0.1)
			model.forward = xzToTargetNorm;
		model.t += xzVelocity.magnitude() * 0.25 * Time.deltaTime;
		model.a = 20;
		model.resolveTransform();
	}
	
	
	@Override
	public void draw(EntityRenderContext context)
	{
		super.draw(context);
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
