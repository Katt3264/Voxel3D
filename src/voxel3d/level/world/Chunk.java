package voxel3d.level.world;

import voxel3d.block.Block;
import voxel3d.global.Settings;
import voxel3d.global.Time;
import voxel3d.graphics.Mesh;
import voxel3d.utility.Color;
import voxel3d.utility.Color5;
import voxel3d.utility.MathX;

public class Chunk {
	
	//TODO: make one single state: empty,working,done
	public boolean isPopulated = false;
	public boolean isBeingPopulated = false;
	public long lastModified = -1;
	public boolean buildingMesh = false;
	public long consistentMesh = -1;
	public boolean buildingLight = false;
	public long consistentLight = -1;
	
	private final int cx, cy, cz;
	private final BlockOctree blocks;
	//private Collection<Entry<Vector3I, BlockSimulable>> simulableBlocks = Collections.<Entry<Vector3I, BlockSimulable>>emptyList();
	private final byte[] red;
	private final byte[] green;
	private final byte[] blue;
	private final byte[] sky;
	private Mesh mesh = null;
	private final Chunk[][][] neibours = new Chunk[3][3][3];
	private boolean hasNeibours = false;
	
	public Chunk(int cx, int cy, int cz)
	{
		this.cx = cx;
		this.cy = cy;
		this.cz = cz;
		blocks = new BlockOctree((byte) Settings.CHUNK_SIZE_LOG, Block.GetNotYetLoaded());
		
		red = new byte[Settings.CHUNK_SIZE3];
		green = new byte[Settings.CHUNK_SIZE3];
		blue = new byte[Settings.CHUNK_SIZE3];
		sky = new byte[Settings.CHUNK_SIZE3];
		
		for(int i = 0; i < Settings.CHUNK_SIZE3; i++) 
		{
			red[i] = 0;
			green[i] = 0;
			blue[i] = 0;
			sky[i] = (byte) (Settings.lightDistance);
		}
	}
	
	public Chunk[][][] tryGetNeibours(World world)
	{
		if(hasNeibours)
			return neibours;
		
		for(int x = -1; x <= 1; x++)
		{
			for(int y = -1; y <= 1; y++)
			{
				for(int z = -1; z <= 1; z++)
				{
					Chunk chunk = world.tryGetChunk(cx + x, cy + y, cz + z);
					if(chunk == null) {return null;}
					neibours[x+1][y+1][z+1] = chunk;
				}
			}
		}
		
		hasNeibours = true;
		return neibours;
	}
	
	//TODO: depricate this
	public void setAllBlocks(Block[] blocks)
	{
		for(int x = 0; x < Settings.CHUNK_SIZE; x++)
		{
			for(int y = 0; y < Settings.CHUNK_SIZE; y++)
			{
				for(int z = 0; z < Settings.CHUNK_SIZE; z++)
				{
					int index = MathX.getXYZ(x, y, z);
					this.blocks.set(x, y, z, blocks[index]);
				}
			}
		}
		lastModified = Time.getAtomTime();
	}
	
	public Block getBlock(int bx, int by, int bz)
	{
		if(!isPopulated)
			return Block.GetNotYetLoaded();
		
		return blocks.get(bx, by, bz);
	}
	
	public byte[] getRed() 
	{        
		return red;
	}
	
	public byte[] getGreen() 
	{
		return green;
	}
	
	public byte[] getBlue() 
	{
		return blue;
	}
	
	public byte[] getSky() 
	{
    	return sky;
	}
	
	public void getColor(int x, int y, int z, Color skyColor, Color writeback)
	{
		int intRed   = red  [MathX.getXYZ(x, y, z)];
		int intGreen = green[MathX.getXYZ(x, y, z)];
		int intBlue  = blue [MathX.getXYZ(x, y, z)];
		int intSky   = sky  [MathX.getXYZ(x, y, z)];
		
		float lightRed 	 = (float)intRed   / (float)Settings.lightDistance;
		float lightGreen = (float)intGreen / (float)Settings.lightDistance;
		float lightBlue  = (float)intBlue  / (float)Settings.lightDistance;
		
		float skyRed   = (skyColor.r * (float)intSky) / (float)Settings.lightDistance;
		float skyGreen = (skyColor.g * (float)intSky) / (float)Settings.lightDistance;
		float skyBlue  = (skyColor.b * (float)intSky) / (float)Settings.lightDistance;
		
		float finalRed   = Math.max(lightRed,   skyRed);
		float finalGreen = Math.max(lightGreen, skyGreen);
		float finalBlue  = Math.max(lightBlue,  skyBlue);
		
		float compositRed   = Settings.brightness + finalRed   * (1f - Settings.brightness);
		float compositGreen = Settings.brightness + finalGreen * (1f - Settings.brightness);
		float compositBlue  = Settings.brightness + finalBlue  * (1f - Settings.brightness);
		
		writeback.set(compositRed, compositGreen, compositBlue);
	}
	
	public void getColor5(int x, int y, int z, Color5 writeback)
	{
		int intRed   = red  [MathX.getXYZ(x, y, z)];
		int intGreen = green[MathX.getXYZ(x, y, z)];
		int intBlue  = blue [MathX.getXYZ(x, y, z)];
		int intSky   = sky  [MathX.getXYZ(x, y, z)];
		
		float lightRed 	 = (float)intRed   / (float)Settings.lightDistance;
		float lightGreen = (float)intGreen / (float)Settings.lightDistance;
		float lightBlue  = (float)intBlue  / (float)Settings.lightDistance;
		float lightSky   = (float)intSky   / (float)Settings.lightDistance;
		
		writeback.set(lightRed, lightGreen, lightBlue, lightSky);
	}
	
	public void setMesh(Mesh mesh)
	{
		this.mesh = mesh;
	}
	
	public Mesh getMesh()
	{
		//check if mesh has been dealocated
		if(mesh != null)
			if(!mesh.isAlive())
				consistentMesh = -1;
				
		return mesh;
	}
	
	public void setBlock(int bx, int by, int bz, Block block)
	{
		if(!isPopulated)
			return;
		
		lastModified = Time.getAtomTime();
		blocks.set(bx, by, bz, block);
	}
	
	public void setRebuildMeshFlag()
	{
		lastModified = Time.getAtomTime();
	}
	
	/*
	 * Save to disk
	 */
	
	/*
	 * Load from disk
	 */
	
}
