package voxel3d.entity.all;

import voxel3d.entity.Entity;
import voxel3d.entity.BasicHostileEntity;
import voxel3d.entity.context.EntityRenderContext;
import voxel3d.entity.context.EntityUpdateContext;
import voxel3d.global.Objects;
import voxel3d.global.Time;
import voxel3d.model.BasicCharacterModel;
import voxel3d.physics.AABB;

public class Runner extends BasicHostileEntity{
	
	
	private final BasicCharacterModel model = new BasicCharacterModel();
	
	public Runner()
	{
		moveSpeed = 4;
	}
	
	static {
		Entity.setEntityDeserializerForType(new Runner());
	}
	
	
	@Override
	public void update(EntityUpdateContext context)
	{
		super.update(context);
		
		model.forward.set(faceDirction);;

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
		model.render(Objects.runner);
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
		return super.getType() + ":Runner";
	}

	@Override
	public Entity getDataStreamableInstance() 
	{
		return new Runner();
	}

}
