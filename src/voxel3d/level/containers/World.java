package voxel3d.level.containers;

import static org.lwjgl.opengl.GL11.GL_ALPHA_TEST;
import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_GREATER;
import static org.lwjgl.opengl.GL11.GL_LEQUAL;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glAlphaFunc;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glDepthFunc;
import static org.lwjgl.opengl.GL11.glDepthRange;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL20.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map.Entry;
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
import voxel3d.generation.biome.Biome;
import voxel3d.global.Debug;
import voxel3d.global.Objects;
import voxel3d.global.Settings;
import voxel3d.global.Time;
import voxel3d.graphics.Mesh;
import voxel3d.item.Item;
import voxel3d.level.Ambiance;
import voxel3d.physics.AABB;
import voxel3d.physics.Ray;
import voxel3d.utility.Color;
import voxel3d.utility.MathX;
import voxel3d.utility.PersistentVector3D;
import voxel3d.utility.Vector3I;
import voxel3d.utility.Vector3d;

public class World implements DataStreamable {
	
	
	private int ox, oy, oz;
	private int renderDistance;
	
	
	private final ConcurrentNavigableMap<Vector3I, BlockContainer> allBlockContainers;
	private final PersistentVector3D<BlockContainer> blockContainers;
	private final PersistentVector3D<LightContainer> lightContainers;
	private final PersistentVector3D<MeshContainer> meshContainers;
	private final ConcurrentLinkedQueue<Entity> entities;
	
	public final String name;
	public final Player player;
	public long loadProgress = 0;
	
	private final Color skyColor = new Color();
	private final Ambiance ambiance;
	private long time = 0;
	

	private AABB[] aabbBuffer = new AABB[1024];

