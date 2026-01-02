package voxel3d.entity.all;

import java.io.IOException;

import voxel3d.block.Block;
import voxel3d.block.all.AirBlock;
import voxel3d.block.context.BlockOnUseContext;
import voxel3d.data.*;
import voxel3d.entity.*;
import voxel3d.entity.context.EntityRenderContext;
import voxel3d.entity.context.EntityUpdateContext;
import voxel3d.global.*;
import voxel3d.graphics.GeometryUtility;
import voxel3d.item.Item;
import voxel3d.item.context.ItemUseContext;
import voxel3d.physics.*;
import voxel3d.utility.*;

public class Player extends Entity implements Strikeable {
	
	private static final double acceleration = 5;

	private static final double jumpSpeed = 6.2;
	private static final double moveSpeed = 4.3;
	
	public boolean inventoryOpen = false;
	
	private double yaw = 0;
	private double pitch = 0;
	
	Vector3d respawnPoint = new Vector3d(0, 100, 0);
	public boolean alive = true;
	public int health = 100;
	public int hunger = 100;
	
	private final float maxHungerTimer = 1f;
	private float hungerTimer = maxHungerTimer;
	
	private Vector3I blockBreakTarget = new Vector3I();
	private float blockBreakProgress = 0;
	
	public Camera camera;
	public Inventory inventory;
	
	public Block facing = AirBlock.getInstance();
	
	public Player()
	{
		camera = new Camera(90);
		position.set(respawnPoint);
		inventory = new Inventory();
	}
	
	@Override
	public boolean getAABB(AABB writeback)
	{
		double sizeXZ = 0.25;
		double sizeY = 1.9;
		writeback.set(position.x - sizeXZ, position.y, position.z - sizeXZ, position.x + sizeXZ, position.y + sizeY, position.z + sizeXZ);
		return true;
	}
	
