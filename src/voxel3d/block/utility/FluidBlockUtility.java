package voxel3d.block.utility;

import voxel3d.block.Block;
import voxel3d.block.FluidAcceptingBlock;
import voxel3d.block.FluidContainer;
import voxel3d.block.context.BlockOnSimulateContext;
import voxel3d.utility.FaceDirection;

public class FluidBlockUtility 
{
	public static void exposeAllInterface(FluidAcceptingBlock source, BlockOnSimulateContext context)
	{
		exposeContainer(source, context, FaceDirection.XP);
		exposeContainer(source, context, FaceDirection.XM);
		exposeContainer(source, context, FaceDirection.YP);
		exposeContainer(source, context, FaceDirection.YM);
		exposeContainer(source, context, FaceDirection.ZP);
		exposeContainer(source, context, FaceDirection.ZM);
	}

	private static void exposeContainer(FluidAcceptingBlock source, BlockOnSimulateContext context, FaceDirection side)
	{
		FluidContainer sourceTank = source.getInterface(side);
		if(sourceTank == null)
			return;
		
		
		int x,y,z;
		FaceDirection auxSide;
		
		switch(side)
		{
		case XP:
			x = 1; y = 0; z = 0; auxSide = FaceDirection.XM; break;
		case YP:
			x = 0; y = 1; z = 0; auxSide = FaceDirection.YM; break;
		case ZP:
			x = 0; y = 0; z = 1; auxSide = FaceDirection.ZM; break;
		case XM:
			x = -1; y = 0; z = 0; auxSide = FaceDirection.XP; break;
		case YM:
			x = 0; y = -1; z = 0; auxSide = FaceDirection.YP; break;
		case ZM:
			x = 0; y = 0; z = -1; auxSide = FaceDirection.ZP; break;
			default:
				throw new RuntimeException("Unreachable");
		}
		
		Block block = context.getLocalBlock(x, y, z);
		
		FluidContainer destination = null;
		FluidAcceptingBlock dBlock = null;
		
		if(block instanceof FluidAcceptingBlock)
		{
			dBlock = ((FluidAcceptingBlock)block);
			destination = dBlock.getInterface(auxSide);
		}
		
		if(destination == null)
			return;
		
		sourceTank.equalize(destination, context.getDeltaTime());
		
	}
	
}
