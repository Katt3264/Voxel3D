package voxel3d.block;

public interface FluxTransferBlock {
	
	public double getFlux();
	public void addFlux(double value);
	public double getFluxConductance();
	
	
}
