package voxel3d.block;

import voxel3d.global.AssetLoader;
import voxel3d.level.ChunkMeshBuilder;
import voxel3d.physics.AABB;
import voxel3d.utility.Color5;
import voxel3d.utility.Vector2f;

public abstract class XBlock extends Block {
	
	public static Vector2f[] getUVFromName(String name)
	{
		return AssetLoader.loadBlockTexture(name);
	}
	
	@Override
	public boolean hasCollisionbox()
	{
		return false;
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
		return false;
	}
	
	@Override
	public boolean isLightTransparent()
	{
		return true;
	}
	
	@Override
	public boolean hasGeometry()
	{
		return true;
	}
	
	@SuppressWarnings("static-access")
	public void render(ChunkMeshBuilder mb, int x, int y, int z)
	{
		Color5 c = mb.getColor(x, y, z).setNormal(1.0f);
		mb.addFace(x, y, z, mb.faceX, c, mb.faceUV, getUV());
	}
	
	protected abstract Vector2f[] getUV();
	
}
