package voxel3d.data;

import java.io.IOException;

public interface DataStreamable {
	
	public void read(DataInputStream stream) throws IOException;
	public void write(DataOutputStream stream);
}
