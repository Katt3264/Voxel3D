package voxel3d.block;

import voxel3d.utility.FaceDirection;

public interface FluidAcceptingBlock {
	
	public FluidContainer getInterface(FaceDirection side);

}
