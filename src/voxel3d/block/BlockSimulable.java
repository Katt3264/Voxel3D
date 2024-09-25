package voxel3d.block;

import voxel3d.block.context.BlockOnSimulateContext;

public interface BlockSimulable {
	
	public void onSimulate(BlockOnSimulateContext context);

}
