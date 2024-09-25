package voxel3d.utility;


public class PersistentVector3D<T> {
	
	private T[][][] chunks;
    public final int size;
	
    @SuppressWarnings("unchecked")
	public PersistentVector3D(int size) 
    {
        this.size = size;

        chunks = (T[][][]) new Object[size][size][size];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                for (int z = 0; z < size; z++) {
                    chunks[x][y][z] = null;
                }
            }
        }
    }
    
    @SuppressWarnings("unchecked")
	public void shift(int xs, int ys, int zs, int newSize)
    {
    	if(xs == 0 && ys == 0 && zs == 0 && newSize == size) {return;}
    	
    	T[][][] newChunks = (T[][][]) new Object[newSize][newSize][newSize];
        for (int x = 0; x < newSize; x++) {
            for (int y = 0; y < newSize; y++) {
                for (int z = 0; z < newSize; z++) {
                	if (x - xs >= 0 && x - xs < size 
                	&&  y - ys >= 0 && y - ys < size 
                	&&  z - zs >= 0 && z - zs < size) {
                        newChunks[x][y][z] = chunks[x - xs][y - ys][z - zs];
                    }
                }
            }
        }
        this.chunks = newChunks;
    }
    
    public T get(int x, int y, int z) 
    {
        if (x < 0 || x >= size || y < 0 || y >= size || z < 0 || z >= size) {
        	return null;
        }

        return chunks[x][y][z];
    }
    
    @SuppressWarnings("unchecked")
	public <S> boolean getNeighbours(int xp, int yp, int zp, S[][][] ns)
    {
    	if (xp < 1 || xp >= size-1 || yp < 1 || yp >= size-1 || zp < 1 || zp >= size-1) {
    		return false;
    	}

		for(int x = -1; x <= 1; x++)
		{
			for(int y = -1; y <= 1; y++)
			{
				for(int z = -1; z <= 1; z++)
				{
					ns[x+1][y+1][z+1] = (S) get(xp + x, yp + y, zp + z);
					if(get(xp + x, yp + y, zp + z) == null)
						return false;
				}
			}
		}
		return true;
    }
    
	public void set(int x, int y, int z, T chunk) 
	{
        if (x < 0 || x >= size || y < 0 || y >= size || z < 0 || z >= size) {
        	return;
        }

        chunks[x][y][z] = chunk;
    }
}
