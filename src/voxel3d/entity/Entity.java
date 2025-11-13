package voxel3d.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;

import voxel3d.data.DataInputStream;
import voxel3d.data.DataOutputStream;
import voxel3d.data.DataStreamable;
import voxel3d.entity.context.EntityRenderContext;
import voxel3d.entity.context.EntityUpdateContext;
import voxel3d.global.Debug;
import voxel3d.global.Time;
import voxel3d.physics.*;
import voxel3d.utility.Vector3d;

public abstract class Entity implements DataStreamable {
	
	public boolean alive = true;
	
	public final Vector3d position = new Vector3d();
	public final Vector3d velocity = new Vector3d();
	public boolean grounded = true;
	public boolean contacted = false;
	protected static final double gravity = 18;
	
	private static final double repellFactor = 10;
	
	private static final TreeMap<String, Entity> typeInstanceMap = new TreeMap<String, Entity>();
	private static final Collection<Spawnable> spawnables = new ArrayList<Spawnable>();
	
	public static void setEntityDeserializerForType(Entity entity)
	{
		if(typeInstanceMap.containsKey(entity.getType()))
		{
			Debug.err("Multiple entities of same type: " + entity.getType());
			throw new RuntimeException();
		}
		
		typeInstanceMap.put(entity.getType(), entity);
		
		if(entity instanceof Spawnable)
		{
			spawnables.add((Spawnable) entity);
		}
	}
	
	
	public static Entity GetInstanceFromData(DataInputStream stream) throws IOException
	{
		String entityType = stream.readString();
		Entity instancer = typeInstanceMap.get(entityType);
		if(instancer == null)
		{
			System.err.println("Unknown entity type: " + entityType);
			throw new RuntimeException();
		}
		instancer = instancer.getDataStreamableInstance();
		instancer.read(stream);
		return instancer;
	}
	
	public static Iterable<Spawnable> getSpawnables()
	{
		return spawnables;
	}
	
	
	public abstract Entity getDataStreamableInstance();
	
	
	public void update(EntityUpdateContext context)
	{
		
	}
	
	public void render(EntityRenderContext context)
	{
		context.setEntityIlluminationLight(this);
	}
	
	public boolean getAABB(AABB writeback)
	{
		return false;
	}
	
	
	public void onOverlap(Entity other)
	{
		Vector3d dir = new Vector3d();
		dir.set(position);
		dir.subtract(other.position);
		dir.normalize();
		dir.multiply(repellFactor * Time.deltaTime);
		velocity.add(dir);
	}
	
	public void move(AABB[] aabbArray, int lenght)
	{
		Vector3d distance = new Vector3d();
		distance.set(velocity);
		distance.multiply(Time.deltaTime);
		
		double prevX = distance.x;
        double prevY = distance.y;
        double prevZ = distance.z;
        
        AABB selfAABB = new AABB();
        
        // Check for Y collision
        getAABB(selfAABB);
        for (int i = 0; i < lenght; i++) {
        	distance.y = aabbArray[i].clipYCollide(selfAABB, distance.y);
        }
        position.y += distance.y;

        // Check for X collision
        getAABB(selfAABB);
        for (int i = 0; i < lenght; i++) {
        	distance.x = aabbArray[i].clipXCollide(selfAABB, distance.x);
        }
        position.x += distance.x;

        // Check for Z collision
        getAABB(selfAABB);
        for (int i = 0; i < lenght; i++) {
        	distance.z = aabbArray[i].clipZCollide(selfAABB, distance.z);
        }
        position.z += distance.z;

        // Update on ground state
        this.grounded = (prevY != distance.y && prevY < 0);
        this.contacted = (prevY != distance.y) || (prevZ != distance.z) || (prevX != distance.x);

        // Stop motion on collision
        if (prevX != distance.x) this.velocity.x = 0.0;
        if (prevY != distance.y) this.velocity.y = 0.0;
        if (prevZ != distance.z) this.velocity.z = 0.0;
	}
	
	public String getType()
	{
		return "Entity";
	}

	@Override
	public void read(DataInputStream stream) throws IOException 
	{
		position.x = stream.readDouble();
		position.y = stream.readDouble();
		position.z = stream.readDouble();
		
		velocity.x = stream.readDouble();
		velocity.y = stream.readDouble();
		velocity.z = stream.readDouble();
	}

	@Override
	public void write(DataOutputStream stream) 
	{
		stream.writeDouble(position.x);
		stream.writeDouble(position.y);
		stream.writeDouble(position.z);
		
		stream.writeDouble(velocity.x);
		stream.writeDouble(velocity.y);
		stream.writeDouble(velocity.z);
	}

}