	public World(int renderDistance, int ox, int oy, int oz, String name)
	{
		this.name = name;
		this.ox = ox;
		this.oy = oy;
		this.oz = oz;
		
		this.renderDistance = renderDistance;
		this.blockContainers = new PersistentVector3D<BlockContainer>(2 * renderDistance + 1);
		this.lightContainers = new PersistentVector3D<LightContainer>(2 * renderDistance + 1);
		this.meshContainers = new PersistentVector3D<MeshContainer>(2 * renderDistance + 1);
		
		entities = new ConcurrentLinkedQueue<Entity>();
		player = new Player();
		entities.add(player);
		//dayNight = new DayNight();
		ambiance = new Ambiance();
		allBlockContainers = new ConcurrentSkipListMap<Vector3I, BlockContainer>();
		
		for(int i = 0; i < aabbBuffer.length; i++)
		{
			aabbBuffer[i] = new AABB();
		}
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
	
	public void setBufferedBlockContainer(int x, int y, int z, BlockContainer blockContainer)
	{
		synchronized(this)
		{
			Vector3I key = new Vector3I();
			key.set(x, y, z);
			allBlockContainers.put(key, blockContainer);
		}
	}
	
	public void setBlockContainer(int x, int y, int z, BlockContainer blockContainer)
	{
		synchronized(this)
		{	
			blockContainers.set(x - ox, y - oy, z - oz, blockContainer);
			Vector3I key = new Vector3I();
			key.set(x, y, z);
			allBlockContainers.put(key, blockContainer);
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
	
			BlockContainer blockContainer = getPersistentBlockContainer(chunkX, chunkY, chunkZ);
			
			if(blockContainer == null) {return false;}
			
			blockContainer.setBlock(xp, yp, zp, block);
			
			return true;
		}
	}
	
	public void setLightContainer(int x, int y, int z, LightContainer lightContainer)
	{
		synchronized(this)
		{
			lightContainers.set(x - ox, y - oy, z - oz, lightContainer);
		}
	}
	
	public void setMeshContainer(int x, int y, int z, MeshContainer meshContainer)
	{
		synchronized(this)
		{
			meshContainers.set(x - ox, y - oy, z - oz, meshContainer);
		}
	}
	
	public void setCenter(int x, int y, int z, int newRenderDistance)
	{
		synchronized(this)
		{
			int xs = ox - (x - newRenderDistance);
			int ys = oy - (y - newRenderDistance);
			int zs = oz - (z - newRenderDistance);
			
			ox = x - newRenderDistance;
			oy = y - newRenderDistance;
			oz = z - newRenderDistance;
			
			renderDistance = newRenderDistance;
			
			blockContainers.shift(xs, ys, zs, 2 * newRenderDistance + 1);
			lightContainers.shift(xs, ys, zs, 2 * newRenderDistance + 1); 
			 meshContainers.shift(xs, ys, zs, 2 * newRenderDistance + 1);
		}
	}
	
	public MeshContainer getMeshContainer(int x, int y, int z)
	{
		synchronized(this)
		{
			return meshContainers.get(x - ox, y - oy, z - oz);
		}
	}
	
	public BlockContainer getPersistentBlockContainer(int x, int y, int z)
	{
		synchronized(this)
		{
			return blockContainers.get(x - ox, y - oy, z - oz);
		}
	}
	
	public BlockContainer getBufferedBlockContainer(int x, int y, int z)
	{
		synchronized(this)
		{
			Vector3I key = new Vector3I();
			key.set(x, y, z);
			return allBlockContainers.get(key);
		}
	}
	
	public Iterable<Entry<Vector3I, BlockContainer>> getAllBlockContainers()
	{
		synchronized(this)
		{
			return allBlockContainers.entrySet();
		}
	}
	
	public LightContainer getPersistentLightContainer(int x, int y, int z)
	{
		synchronized(this)
		{
			return lightContainers.get(x - ox, y - oy, z - oz);
		}
	}
	
	public boolean getPersistentBlockContainers(int x, int y, int z, BlockContainer[][][] arr)
	{
		synchronized(this)
		{
			return blockContainers.getNeighbours(x - ox, y - oy, z - oz, arr);
		}
	}
	
	public boolean getBufferedBlockContainers(int x, int y, int z, BlockContainer[][][] arr)
	{
		synchronized(this)
		{
			for(int xx = -1; xx <= 1; xx++)
			{
				for(int yy = -1; yy <= 1; yy++)
				{
					for(int zz = -1; zz <= 1; zz++)
					{
						BlockContainer b = getBufferedBlockContainer(x + xx, y + yy, z + zz);
						arr[xx+1][yy+1][zz+1] = b;
						if(b == null)
							return false;
					}
				}
			}
			return true;
		}
	}
	
	public boolean getPersistentLightContainers(int x, int y, int z, LightContainer[][][] arr)
	{
		synchronized(this)
		{
			return lightContainers.getNeighbours(x - ox, y - oy, z - oz, arr);
		}
	}
	
	public int getRenderDistance()
	{
		synchronized(this)
		{
			return renderDistance;
		}
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
		synchronized(this)
		{
			return skyColor;
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
	
			BlockContainer blockContainer = getPersistentBlockContainer(chunkX, chunkY, chunkZ);
			
			if(blockContainer == null) {return Block.GetNotYetLoaded();}
			
			return blockContainer.getBlock(xp, yp, zp);
		}
	}
	
	public void getColor(int x, int y, int z, Color writeback)
	{
		synchronized(this)
		{
			int chunkX = MathX.chunkFloorDiv(x) - ox;
			int chunkY = MathX.chunkFloorDiv(y) - oy;
			int chunkZ = MathX.chunkFloorDiv(z) - oz;
			
			int xp = MathX.chunkMod(x);
			int yp = MathX.chunkMod(y);
			int zp = MathX.chunkMod(z);
			
			if((chunkX < 0 || chunkX >= 2 * renderDistance + 1)
			|| (chunkY < 0 || chunkY >= 2 * renderDistance + 1)
			|| (chunkZ < 0 || chunkZ >= 2 * renderDistance + 1)) 
			{
				writeback.set(0, 0, 0);
				return;
			}
			
			LightContainer lightContainer = lightContainers.get(chunkX, chunkY, chunkZ);
			
			if(lightContainer == null) 
			{
				writeback.set(0, 0, 0);
				return;
			}
			
			lightContainer.getColor(xp, yp, zp, skyColor, writeback);
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
		int spawnableCount = 0;
		for(Entity entity : getEntities())
		{
			if(entity instanceof Spawnable)
			{
				spawnableCount++;
			}
		}
		
		if(spawnableCount >= Settings.spawnableLimit)
			return;
		
		
		Random random = new Random();
		for(Spawnable spawnable : Entity.getSpawnables())
		{
			double spawnRadius = 64;
			int x = (int)(player.position.x + (random.nextDouble() * 2.0 - 1.0)*spawnRadius);
			int y = (int)(player.position.y + (random.nextDouble() * 2.0 - 1.0)*spawnRadius);
			int z = (int)(player.position.z + (random.nextDouble() * 2.0 - 1.0)*spawnRadius);
			spawnable.trySpawn(x, y, z, this);
		}
	}
	
	public void update()
	{
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
		
		Color colorF = new Color();
		Color colorA = new Color();
		Color skyColor = new Color();
		
		Biome.getBiome((int)player.position.x, (int)player.position.y, (int)player.position.z).getBiomeColorFactor(colorF);
		Biome.getBiome((int)player.position.x, (int)player.position.y, (int)player.position.z).getBiomeColorAcc(colorA);
		ambiance.getLight(skyColor);
	}
	
	public void render()
	{
		Mesh.cleanup();
		Debug.triangles = 0;
		float[] perspective = new float[4*4];
		float[] view = new float[4*4];
		
        // ignore depth buffer and binary alpha test
        glDisable(GL_DEPTH_TEST);
        glDisable(GL_ALPHA_TEST);
		player.camera.getMatrix(-player.camera.position.x, -player.camera.position.y, -player.camera.position.z, perspective, view);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glMatrixMode(GL_PROJECTION);
		glLoadMatrixf(perspective);
		glMatrixMode(GL_MODELVIEW);
		glLoadMatrixf(view);
		glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
    	ambiance.draw();
    	
    	// normal 3D
    	glEnable(GL_DEPTH_TEST);
    	glDepthRange(0, 1);
        glDepthFunc(GL_LEQUAL);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    	glEnable(GL_ALPHA_TEST);
    	glAlphaFunc(GL_GREATER, 0.5f);
    	glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);

    	player.camera.getMatrix(-ox * Settings.CHUNK_SIZE, -oy * Settings.CHUNK_SIZE, -oz * Settings.CHUNK_SIZE, perspective, view);
		Objects.shader.bind();
        glUniformMatrix4fv(glGetUniformLocation(Objects.shader.getProgramId(), "projectionMatrix"), false, perspective);
        glUniformMatrix4fv(glGetUniformLocation(Objects.shader.getProgramId(), "viewMatrix"), false, view);
        ambiance.getLight(skyColor);
        glUniform3f(glGetUniformLocation(Objects.shader.getProgramId(), "skyColor"), skyColor.r, skyColor.g, skyColor.b);
        glUniform1f(glGetUniformLocation(Objects.shader.getProgramId(), "brightness"), Settings.brightness);
		        
        float[] transform = new float[4*4];
        transform[0] = 1;
        transform[5] = 1;
        transform[10] = 1;
        transform[15] = 1;
		int size = 2 * renderDistance + 1;
		int transformMatrixLocation = glGetUniformLocation(Objects.shader.getProgramId(), "modelMatrix");
		Objects.chunkAtlas.glBind();
		for(int r = 0; r <= renderDistance; r++)
		{
			for(int x = 0; x < size; x++)
			{
				for(int y = 0; y < size; y++)
				{
					for(int z = 0; z < size; z++)
					{
						int halfSize = renderDistance;
						int trueR = Math.max(Math.abs(x - halfSize), Math.max(Math.abs(y - halfSize), Math.abs(z - halfSize)));
						if(r != trueR)
							continue;
						
						MeshContainer builder = meshContainers.get(x, y, z);
						if(builder == null) {continue;}
						
						Mesh mesh = builder.getMesh();
						if(mesh == null) {continue;}
						mesh.notifyUsed();
						
						if(mesh.triangles == 0) {continue;}
						
						transform[12] = (x) * Settings.CHUNK_SIZE;
						transform[13] = (y) * Settings.CHUNK_SIZE;
						transform[14] = (z) * Settings.CHUNK_SIZE;
						if(!inFrustum(transform[12], transform[13], transform[14])) {continue;}
						
						Debug.triangles += mesh.triangles;
						
						glUniformMatrix4fv(transformMatrixLocation, false, transform);
						mesh.draw();
					}
				}
			}
		}
		Objects.chunkAtlas.glUnbind();
		transform[12] = 0;
		transform[13] = 0;
		transform[14] = 0;
		Objects.shader.unbind();
		
		//TODO: add support for this in shader
		//player.camera.getMatrix(-player.position.x, -player.position.y, -player.position.z, perspective, view);
		//glUniformMatrix4fv(glGetUniformLocation(Objects.shader.getProgramId(), "projectionMatrix"), false, perspective);
        //glUniformMatrix4fv(glGetUniformLocation(Objects.shader.getProgramId(), "viewMatrix"), false, view);
        EntityRenderContext entityRenderContext = new EntityRenderContext(this);
        for (Entity entity : entities)
		{
        	player.camera.getMatrix(-entity.position.x, -entity.position.y, -entity.position.z, perspective, view);
    		glMatrixMode(GL_PROJECTION);
    		glLoadMatrixf(perspective);
        	glMatrixMode (GL_MODELVIEW);
        	glLoadMatrixf(view);
        	glColor4f(1,1,1,1);
        	//glUniformMatrix4fv(transformMatrixLocation, false, transform);
        	entity.draw(entityRenderContext);
		}
        //Objects.shader.unbind();
	}
	
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
	}

	@Override
	public void write(DataOutputStream stream) 
	{
		stream.writeDouble(ambiance.time);
	}
	
}
