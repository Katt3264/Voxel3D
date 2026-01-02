package voxel3d.level.world;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

import voxel3d.block.Block;
import voxel3d.block.all.AirBlock;
import voxel3d.block.context.BlockOnBreakContext;
import voxel3d.data.DataInputStream;
import voxel3d.data.DataOutputStream;
import voxel3d.data.DataStreamable;
import voxel3d.entity.Entity;
import voxel3d.entity.Spawnable;
import voxel3d.entity.all.ItemEntity;
import voxel3d.entity.all.Player;
import voxel3d.entity.context.EntityRenderContext;
import voxel3d.entity.context.EntityUpdateContext;
import voxel3d.global.Debug;
import voxel3d.global.Objects;
import voxel3d.global.Settings;
import voxel3d.global.Time;
import voxel3d.graphics.GraphicsWrapper;
import voxel3d.graphics.Mesh;
import voxel3d.item.Item;
import voxel3d.level.Ambiance;
import voxel3d.physics.AABB;
import voxel3d.physics.Ray;
import voxel3d.utility.Color;
import voxel3d.utility.MathX;
import voxel3d.utility.Vector3I;
import voxel3d.utility.Vector3d;

/**
* This class represents a 3d world of blocks
*/
public class World implements DataStreamable {
	
	public final String name;
	public final Player player;
	public long loadProgress = 0;
	private boolean paused = false;
	
	private int ox, oy, oz;
	private int renderDistance;
	
	//TODO: use better data structure for chunks
	private final ConcurrentNavigableMap<Vector3I, Chunk> chunks;
	private final ConcurrentLinkedQueue<Entity> entities;
	private final WorldScheduler worldScheduler;
	
	private final Color skyColor = new Color();
	private final Ambiance ambiance;
	private long time = 0;
	private int seed;
	
	//TODO: fix this
	private AABB[] aabbBuffer = new AABB[1024];

	public World(int renderDistance, int ox, int oy, int oz, String name)
	{
		this.name = name;
		this.ox = ox;
		this.oy = oy;
		this.oz = oz;
		
		seed = new Random().nextInt();
		
		this.renderDistance = renderDistance;
		this.chunks = new ConcurrentSkipListMap<Vector3I, Chunk>();
		
		entities = new ConcurrentLinkedQueue<Entity>();
		player = new Player();
		entities.add(player);
		ambiance = new Ambiance();
		
		for(int i = 0; i < aabbBuffer.length; i++)
		{
			aabbBuffer[i] = new AABB();
		}
		this.worldScheduler = new WorldScheduler(this);
	}
	
	public void start()
	{
		worldScheduler.start();
	}
	
	public void pause()
	{
		if(paused)
			return;
		
		paused = true;
		worldScheduler.pause();
	}
	
	public void resume()
	{
		if(!paused)
			return;
		
		worldScheduler.resume();
		paused = false;
	}
	
	public void stop()
	{
		worldScheduler.stop();
	}
	
	private boolean isPaused()
	{
		return paused;
	}
	
	public boolean isPlayerAlive()
	{
		return player.alive;
	}
	
	public boolean isLoading()
	{
		return worldScheduler.loadingInProgress;
	}
	
	public boolean isRunning()
	{
		return worldScheduler.isRunning();
	}
	
	public String getClockTime()
	{
		return ambiance.getClockTime();
	}
	
	public long getTime()
	{
		return time;
	}
	
	public void elapseTime(long nano)
	{
		time += nano;
	}
	
	public Iterable<Entity> getEntities()
	{
		synchronized(entities)
		{
			return entities;
		}
	}
	
	public void addEntities(Collection<Entity> en)
	{
		synchronized(entities)
		{
			entities.addAll(en);
		}
	}
	
	public void addEntity(Entity en)
	{
		synchronized(entities)
		{
			entities.add(en);
		}
	}
	
	public void removeEntity(Entity en)
	{
		synchronized(entities)
		{
			entities.remove(en);
		}
	}
	
	public boolean setBlock(int x, int y, int z, Block block)
	{
		synchronized(this)
		{
			int chunkX = MathX.chunkFloorDiv(x);
			int chunkY = MathX.chunkFloorDiv(y);
			int chunkZ = MathX.chunkFloorDiv(z);
			
			int xp = MathX.chunkMod(x);
			int yp = MathX.chunkMod(y);
			int zp = MathX.chunkMod(z);
	
			Chunk chunk = tryGetChunk(chunkX, chunkY, chunkZ);
			
			//TODO: change this event
			if(chunk == null) {return false;}
			
			chunk.setBlock(xp, yp, zp, block);
			
			return true;
		}
	}
	
