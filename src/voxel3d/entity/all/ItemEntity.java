package voxel3d.entity.all;

import static org.lwjgl.opengl.GL20.*;

import java.io.IOException;

import voxel3d.data.DataInputStream;
import voxel3d.data.DataOutputStream;
import voxel3d.entity.Entity;
import voxel3d.entity.context.EntityRenderContext;
import voxel3d.entity.context.EntityUpdateContext;
import voxel3d.global.Time;
import voxel3d.graphics.Texture;
import voxel3d.item.Item;
import voxel3d.item.context.ItemRenderContext;
import voxel3d.physics.AABB;
import voxel3d.utility.GeometryUtility;
import voxel3d.utility.Vector3d;

public class ItemEntity extends Entity{
	
	public Item item;
	
	private long offset;
	
	private int ttl;
	
	static {
		Entity.setEntityDeserializerForType(new ItemEntity());
	}
	
	
	public ItemEntity()
	{
		position.x += (Math.random() - 0.5) * 2.0 * 0.1;
		position.y += (Math.random() - 0.5) * 2.0 * 0.1;
		position.z += (Math.random() - 0.5) * 2.0 * 0.1;
		offset = (long) (System.currentTimeMillis() + Math.random() * 1000d);
		ttl = 1000 * 60 * 5;
	}
	
	@Override
	public void update(EntityUpdateContext context)
	{
		if(grounded)
		{
			velocity.x -= velocity.x*Time.deltaTime*1;
			velocity.y -= velocity.y*Time.deltaTime*1;
			velocity.z -= velocity.z*Time.deltaTime*1;
		}
		
		ttl -= Time.deltaTime * 1000f;
		
		if(ttl <= 0)
			alive = false;
		
		velocity.y += -gravity * Time.deltaTime;
		
		Vector3d dir = new Vector3d();
		dir.set(context.getPlayerPosition());
		dir.subtract(position);
		
		if(dir.magnitude() < 3)
		{
			dir.multiply(Time.deltaTime);
			dir.multiply(4);
			velocity.add(dir);
		}
		
	}
	
	@Override
	public void draw(EntityRenderContext context)
	{
		if(this.item == null) {
			//fail
		}
		
		super.draw(context);
		
		double x = 0;
		double y = 0;
		double z = 0;
		
		glTranslated(x, y, z);
		glRotated((double)((context.getTimeMilliseconds()-offset) % 3600) * 0.1f, 0, 1, 0);
		glTranslated(-x, -y, -z);
		glTranslated(0, Math.sin((double)((context.getTimeMilliseconds()-offset) % 2000) * Math.PI * 0.001f) * 0.1f + 0.35f, 0);
		
		double size = 0.5;
		Vector3d right = new Vector3d();
		Vector3d up = new Vector3d();
		Vector3d forward = new Vector3d();
		right.set(size, 0, 0);
		up.set(0, size, 0);
		forward.set(0, 0, size);
		
		ItemRenderContext itemRenderContext = new ItemRenderContext();
		item.render(itemRenderContext);
		Texture tex = itemRenderContext.texture;
		GeometryUtility.drawBox(new Vector3d(0, 0, 0), right, up, forward, tex);
		
	}
	
	@Override
	public boolean getAABB(AABB writeback)
	{
		double size = 0.25;
		writeback.set(position.x - size, position.y - size, position.z - size, position.x + size, position.y + size, position.z + size);
		return true;
	}
	
	@Override
	public String getType()
	{
		return super.getType() + ":ItemEntity";
	}
	
	@Override
	public void read(DataInputStream stream) throws IOException 
	{
		super.read(stream);
		item = stream.readItem();
		ttl = stream.readInt();
	}

	@Override
	public void write(DataOutputStream stream) 
	{
		super.write(stream);
		stream.writeItem(item);
		stream.writeInt(ttl);
	}

	@Override
	public Entity getDataStreamableInstance() 
	{
		return new ItemEntity();
	}
	
}
