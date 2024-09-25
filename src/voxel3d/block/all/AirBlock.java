package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.level.ChunkMeshBuilder;
import voxel3d.physics.AABB;

public class AirBlock extends Block{
	
	private static AirBlock sharedInstance = new AirBlock();
	
	static {
		Block.setBlockDeserializerForLegacyID(sharedInstance);
	}
	
	public static AirBlock getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public int getLegacyID()
	{
		return 0xbc117879;
	}
	
	@Override
	public String getName()
	{
		return "Air";
	}
	
	@Override
	public boolean hasCollisionbox()
	{
		return false;
	}
	
	@Override
	public boolean hasSelectionBox()
	{
		return false;
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
		return false;
	}
	
	@Override
	public void render(ChunkMeshBuilder mb, int x, int y, int z)
	{
	}

	@Override
	public void getSelectionBox(int x, int y, int z, AABB aabb) {
	}

	@Override
	public void getCollisionBox(int x, int y, int z, AABB aabb) {		
	}
	
	
	
}
