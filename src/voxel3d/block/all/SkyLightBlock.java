package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.SolidCubeBlock;
import voxel3d.level.ChunkMeshBuilder;
import voxel3d.utility.Color5;
import voxel3d.utility.Vector2f;

public class SkyLightBlock extends SolidCubeBlock {

	private static SkyLightBlock sharedInstance = new SkyLightBlock();
	private static Vector2f[] uvs = SolidCubeBlock.getUVFromName("Sky light");
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static SkyLightBlock getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public Vector2f[] getUV(int index) 
	{
		return uvs;
	}

	@Override
	public int getLegacyID() {
		return 0x08b9e581;
	}

	@Override
	public String getName() {
		return "Sky light";
	}
	
	@Override
	public float getSkyLight()
	{
		return 1f;
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void render(ChunkMeshBuilder mb, int x, int y, int z)
	{
		Color5 cxp = mb.getColor(x+1, y, z).setNormal(1.0f);
		if(shouldDraw(mb.getBlock(x + 1, y, z))) {mb.addFace(x, y, z, mb.faceXP, cxp, mb.faceUV, getUV(0));}
		
		Color5 cxm = mb.getColor(x-1, y, z).setNormal(1.0f);
		if(shouldDraw(mb.getBlock(x - 1, y, z))) {mb.addFace(x, y, z, mb.faceXM, cxm, mb.faceUV, getUV(1));}
		
		Color5 cyp = mb.getColor(x, y+1, z).setNormal(1.0f);
		if(shouldDraw(mb.getBlock(x, y + 1, z))) {mb.addFace(x, y, z, mb.faceYP, cyp, mb.faceUV, getUV(2));}
		
		Color5 cym = mb.getColor(x, y-1, z).setNormal(1.0f);
		if(shouldDraw(mb.getBlock(x, y - 1, z))) {mb.addFace(x, y, z, mb.faceYM, cym, mb.faceUV, getUV(3));}
		
		Color5 czp = mb.getColor(x, y, z+1).setNormal(1.0f);
		if(shouldDraw(mb.getBlock(x, y, z + 1))) {mb.addFace(x, y, z, mb.faceZP, czp, mb.faceUV, getUV(4));}
		
		Color5 czm = mb.getColor(x, y, z-1).setNormal(1.0f);
		if(shouldDraw(mb.getBlock(x, y, z - 1))) {mb.addFace(x, y, z, mb.faceZM, czm, mb.faceUV, getUV(5));}
	}
	
	/*private boolean shouldDraw(Block other)
	{
		//return (!other.isSolidBlock() && isSolidBlock()) || (!other.isSolidBlock() && !isSolidBlock() && this != other);
		return !other.isSolidBlock();
	}*/

}
