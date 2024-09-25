package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.NeutroniumFluxBlock;
import voxel3d.block.SolidCubeBlock;
import voxel3d.utility.Vector2f;

public class NeutroniumRetainerBlock extends SolidCubeBlock implements NeutroniumFluxBlock {
	
	private static NeutroniumRetainerBlock sharedInstance = new NeutroniumRetainerBlock();
	private static Vector2f[] uvs = SolidCubeBlock.getUVFromName("Neutronium retainer");
	
	static {
		Block.setBlockDeserializerForLegacyID(getInstance());
	}
	
	public static NeutroniumRetainerBlock getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public Block getDataStreamableInstance()
	{
		return getInstance();
	}
	
	@Override
	public int getLegacyID()
	{
		return 0x997f2ddc;
	}
	
	@Override
	public String getName()
	{
		return "Neutronium retainer";
	}

	@Override
	protected Vector2f[] getUV(int index)
	{
		return uvs;
	}
	
	@Override
	public String getInfo()
	{
		return super.getInfo();
	}

	@Override
	public double getNeutroniumFlux() 
	{
		return 0;
	}

	@Override
	public void addNeutroniumFlux(double value) 
	{
	}

	@Override
	public double getNeutroniumFaceLoss() 
	{
		return 0.1;
	}

}