	public void setCenter(int x, int y, int z, int newRenderDistance)
	{
		synchronized(this)
		{
			ox = x - newRenderDistance;
			oy = y - newRenderDistance;
			oz = z - newRenderDistance;
			
			renderDistance = newRenderDistance;
		}
	}
	
	public int getRenderDistance()
	{
		return renderDistance;
	}
	
	public void getOffset(Vector3I writeback)
	{
		synchronized(this)
		{
			writeback.set(ox, oy, oz);
		}
	}
	
	public Color getSkyColor()
	{
		return skyColor;
	}
	
	public Chunk tryGetChunk(int x, int y, int z)
	{
		synchronized(this)
		{
			Vector3I pos = new Vector3I(x,y,z);
			Chunk chunk = chunks.get(pos);
			return chunk;
		}
	}
	
	public Chunk forceGetChunk(int x, int y, int z)
	{
		synchronized(this)
		{
			Chunk chunk = tryGetChunk(x,y,z);
			if(chunk == null)
			{
				chunk = new Chunk(x,y,z);
				chunks.put(new Vector3I(x,y,z), chunk);
			}
			return chunk;
		}
	}
	
	public Iterable<Vector3I> getAllChunkPositions()
	{
		return chunks.navigableKeySet();
	}
	
	private Mesh getLocalChunkMesh(int x, int y, int z)
	{
		synchronized(this)
		{
			Chunk chunk = tryGetChunk(x, y, z);
			if(chunk == null) {return null;}
			return chunk.getMesh();
		}
	}
	
	public Block getBlock(int x, int y, int z)
	{
		synchronized(this)
		{
			int chunkX = MathX.chunkFloorDiv(x);
			int chunkY = MathX.chunkFloorDiv(y);
			int chunkZ = MathX.chunkFloorDiv(z);
			
			int xp = MathX.chunkMod(x);
			int yp = MathX.chunkMod(y);
			int zp = MathX.chunkMod(z);
	
			Chunk chunk = tryGetChunk(chunkX, chunkY, chunkZ);
			
			//TODO: change this event
			if(chunk == null) {return Block.GetNotYetLoaded();}
			
			return chunk.getBlock(xp, yp, zp);
		}
	}
	
	public void getColor(int x, int y, int z, Color writeback)
	{
		synchronized(this)
		{
			int chunkX = MathX.chunkFloorDiv(x);
			int chunkY = MathX.chunkFloorDiv(y);
			int chunkZ = MathX.chunkFloorDiv(z);
			
			int xp = MathX.chunkMod(x);
			int yp = MathX.chunkMod(y);
			int zp = MathX.chunkMod(z);
			
			Chunk chunk = tryGetChunk(chunkX, chunkY, chunkZ);
			
			if(chunk == null) 
			{
				writeback.set(0, 0, 0);
				return;
			}
			
			chunk.getColor(xp, yp, zp, skyColor, writeback);
		}
	}
	
	public boolean placeBlock(int x, int y, int z, Block block, Vector3d faceDirection)
	{
		if(block.hasCollisionbox())
		{
			AABB blockAABB = new AABB();
			block.getCollisionBox(x, y, z, blockAABB);
			AABB entityAABB = new AABB();
			for(Entity entity : getEntities())
			{
				entity.getAABB(entityAABB);
				if(blockAABB.intersects(entityAABB)) {return false;}
			}
		}

		setBlock(x, y, z, block);
			
		return true;
	}
	
	public void breakBlock(int x, int y, int z, Item tool)
	{
		Block breakBlock = getBlock(x, y, z);
		setBlock(x, y, z, AirBlock.getInstance());
		
		BlockOnBreakContext breakContext = new BlockOnBreakContext(x, y, z);
		breakBlock.onBreak(breakContext);
		
		if(breakContext.drops != null)
		{
			for(Item dropItem : breakContext.drops)
			{
				ItemEntity itemEntity = new ItemEntity();
				itemEntity.item = dropItem;
				itemEntity.position.set(x + 0.5, y + 0.5, z + 0.5);
				addEntity(itemEntity);
			}
		}
	}
	
