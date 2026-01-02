package voxel3d.entity;

import java.io.IOException;
import java.util.Random;

import voxel3d.data.DataInputStream;
import voxel3d.data.DataOutputStream;
import voxel3d.entity.context.EntityUpdateContext;
import voxel3d.global.Time;
import voxel3d.level.world.World;
import voxel3d.utility.Vector3d;

public abstract class BasicPassiveEntity extends Entity implements Spawnable, Strikeable{
	
	private int health = 100;
	
	private double strikeTime = 5;
	private double strikeTimer = 0;
	
	private double aliveTime = 60 * 5;
	
	public double acceleration = 3;
	public double jumpSpeed = 7;
	public double moveSpeed = 2;
	
	public Vector3d xzVelocity = new Vector3d();
	private Vector3d nextPosition = new Vector3d();
	private Vector3d target = new Vector3d();
	
	public boolean idle = false;
	
	private double moveTimer = 0;
	private double idleTime = 20;
	private double idleTimer = 0;
	private double walkTime = 10;
	private double walkTimer = 0;
	
	@Override
	public void update(EntityUpdateContext context)
	{
		super.update(context);
		
		moveTimer -= Time.deltaTime;
		idleTimer -= Time.deltaTime;
		walkTimer -= Time.deltaTime;
		strikeTimer -= Time.deltaTime;
		
		Vector3d dirToPlayer = new Vector3d();
		dirToPlayer.add(context.getPlayerPosition());
		dirToPlayer.subtract(position);
		double distanceToPlayer = dirToPlayer.magnitude();
		
		if(strikeTimer > 0)
			idleTimer = -1;
		
		if(idleTimer < 0 && idle)
		{
			idle = false;
			Random random = new Random();
			Vector3d randomTargetDir = new Vector3d(random.nextGaussian(), 0, random.nextGaussian());
			randomTargetDir.normalize();
			randomTargetDir.multiply(100);
			target.set(position.x + randomTargetDir.x, position.y - 100, position.z + randomTargetDir.z);
			walkTimer = walkTime;
		}
		
		if(walkTimer < 0 && !idle)
		{
			idle = true;
			idleTimer = idleTime;
		}
		
		setNextTarget(context);
		
		Vector3d dirToNextPosition  = new Vector3d();
		dirToNextPosition.add(nextPosition);
		dirToNextPosition.subtract(position);
		dirToNextPosition.y = 0;
		//double distanceToNextPosition = dirToNextPosition.magnitude();
		dirToNextPosition.normalize();
		
		xzVelocity.set(velocity);
		xzVelocity.y = 0;
		
		if(!idle) // move to a target
		{
			if(nextPosition.y - position.y > 0.1 && grounded)
			{
				velocity.y = jumpSpeed;
			}
			
			Vector3d targetVelocity = new Vector3d();
			targetVelocity.set(dirToNextPosition);
			targetVelocity.multiply(moveSpeed);
			if(strikeTimer > 0)
				targetVelocity.multiply(3);
			targetVelocity.subtract(velocity);
			targetVelocity.normalize();
			
			if(strikeTimer > 0)
				targetVelocity.multiply(3);
			
			velocity.x += targetVelocity.x * moveSpeed * acceleration * Time.deltaTime;
			velocity.z += targetVelocity.z * moveSpeed * acceleration * Time.deltaTime;
			
		}
		
		velocity.x -= velocity.x*Time.deltaTime*acceleration;
		velocity.z -= velocity.z*Time.deltaTime*acceleration;
		velocity.y += -gravity * Time.deltaTime;
		
		if(distanceToPlayer > 64)
		{
			aliveTime -= Time.deltaTime;
		}
		
		if(distanceToPlayer > 128 || aliveTime < 0 || health <= 0)
		{
			this.alive = false;
		}
	}
	
	private void setNextTarget(EntityUpdateContext context)
	{
		Vector3d dir = new Vector3d();
		dir.add(nextPosition);
		dir.subtract(position);
		dir.y = 0;
		double distance = dir.magnitude();
		dir.normalize();
		
		Vector3d dirToTarget = new Vector3d();
		dirToTarget.add(target);
		dirToTarget.subtract(position);
		double distanceToTarget = dirToTarget.magnitude();
		dirToTarget.y = 0;
		dirToTarget.normalize();
		
		if(distanceToTarget < 1)
		{
			nextPosition.set(context.getPlayerPosition());
		}
		else if(distance < 0.2 || distance > 1.5 || moveTimer < 0)
		{
			moveTimer = 1;
			int dx = (int) Math.round(dirToTarget.x);
			int dy = -100;
			int dz = (int) Math.round(dirToTarget.z);
			
			int tx = (int) Math.floor(position.x);
			int ty = (int) Math.floor(position.y);
			int tz = (int) Math.floor(position.z);
			
			if(context.getBlock(
					(int) Math.floor(tx+dx), 
					(int) Math.floor(ty), 
					(int) Math.floor(tz+dz)).hasCollisionbox())
			{
				dy = 1;
			}
			
			nextPosition.x = tx+dx + 0.5;
			nextPosition.y = ty+dy;
			nextPosition.z = tz+dz + 0.5;
		}
	}
	
	@Override
	public void strike(Vector3d direction, double damage)
	{
		Vector3d dirxz = new Vector3d();
		dirxz.set(direction);
		dirxz.y = 0;
		dirxz.multiply(10.0);
		
		velocity.add(dirxz);
		velocity.y += 5.0 * direction.magnitude();
		
		strikeTimer = strikeTime;
		walkTimer = 0;
		
		health -= damage;
	}
	
	@Override
	public boolean trySpawn(int x, int y, int z, World world) 
	{
		if(!world.getBlock(x, y-1, z).hasCollisionbox() || world.getBlock(x, y, z).hasCollisionbox() || world.getBlock(x, y+1, z).hasCollisionbox())
			return false;

		Entity spawn = getDataStreamableInstance();
		spawn.position.set(x + 0.5, y, z+0.5);
		world.addEntity(spawn);
		return true;
	}
	
	@Override
	public String getType()
	{
		return super.getType();
	}
	
	@Override
	public void read(DataInputStream stream) throws IOException 
	{
		super.read(stream);
		health = (int)stream.readKeyValueDouble("health");
	}

	@Override
	public void write(DataOutputStream stream) 
	{
		super.write(stream);
		stream.writeKeyValueDouble("health", health);
	}
}