	@Override
	public void update(EntityUpdateContext context)
	{
		if(!alive)
			return;
		
		camera.fov = Settings.fov;
		Objects.audioManager.setListener(this);
		
		velocity.x -= velocity.x*Time.deltaTime*acceleration;
		velocity.z -= velocity.z*Time.deltaTime*acceleration;
		velocity.y += -gravity * Time.deltaTime;
		
		if ((Input.inventory.isButtonPress() || Input.esc.isButtonPress()) && inventoryOpen) 
        {
			Input.hideMouse();
    		inventoryOpen = false;
        }
		else if (Input.inventory.isButtonPress() && !inventoryOpen) 
        {
			Input.showMouse();
    		inventoryOpen = true;
        }

		
		if(!inventoryOpen)
		{
			double moveF = 1;
			
			if(Input.sprint.isButtonDown())
			{
				moveF = 1.5;
			}
			
			if(Input.drop.isButtonPress())
			{
				if(inventory.items[inventory.selectedHotbar].value > 0)
				{
					inventory.items[inventory.selectedHotbar].value--;
					Item item = inventory.items[inventory.selectedHotbar].item;
					
					ItemEntity ie = new ItemEntity();
					ie.item = item;
					ie.position.set(
							camera.position.x + camera.forward.x,
							camera.position.y + camera.forward.y,
							camera.position.z + camera.forward.z);
					ie.velocity.set(camera.forward);
					ie.velocity.multiply(4);
					context.createItemEntity(ie);;
				}
			}
		
			if(Input.forward.isButtonDown())
			{
				velocity.x += Math.sin(yaw) * moveSpeed * moveF * acceleration * Time.deltaTime;
				velocity.z += Math.cos(yaw) * moveSpeed * moveF * acceleration * Time.deltaTime;
			}
			if(Input.back.isButtonDown())
			{
				velocity.x += -Math.sin(yaw) * moveSpeed * moveF * acceleration * Time.deltaTime;
				velocity.z += -Math.cos(yaw) * moveSpeed * moveF * acceleration * Time.deltaTime;
			}
		
			if(Input.right.isButtonDown())
			{
				velocity.x += Math.sin(yaw + Math.PI * 0.5d) * moveSpeed * moveF * acceleration * Time.deltaTime;
				velocity.z += Math.cos(yaw + Math.PI * 0.5d) * moveSpeed * moveF * acceleration * Time.deltaTime;
			}
			if(Input.left.isButtonDown())
			{
				velocity.x += -Math.sin(yaw + Math.PI * 0.5d) * moveSpeed * moveF * acceleration * Time.deltaTime;
				velocity.z += -Math.cos(yaw + Math.PI * 0.5d) * moveSpeed * moveF * acceleration * Time.deltaTime;
			}
		
			if(Input.jump.isButtonDown() && grounded)
			{
				velocity.y = jumpSpeed;
			}
			if(Input.jump.isButtonDown() && Settings.godMode)
			{
				velocity.y += gravity*2*Time.deltaTime;
			}
			
			{
				Vector3I hitPoint = new Vector3I();
				Vector3I hitNormal = new Vector3I();
				boolean hit = context.terrainRaycast(new Ray(camera.position, camera.forward, 5), hitPoint, hitNormal);
				if(hit)
				{
					facing = context.getBlock(hitPoint.x, hitPoint.y, hitPoint.z);
				}
				else
				{
					facing = AirBlock.getInstance();
				}
			}
			
			if(Input.hit.isButtonPress())
			{
				Iterable<Entity> hits = context.entityRaycast(new Ray(camera.position, camera.forward, 3.0));
				for(Entity entity : hits)
				{
					if(entity instanceof Strikeable && entity != this)
					{
						((Strikeable) entity).strike(camera.forward, 10.0);
					}
				}
			}
			
			if(Input.hit.isButtonDown())
			{
				Vector3I hitPoint = new Vector3I();
				Vector3I hitNormal = new Vector3I();
				boolean hit = context.terrainRaycast(new Ray(camera.position, camera.forward, 5), hitPoint, hitNormal);
				
				if(hit)
				{
					Block block = context.getBlock(hitPoint.x, hitPoint.y, hitPoint.z);
					
					if(hitPoint.x != blockBreakTarget.x || hitPoint.y != blockBreakTarget.y || hitPoint.z != blockBreakTarget.z)
					{
						blockBreakProgress = 0;
						blockBreakTarget.set(hitPoint.x, hitPoint.y, hitPoint.z);
					}
					
					blockBreakProgress += (1d / block.getBreakTime(inventory.items[inventory.selectedHotbar].item)) * Time.deltaTime;
					
					if(blockBreakProgress >= 1)
					{
						blockBreakProgress = 0;
						context.breakBlock(hitPoint.x, hitPoint.y, hitPoint.z, inventory.items[inventory.selectedHotbar].item);
					}
				}
				else
				{
					blockBreakProgress = 0;
				}
			}
			else
			{
				blockBreakProgress = 0;
			}
		
			
			if(Input.place.isButtonPress())
			{
				boolean preventPlacementOfBlock = false;
				
				// block interact
				Vector3I hitPoint = new Vector3I();
				Vector3I hitNormal = new Vector3I();
				boolean hit = context.terrainRaycast(new Ray(camera.position, camera.forward, 5), hitPoint, hitNormal);
				if(hit)
				{
					Block block = context.getBlock(
							hitPoint.x, 
							hitPoint.y, 
							hitPoint.z);
					
					BlockOnUseContext blockContext = new BlockOnUseContext();
					block.onUse(blockContext);
					
					// force hard update
					if(blockContext.dataModified)
					{
						context.placeBlock(
								hitPoint.x, 
								hitPoint.y, 
								hitPoint.z, 
								block, camera.forward);
					}
					
					/*if(block.getHUD() != null)
					{
						Input.showMouse();
		        		inventoryOpen = true;
					}*/
					
					if(blockContext.interactionPreventsPlaceBlock/* || block.getHUD() != null*/)
					{
						preventPlacementOfBlock = true;
					}
				}
				
				
				if(inventory.items[inventory.selectedHotbar].item != null && !preventPlacementOfBlock)
				{
					ItemUseContext itemUseContext = new ItemUseContext(context);
					inventory.items[inventory.selectedHotbar].item.onUse(itemUseContext);
					Block placeBlock = itemUseContext.placeBlock;
					
					// place block
					if(placeBlock != null)
					{
						hit = context.terrainRaycast(new Ray(camera.position, camera.forward, 5), hitPoint, hitNormal);
						if(hit && inventory.items[inventory.selectedHotbar].value > 0)
						{
							boolean b = context.placeBlock(
									hitPoint.x + hitNormal.x, 
									hitPoint.y + hitNormal.y, 
									hitPoint.z + hitNormal.z, 
									placeBlock, camera.forward);
							
							if(b)
							{
								inventory.items[inventory.selectedHotbar].value--;
							}
						}
					}
					
					// consume item
					int hungerPoints = itemUseContext.hungerPoint;
					if(hungerPoints != 0 && hunger < 100)
					{
						hunger += hungerPoints;
						inventory.items[inventory.selectedHotbar].value--;
					}
				}
			}
		
			yaw += (Input.getMouseX() / 300f) * Settings.mouseSensitivity;
			pitch += (Input.getMouseY() / 300f) * Settings.mouseSensitivity;
			pitch = Math.max(Math.min(pitch,  Math.PI * 0.499d),  -Math.PI * 0.499d);
		
		}
		
		
		camera.forward.x = (float) (Math.sin(yaw) * Math.cos(pitch));
		camera.forward.z = (float) (Math.cos(yaw) * Math.cos(pitch));
		camera.forward.y = (float) (-Math.sin(pitch));
		
		camera.up.x = (float) (Math.sin(yaw) * Math.sin(pitch));
		camera.up.z = (float) (Math.cos(yaw) * Math.sin(pitch));
		camera.up.y = (float) (Math.cos(pitch));
		
		camera.position.set(position);
		camera.position.add(0, 1.75d, 0);
		
		hunger = Math.max(hunger, 0);
		health = Math.max(health, 0);
		
		hungerTimer -= Time.deltaTime;
		if(hungerTimer <= 0)
		{
			hungerTimer = maxHungerTimer;
			
			// consume hunger TODO: balance
			/*if(hunger > 0)
			{
				hunger--;
			}*/
			
			// consume health
			if(hunger <= 0 && health > 10)
			{
				health--;
			}
			
			// use hunger to regenerate health
			if(hunger > 50 && health < 100)
			{
				hunger--;
				health++;
			}
			else if(hunger > 100 && health < 200)
			{
				hunger--;
				health++;
			}
		}
		
		if(health <= 0)
			die();
	}
	