	public int getOverlappingBox(AABB aabb, AABB[] writeback)
	{
		int minX = (int) (Math.floor(aabb.minX) - 1);
        int maxX = (int) (Math.ceil(aabb.maxX) + 1);
        int minY = (int) (Math.floor(aabb.minY) - 1);
        int maxY = (int) (Math.ceil(aabb.maxY) + 1);
        int minZ = (int) (Math.floor(aabb.minZ) - 1);
        int maxZ = (int) (Math.ceil(aabb.maxZ) + 1);
        
        int iterator = 0;
        
        for (int x = minX; x < maxX; x++) {
            for (int y = minY; y < maxY; y++) {
                for (int z = minZ; z < maxZ; z++) {
                    if (getBlock(x, y, z).hasCollisionbox()) {
                    	getBlock(x, y, z).getCollisionBox(x, y, z, writeback[iterator]);
                    	iterator++;
                    }
                }
            }
        }
        return iterator;
	}
	
	public Collection<Entity> entityRaycast(Ray ray)
	{
		Collection<Entity> results = new ArrayList<Entity>();
		
		for(Entity entity : getEntities())
		{
			AABB aabb = new AABB();
			entity.getAABB(aabb);
			if(aabb.intersects(ray))
			{
				results.add(entity);
			}
		}
		return results;
	}
	
	public boolean terrainRaycast(Ray ray, Vector3I point, Vector3I normal)
	{
		double dx = ray.start.x;
		double dy = ray.start.y;
		double dz = ray.start.z;
		
		AABB aabb = new AABB();
		
		double distanceCheck = 0;
		while(distanceCheck < ray.length)
		{
			dx = ray.start.x + ray.direction.x * distanceCheck;
			dy = ray.start.y + ray.direction.y * distanceCheck;
			dz = ray.start.z + ray.direction.z * distanceCheck;
			
			int ix = (int) Math.floor(dx);
			int iy = (int) Math.floor(dy);
			int iz = (int) Math.floor(dz);
			
			Block block = getBlock(ix, iy, iz);
			if(block != null)
			{
				if(block.hasSelectionBox())
				{
					block.getSelectionBox(ix, iy, iz, aabb);
				
					if(aabb.minX < dx && aabb.maxX > dx && aabb.minY < dy && aabb.maxY > dy && aabb.minZ < dz && aabb.maxZ > dz)
					{
						point.set(ix, iy, iz);
						aabb.getNormal(dx,dy,dz,normal);
						return true;
					}
				}
			}
			distanceCheck += 0.01;
		}
		return false;
	}
	
	public void spawnRoutine()
	{
		Random random = new Random();
		for(Spawnable spawnable : Entity.getSpawnables())
		{
			int spawnableCount = 0;
			for(Entity entity : getEntities())
			{
				if(entity.getType().equals(((Entity)spawnable).getType()))
					spawnableCount++;
			}
			
			if(spawnableCount >= Settings.spawnableLimit)
				continue;
			
			for(int i = 0; i < 64; i++)
			{
				double dx = ((random.nextDouble() * 2.0 - 1.0)*Settings.maxSpawnRadius);
				double dy = ((random.nextDouble() * 2.0 - 1.0)*Settings.maxSpawnRadius);
				double dz = ((random.nextDouble() * 2.0 - 1.0)*Settings.maxSpawnRadius);
				
				if(Math.sqrt(dx*dx + dy*dy + dz*dz) < Settings.minSpawnRadius)
					continue;
				
				int x = (int)(player.position.x + dx);
				int y = (int)(player.position.y + dy);
				int z = (int)(player.position.z + dz);
				
				if (spawnable.trySpawn(x, y, z, this))
					break;
			}
		}
	}
	
