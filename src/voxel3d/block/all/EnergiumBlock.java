package voxel3d.block.all;

import voxel3d.block.Block;
import voxel3d.block.BlockSimulable;
import voxel3d.block.NeutroniumFluxBlock;
import voxel3d.block.SolidCubeBlock;
import voxel3d.block.FluxTransferBlock;
import voxel3d.block.context.BlockOnSimulateContext;
import voxel3d.block.utility.NeutroniumBlockUtility;
import voxel3d.utility.TextUtill;
import voxel3d.utility.Vector2f;

public class EnergiumBlock extends SolidCubeBlock implements BlockSimulable, FluxTransferBlock, NeutroniumFluxBlock {
	
	private static Vector2f[] uvs = SolidCubeBlock.getUVFromName("Energium");
	
	private double neutroniumFlux = 0;
	
	static {
		Block.setBlockDeserializerForLegacyID(getInstance());
	}
	
	public static EnergiumBlock getInstance()
	{
		return new EnergiumBlock();
	}
	
	@Override
	public Block getDataStreamableInstance()
	{
		return getInstance();
	}
	
	@Override
	public int getLegacyID()
	{
		return 0xe4da1439;
	}
	
	@Override
	public String getName()
	{
		return "Energium";
	}

	@Override
	protected Vector2f[] getUV(int index)
	{
		return uvs;
	}
	
	@Override
	public String getInfo()
	{
		return super.getInfo() 
				+ " \nFlux:" + TextUtill.toUnit(getFlux()) 
				+ " \nN:" + TextUtill.toUnit(neutroniumFlux);
	}

	@Override
	public void onSimulate(BlockOnSimulateContext context) 
	{
		double neutroniumTarget = 100;
		double emission = neutroniumTarget - neutroniumFlux;

		neutroniumFlux += 0.1 * emission * 0.5;
		
		NeutroniumBlockUtility.FluxInteract(this, context);
	}

	@Override
	public double getFlux()
	{
		return neutroniumFlux * 1000;
	}

	@Override
	public void addFlux(double value) 
	{
		
	}

	@Override
	public double getNeutroniumFlux() 
	{
		return neutroniumFlux;
	}

	@Override
	public void addNeutroniumFlux(double value) 
	{
		neutroniumFlux += value;
	}

	@Override
	public double getNeutroniumFaceLoss() 
	{
		return 10;
	}

	@Override
	public double getFluxConductance() 
	{
		return 1;
	}


}