	private void die()
	{
		//TODO: play sound
		
		inventoryOpen = false;
		alive = false;
	}
	
	public void respawn()
	{
		//TODO: play sound
		
		health = 100;
		hunger = 100;
		position.set(respawnPoint);
		yaw = 0;
		pitch = 0;
		alive = true;
	}
	
	@Override
	public void strike(Vector3d direction, double damage)
	{
		//TODO: play sound
		
		Vector3d dirxz = new Vector3d();
		dirxz.set(direction);
		dirxz.y = 0;
		//dirxz.normalize();
		dirxz.multiply(10.0);
		
		velocity.add(dirxz);
		velocity.y += 5.0 * direction.magnitude();
		
		health -= damage;
	}
	
	@Override
	public void render(EntityRenderContext context)
	{
		//super.draw(world);
		if(blockBreakProgress == 0)
			return;
		
		
		double size = 1.01;
		
		Vector3d pos = new Vector3d();
		Vector3d right = new Vector3d();
		Vector3d up = new Vector3d();
		Vector3d forward = new Vector3d();
		pos.set(blockBreakTarget.x + 0.5, blockBreakTarget.y + 0.5, blockBreakTarget.z + 0.5);
		pos.subtract(position);
		right.set(size, 0, 0);
		up.set(0, size, 0);
		forward.set(0, 0, size);
		
		int prog = (int)((float)blockBreakProgress * (float)Objects.blockBreak.length);
		
		GeometryUtility.drawBox(pos, right, up, forward, Objects.blockBreak[prog]);
	}
	
	@Override
	public void read(DataInputStream stream) throws IOException 
	{
		super.read(stream);
		pitch = stream.readKeyValueDouble("pitch");
		yaw = stream.readKeyValueDouble("yaw");
		hunger = (int) stream.readKeyValueDouble("hunger");
		health = (int) stream.readKeyValueDouble("health");
		inventory.read(stream);
	}

	@Override
	public void write(DataOutputStream stream) 
	{
		super.write(stream);
		stream.writeKeyValueDouble("pitch", pitch);
		stream.writeKeyValueDouble("yaw", yaw);
		stream.writeKeyValueDouble("hunger", hunger);
		stream.writeKeyValueDouble("health", health);
		inventory.write(stream);
	}

	@Override
	public String getType() 
	{
		return "Player";
	}

	@Override
	public Entity getDataStreamableInstance() 
	{
		throw new RuntimeException("This should never be called!");
	}

}
