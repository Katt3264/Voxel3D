package voxel3d.block;

import voxel3d.global.AssetLoader;
import voxel3d.level.ChunkMeshBuilder;
import voxel3d.physics.AABB;
import voxel3d.utility.Color5;
import voxel3d.utility.Vector2f;

public abstract class SolidCubeBlock extends Block {
	
	public static Vector2f[] getUVFromName(String name)
	{
		return AssetLoader.loadBlockTexture(name);
	}
	
	public static Vector2f[][] getUVsFromTopSideBottom(String top, String side, String bottom)
	{
		return new Vector2f[][] {
			AssetLoader.loadBlockTexture(side),
			AssetLoader.loadBlockTexture(side),
			AssetLoader.loadBlockTexture(top),
			AssetLoader.loadBlockTexture(bottom),
			AssetLoader.loadBlockTexture(side),
			AssetLoader.loadBlockTexture(side)
		};
	}
	
	public static Vector2f[][] getUVsFromTopSide(String top, String side)
	{
		return getUVsFromTopSideBottom(top, side, top);
	}
	
	@Override
	public boolean hasCollisionbox()
	{
		return true;
	}
	
	@Override
	public boolean hasSelectionBox()
	{
		return true;
	}
	
	@Override
	public void getSelectionBox(int x, int y, int z, AABB aabb)
	{
		aabb.set(x, y, z, x + 1, y + 1, z + 1);
	}
	
	@Override
	public void getCollisionBox(int x, int y, int z, AABB aabb)
	{
		aabb.set(x, y, z, x + 1, y + 1, z + 1);
	}
	
	@Override
	public boolean isSolidBlock()
	{
		return true;
	}
	
	@Override
	public boolean isLightTransparent()
	{
		return false;
	}
	
	@Override
	public boolean hasGeometry()
	{
		return true;
	}
	
	@SuppressWarnings("static-access")
	public void render(ChunkMeshBuilder mb, int x, int y, int z)
	{
		Color5 cxp = mb.getColor(x+1, y, z).setNormal(0.9f);
		if(shouldDraw(mb.getBlock(x + 1, y, z))) {mb.addFace(x, y, z, mb.faceXP, cxp, mb.faceUV, getUV(0));}
		
		Color5 cxm = mb.getColor(x-1, y, z).setNormal(0.9f);
		if(shouldDraw(mb.getBlock(x - 1, y, z))) {mb.addFace(x, y, z, mb.faceXM, cxm, mb.faceUV, getUV(1));}
		
		Color5 cyp = mb.getColor(x, y+1, z).setNormal(1.0f);;
		if(shouldDraw(mb.getBlock(x, y + 1, z))) {mb.addFace(x, y, z, mb.faceYP, cyp, mb.faceUV, getUV(2));}
		
		Color5 cym = mb.getColor(x, y-1, z).setNormal(1.0f);;
		if(shouldDraw(mb.getBlock(x, y - 1, z))) {mb.addFace(x, y, z, mb.faceYM, cym, mb.faceUV, getUV(3));}
		
		Color5 czp = mb.getColor(x, y, z+1).setNormal(0.8f);
		if(shouldDraw(mb.getBlock(x, y, z + 1))) {mb.addFace(x, y, z, mb.faceZP, czp, mb.faceUV, getUV(4));}
		
		Color5 czm = mb.getColor(x, y, z-1).setNormal(0.8f);
		if(shouldDraw(mb.getBlock(x, y, z - 1))) {mb.addFace(x, y, z, mb.faceZM, czm, mb.faceUV, getUV(5));}
	}
	
	protected boolean shouldDraw(Block other)
	{
		//return (!other.isSolidBlock() && isSolidBlock()) || (!other.isSolidBlock() && !isSolidBlock() && this != other);
		return !other.isSolidBlock();
	}
	
	protected abstract Vector2f[] getUV(int index);

}
