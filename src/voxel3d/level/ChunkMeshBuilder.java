package voxel3d.level;


import voxel3d.block.Block;
import voxel3d.global.Debug;
import voxel3d.global.Objects;
import voxel3d.global.Settings;
import voxel3d.graphics.Mesh;
import voxel3d.level.containers.MeshContainer;
import voxel3d.level.containers.BlockContainer;
import voxel3d.level.containers.LightContainer;
import voxel3d.utility.*;

public class ChunkMeshBuilder implements MainThreadExecutable, Executable {

	private static final float epsilon0 = -0.0001f;
	private static final float epsilon1 = 1.0001f;
	private static final float uv0 = 0.001f;
	private static final float uv1 = 0.999f;
	
	public static final float[] faceXP = new float[]
			{
				epsilon1, epsilon1, epsilon0,
				epsilon1, epsilon0, epsilon0,
				epsilon1, epsilon0, epsilon1,
				epsilon1, epsilon1, epsilon0,
				epsilon1, epsilon0, epsilon1,
				epsilon1, epsilon1, epsilon1
			};
	
	public static final float[] faceXM = new float[]
			{
				epsilon0, epsilon1, epsilon1,
				epsilon0, epsilon0, epsilon1,
				epsilon0, epsilon0, epsilon0,
				epsilon0, epsilon1, epsilon1,
				epsilon0, epsilon0, epsilon0,
				epsilon0, epsilon1, epsilon0
			};
	
	public static final float[] faceYP = new float[]
			{
				epsilon1, epsilon1, epsilon0,
				epsilon1, epsilon1, epsilon1,
				epsilon0, epsilon1, epsilon1,
				epsilon1, epsilon1, epsilon0,
				epsilon0, epsilon1, epsilon1,
				epsilon0, epsilon1, epsilon0
			};
	
	public static final float[] faceYM = new float[]
			{
				epsilon1, epsilon0, epsilon1,
				epsilon1, epsilon0, epsilon0,
				epsilon0, epsilon0, epsilon0,
				epsilon1, epsilon0, epsilon1,
				epsilon0, epsilon0, epsilon0,
				epsilon0, epsilon0, epsilon1
			};
	
	public static final float[] faceZP = new float[]
			{
				epsilon1, epsilon1, epsilon1,
				epsilon1, epsilon0, epsilon1,
				epsilon0, epsilon0, epsilon1,
				epsilon1, epsilon1, epsilon1,
				epsilon0, epsilon0, epsilon1,
				epsilon0, epsilon1, epsilon1
			};
	public static final float[] faceZM = new float[]
			{
				epsilon0, epsilon1, epsilon0,
				epsilon0, epsilon0, epsilon0,
				epsilon1, epsilon0, epsilon0,
				epsilon0, epsilon1, epsilon0,
				epsilon1, epsilon0, epsilon0,
				epsilon1, epsilon1, epsilon0
			};
	
	public static final float[] faceUV = new float[]
			{
				uv0, uv0,
				uv0, uv1,
				uv1, uv1,
				uv0, uv0,
				uv1, uv1,
				uv1, uv0,
			};
	
	public static final float[] faceX = new float[]
			{
				1f, 1f, 1f,
				1f, 0f, 1f,
				0f, 0f, 0f,
				1f, 1f, 1f,
				0f, 0f, 0f,
				0f, 1f, 0f,
				
				0f, 1f, 0f,
				0f, 0f, 0f,
				1f, 0f, 1f,
				0f, 1f, 0f,
				1f, 0f, 1f,
				1f, 1f, 1f,
				
				0f, 1f, 1f,
				0f, 0f, 1f,
				1f, 0f, 0f,
				0f, 1f, 1f,
				1f, 0f, 0f,
				1f, 1f, 0f,
				
				1f, 1f, 0f,
				1f, 0f, 0f,
				0f, 0f, 1f,
				1f, 1f, 0f,
				0f, 0f, 1f,
				0f, 1f, 1f,
			};
	

	
	/*private static final float[] fullColor = new float[]
			{
				1f, 1f, 1f
			};*/
	
	private final BlockContainer[][][] blocks;
	private final LightContainer[][][] lights;
	private final MeshContainer meshContainer;
	private final long newConsistent;
	
