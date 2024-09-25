package voxel3d.block.context;

public class BlockOnUseContext {
	
	public boolean interactionPreventsPlaceBlock = false;
	
	public boolean dataModified = false;
	
	public void modifiedVissibleProperty()
	{
		//rebuildMesh = true;
		dataModified = true;
		interactionPreventsPlaceBlock = true;
	}
	
	/*public void modifiedInvissibleProperty()
	{
		dataModified = true;
		interactionPreventsPlaceBlock = true;
	}*/
	
	

}
