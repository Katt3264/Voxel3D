package voxel3d.entity;

import java.util.Random;

import voxel3d.entity.context.EntityUpdateContext;
import voxel3d.global.Time;
import voxel3d.level.containers.World;
import voxel3d.utility.Vector3d;

public abstract class MovingEntity extends Entity implements Strikeable, Spawnable {
	
	private double health = 100;
	
	private double randomTargetTimer = 0;
	private double waitTimer = 0;
	private double stunTimer = 0;
	
	private Vector3d randomTarget = new Vector3d();
	
	public double randomTargetWait = 16;
	public double stunTime = 2;
	
	private static final double randomTargetSize = 16;
	
	
	private double aliveTime = 60 * 5;
	private Random random = new Random();
	
	
	public double acceleration = 3;
	public double jumpDelay = 1;
	public double jumpHeight = 8;
	public double moveSpeed = 2;
	public double runSpeed = 2;
	
	
	public double distanceToPlayer;
	public Vector3d target = new Vector3d();
	public Vector3d xzVelocity = new Vector3d();
	public Vector3d xzToTargetNorm = new Vector3d();
	
	@Override
	public void update(EntityUpdateContext context)
	{
		Vector3d playerTarget = new Vector3d();
		Vector3d dir = new Vector3d();
		
		playerTarget.set(context.getPlayerPosition());
		
		dir.set(context.getPlayerPosition());
		dir.subtract(position);
		distanceToPlayer = dir.magnitude();
		
		xzToTargetNorm.set(target);
		xzToTargetNorm.subtract(position);
		xzToTargetNorm.y = 0;
		xzToTargetNorm.normalize();
		
		xzVelocity.set(velocity);
		xzVelocity.y = 0;

		stunTimer -= Time.deltaTime;
		randomTargetTimer -= Time.deltaTime;
		if(randomTargetTimer < 0)
		{
			randomTargetTimer = randomTargetWait;
			double x = random.nextGaussian();
			double y = random.nextGaussian();
			double z = random.nextGaussian();
			randomTarget.set(x, y, z);
			randomTarget.y = 0;
			randomTarget.normalize();
			randomTarget.multiply(randomTargetSize);
			randomTarget.add(position);
		}
		

		dir.set(xzToTargetNorm);
		dir.multiply(acceleration * moveSpeed * Time.deltaTime);
		target.set(randomTarget);
		
		
		if(stunTimer < 0)
		{
			velocity.add(dir);
		}
		
		//jump over obstruction
		if(grounded)
		{
			if(velocity.magnitude() < 0.5 && distanceToPlayer > 2)
			{
				waitTimer -= Time.deltaTime;
				if(waitTimer < 0)
				{
					waitTimer = jumpDelay;
					velocity.y = jumpHeight;
				}
			}
			else
			{
				waitTimer = jumpDelay;
			}
		}
		
		//if(grounded)
		{
			velocity.x -= velocity.x*Time.deltaTime*acceleration;
			//velocity.y -= velocity.y*Time.deltaTime*acceleration;
			velocity.z -= velocity.z*Time.deltaTime*acceleration;
		}
		velocity.y += -gravity * Time.deltaTime;
		
		if(distanceToPlayer > 64)
		{
			//target = position.subtract(act.world.player.position).add(position);
			//aliveTime -= Time.deltaTime;
		}
		
		if(distanceToPlayer > 128 || aliveTime < 0 || health <= 0)
		{
			this.alive = false;
		}
	}
	
	@Override
	public void strike(Vector3d direction, double damage)
	{
		Vector3d dirxz = new Vector3d();
		dirxz.set(direction);
		dirxz.y = 0;
		dirxz.normalize();
		dirxz.multiply(10.0);
		
		velocity.add(dirxz);
		velocity.y += 5.0;
		
		stunTimer = stunTime;
		
		health -= damage;
	}
	
	@Override
	public void trySpawn(int x, int y, int z, World world) 
	{
		if(world.getBlock(x, y-1, z).hasCollisionbox() && !world.getBlock(x, y, z).hasCollisionbox() && !world.getBlock(x, y+1, z).hasCollisionbox())
		{
			Entity spawn = getDataStreamableInstance();
			spawn.position.set(x + 0.5, y, z+0.5);
			world.addEntity(spawn);
		}
	}
	
	@Override
	public String getType()
	{
		return super.getType() + ":MovingEntity";
	}

}