	private FloatList verticesList;
	private FloatList uvList;
	private FloatList colorList;
	
	private final Color5 workColor = new Color5(0f, 0f, 0f, 0f);
	
	
	public ChunkMeshBuilder(BlockContainer[][][] blocks, LightContainer[][][] lights, MeshContainer meshContainer, long newConsistent)
	{
		this.blocks = blocks;
		this.lights = lights;
		this.meshContainer = meshContainer;
		this.newConsistent = newConsistent;
		meshContainer.wip = true;
	}
	
	
	public void executeOnMainThread()
	{
		Mesh newMesh = null;
		
		if(verticesList != null)
		{
			if(verticesList.size() != 0)
			{
				newMesh = new Mesh(verticesList, uvList, colorList);
			}
			verticesList.free();
			uvList.free();
			colorList.free();
		}
		meshContainer.set(newMesh, newConsistent);
		meshContainer.lastChange = System.currentTimeMillis();
		meshContainer.wip = false;
	}
	
	public void execute()
	{
		boolean allSolid = true;
		for(int x = 0; x < 3; x++)
		{
			for(int y = 0; y < 3; y++)
			{
				for(int z = 0; z < 3; z++)
				{
					allSolid = allSolid && blocks[x][y][z].getSolid();
				}
			}
		}
		
		
		if(!blocks[1][1][1].getEmpty() && !allSolid)
		{	
			verticesList = new FloatList();
			uvList = new FloatList();
			colorList = new FloatList();
			
			for(int x = 0; x < Settings.CHUNK_SIZE; x++)
			{
				for(int y = 0; y < Settings.CHUNK_SIZE; y++)
				{
					for(int z = 0; z < Settings.CHUNK_SIZE; z++)
					{
						// Border or not solid
						if((x == 0 || x == Settings.CHUNK_SIZE - 1)
						|| (y == 0 || y == Settings.CHUNK_SIZE - 1)
						|| (z == 0 || z == Settings.CHUNK_SIZE - 1)
						|| (!blocks[1][1][1].getSolid()))
						{
							getBlock(x, y, z).render(this, x, y, z);
						}
					}
				}
			}
		}
		
		//blocks = null;
		//lights = null;
		
		Debug.chunkBuilds++;
		
		Objects.mainQueue.add(this);
	}
	
	
	public void addFace(int x, int y, int z, float[] face, Color5 color, float[] uvs, Vector2f[] uv)
	{	
		int v = 0;
		for(int i = 0; i < face.length; i+=3)
		{
			verticesList.add(face[i+0] + x);
			verticesList.add(face[i+1] + y);
			verticesList.add(face[i+2] + z);
			
			colorList.add((float) color.r * color.norm); 
			colorList.add((float) color.g * color.norm); 
			colorList.add((float) color.b * color.norm);
			colorList.add((float) color.sky * color.norm);
			
			uvList.add(uvs[v+0] * uv[1].x + (1 - uvs[v+0]) * uv[0].x); 
			uvList.add(uvs[v+1] * uv[1].y + (1 - uvs[v+1]) * uv[0].y);
			//uvList.add((float) color.norm);
			
			
			v = (v+2) % uvs.length;
		}
	}
	
	public Block getBlock(int x, int y, int z)
	{
		int chunkX = MathX.chunkFloorDiv(x) + 1;
		int chunkY = MathX.chunkFloorDiv(y) + 1;
		int chunkZ = MathX.chunkFloorDiv(z) + 1;
		
		int xp = MathX.chunkMod(x);
		int yp = MathX.chunkMod(y);
		int zp = MathX.chunkMod(z);

		return blocks[chunkX][chunkY][chunkZ].getBlock(xp, yp, zp);
	}
	
	public Color5 getColor(int x, int y, int z)
	{
		int chunkX = MathX.chunkFloorDiv(x) + 1;
		int chunkY = MathX.chunkFloorDiv(y) + 1;
		int chunkZ = MathX.chunkFloorDiv(z) + 1;
		
		int xp = MathX.chunkMod(x);
		int yp = MathX.chunkMod(y);
		int zp = MathX.chunkMod(z);
		
		lights[chunkX][chunkY][chunkZ].getColor(xp, yp, zp, workColor);
		
		return workColor;
	}
}