	public void update()
	{
		//TODO: improve
		
		worldScheduler.mainThreadFrameEntry();
		
		if(worldScheduler.loadingInProgress || isPaused())
			return;
		
		
		setCenter(
				MathX.chunkFloorDiv((int)player.position.x), 
				MathX.chunkFloorDiv((int)player.position.y), 
				MathX.chunkFloorDiv((int)player.position.z), 
				getRenderDistance());
		
		ambiance.elapse((long) (1_000_000_000L * Time.deltaTime));
		elapseTime((long) (1_000_000_000L * Time.deltaTime));
		
		
		spawnRoutine();
		
		
		AABB entityAABB = new AABB();
		EntityUpdateContext entityUpdateContext = new EntityUpdateContext(this);
		for(Entity entity : getEntities())
		{
			entity.update(entityUpdateContext);
			entity.getAABB(entityAABB);
			
			AABB entityOtherAABB = new AABB();
			for(Entity other : getEntities())
			{
				other.getAABB(entityOtherAABB);
				if(entityAABB.intersects(entityOtherAABB) && entity != other)
				{
					entity.onOverlap(other);
				}
			}
			if(entity.getAABB(entityAABB))
			{
				int lenght = getOverlappingBox(entityAABB, aabbBuffer);
				entity.move(aabbBuffer, lenght);
			}
		}
		
		AABB playerAABB = new AABB();
		player.getAABB(playerAABB);
		for(Entity entity : getEntities())
		{
			if(entity instanceof ItemEntity)
			{
				entity.getAABB(entityAABB);
				if(playerAABB.intersects(entityAABB) && player.inventory.addItem(((ItemEntity)entity).item))
				{
					removeEntity(entity);
				}
			}
			if(!entity.alive)
			{
				removeEntity(entity);
			}
		}
		
		//Color colorF = new Color();
		//Color colorA = new Color();
		Color skyColor = new Color();
		
		//Biome.getBiome((int)player.position.x, (int)player.position.y, (int)player.position.z).getBiomeColorFactor(colorF);
		//Biome.getBiome((int)player.position.x, (int)player.position.y, (int)player.position.z).getBiomeColorAcc(colorA);
		ambiance.getLight(skyColor);
	}
	
	public void render()
	{
		Debug.triangles = 0;

        GraphicsWrapper.setRenderModeSkybox(player.camera);
    	ambiance.render();
    	
    	ambiance.getLight(skyColor);
    	GraphicsWrapper.setRenderModeVoxelChunk(
    			player.camera, 
    			ox * Settings.CHUNK_SIZE, oy * Settings.CHUNK_SIZE, oz * Settings.CHUNK_SIZE, 
    			skyColor, Objects.chunkAtlas);
    	
		int size = 2 * renderDistance + 1;
		for(int x = 0; x < size; x++)
		{
			for(int y = 0; y < size; y++)
			{
				for(int z = 0; z < size; z++)
				{
					Mesh mesh = getLocalChunkMesh(x+ox, y+oy, z+oz);
					if(mesh == null) {continue;}
					mesh.keepAlive();
					
					if(mesh.triangles == 0) {continue;}
					
					if(!inFrustum(x * Settings.CHUNK_SIZE, y * Settings.CHUNK_SIZE, z * Settings.CHUNK_SIZE)) {continue;}
					Debug.triangles += mesh.triangles;
					GraphicsWrapper.RenderVoxelChunk(x * Settings.CHUNK_SIZE, y * Settings.CHUNK_SIZE, z * Settings.CHUNK_SIZE, mesh);
				}
			}
		}

        EntityRenderContext entityRenderContext = new EntityRenderContext(this);
        for (Entity entity : entities)
		{
        	GraphicsWrapper.SetRenderModeEntity(player.camera, entity);
        	entity.render(entityRenderContext);
		}
        
	}
	
	//TODO: not correct
	private boolean inFrustum(double cx, double cy, double cz)
	{
		// position of centre of chunk
		cx = cx + this.ox * Settings.CHUNK_SIZE + Settings.CHUNK_SIZE * 0.5;
		cy = cy + this.oy * Settings.CHUNK_SIZE + Settings.CHUNK_SIZE * 0.5;
		cz = cz + this.oz * Settings.CHUNK_SIZE + Settings.CHUNK_SIZE * 0.5;
		
		// most pesemistic point on bounding sphere
		double halfQbrt3 = Math.cbrt(3.0) / 2.0;
		cx += player.camera.forward.x * Settings.CHUNK_SIZE * halfQbrt3;
		cy += player.camera.forward.y * Settings.CHUNK_SIZE * halfQbrt3;
		cz += player.camera.forward.z * Settings.CHUNK_SIZE * halfQbrt3;
		
		double dx = cx - player.camera.position.x;
		double dy = cy - player.camera.position.y;
		double dz = cz - player.camera.position.z;
		
		if(player.camera.forward.x * dx + player.camera.forward.y * dy + player.camera.forward.z * dz > 0)
			return true;
		
		return false;
	}

	@Override
	public void read(DataInputStream stream) throws IOException 
	{
		ambiance.setTime(stream.readDouble());
		stream.readInt();
	}

	@Override
	public void write(DataOutputStream stream) 
	{
		stream.writeDouble(ambiance.time);
		stream.writeInt(seed);
	}
	
}
