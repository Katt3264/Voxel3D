package voxel3d.entity.all;

import voxel3d.entity.Entity;
import voxel3d.entity.BasicHostileEntity;
import voxel3d.entity.context.EntityRenderContext;
import voxel3d.entity.context.EntityUpdateContext;
import voxel3d.global.Objects;
import voxel3d.global.Time;
import voxel3d.model.BasicCharacterModel;
import voxel3d.physics.AABB;
import voxel3d.utility.Vector3d;

public class Undead extends BasicHostileEntity {
	
	private final BasicCharacterModel model = new BasicCharacterModel();
	

	static {
		Entity.setEntityDeserializerForType(new Undead());
	}
	
	public Undead()
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
	public void draw(EntityRenderContext context)
	{
		super.draw(context);
		model.render(Objects.undead);
	}
	
	@Override
	public boolean getAABB(AABB writeback)
	{
		double size = 0.25;
		double height = 1.9;
		writeback.set(position.x - size, position.y, position.z - size, position.x + size, position.y + height, position.z + size);
		return true;
	}

	@Override
	public String getType()
	{
		return super.getType() + ":Undead";
	}

	@Override
	public Entity getDataStreamableInstance() 
	{
		return new Undead();
	}

}
