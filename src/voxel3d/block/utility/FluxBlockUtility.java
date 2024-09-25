package voxel3d.block.utility;

import voxel3d.block.Block;
import voxel3d.block.FluxTransferBlock;
import voxel3d.block.context.BlockOnSimulateContext;

public class FluxBlockUtility {
	
	public static void ThermalInteract(FluxTransferBlock source, BlockOnSimulateContext context)
	{
		Block[] contacts = new Block[6];
		context.getBlocks(contacts);
		
		double outConductance = 0;;
		double netFlow = 0;

		for(Block block : contacts)
		{
			if(block instanceof FluxTransferBlock)
			{
				FluxTransferBlock tBlock = (FluxTransferBlock) block;
				double otherConductance = tBlock.getFluxConductance();
				double conductanceToOther = 2.0 / ((1.0 / source.getFluxConductance()) + (1.0 / otherConductance));
				double drop = tBlock.getFlux() - source.getFlux();
				
				outConductance += conductanceToOther;
				netFlow += drop * conductanceToOther;
			}
		}
		
		if(outConductance != 0)
		{
			double step = netFlow / outConductance;
			source.addFlux(step);
		}
	}
}
