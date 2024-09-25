package voxel3d.block.utility;

import voxel3d.block.Block;
import voxel3d.block.NeutroniumFluxBlock;
import voxel3d.block.context.BlockOnSimulateContext;

public class NeutroniumBlockUtility {
	
	public static void FluxInteract(NeutroniumFluxBlock source, BlockOnSimulateContext context)
	{
		Block[] contacts = new Block[6];
		context.getBlocks(contacts);
		
		double cSum = 0;
		double inFlow = 0;
		
		for(Block block : contacts)
		{
			if(block instanceof NeutroniumFluxBlock)
			{
				NeutroniumFluxBlock tBlock = (NeutroniumFluxBlock) block;
				double conduct = 2.0 / ((1.0 / source.getNeutroniumFaceLoss()) + (1.0 / tBlock.getNeutroniumFaceLoss()));
				double in = tBlock.getNeutroniumFlux() * conduct;
				
				cSum += conduct;
				inFlow += in;
			}
			else
			{
				double conduct = 2.0 / ((1.0 / source.getNeutroniumFaceLoss()) + 1.0);
				cSum += conduct;
			}
		}
		
		if(cSum != 0)
		{
			double target = (inFlow / cSum);
			source.addNeutroniumFlux((target - source.getNeutroniumFlux()) * 0.5);
		}
	}

}
