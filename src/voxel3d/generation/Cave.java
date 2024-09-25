package voxel3d.generation;

public class Cave {
	
	public static boolean isCave(int x, int y, int z)
	{
		return caveEntryMap(x , y, z);
	}
	
	private static boolean caveEntryMap(int x, int y, int z)
	{
		int closestX = Math.floorDiv(x + 64 , 128) * 128;
		int closestZ = Math.floorDiv(z + 64 , 128) * 128;
		
		int r = caveRadius(y);
		
		for(int wy = -r; wy <= r; wy ++)
		{
			int wx = caveWarpX(closestX * 0, y + wy, closestZ* 0);
			int wz = caveWarpZ(closestX* 0, y + wy, closestZ* 0);
			double distance = (x + wx - closestX) * (x + wx - closestX) + (z + wz - closestZ) * (z + wz - closestZ) + wy*wy*4;
			
			if(distance < r*r)
			{
				return true;
			}
		}
		
		return false;
	}
	
	private static int caveWarpX(int x, int y, int z)
	{
		double d = (
				SimplexNoise.noise(x / 32d, y / 64d, z / 32d) * 32d +
				SimplexNoise.noise(x / 32d, y / 32d, z / 32d) * 16d +
				SimplexNoise.noise(x / 32d, y / 16d, z / 32d) * 8d
				);
		return (int) (d);
	}
	
	private static int caveWarpZ(int x, int y, int z)
	{
		x += 7368245;
		y += 1278534;
		z += 9345672;
		
		double d = (
				SimplexNoise.noise(x / 32d, y / 64d, z / 32d) * 32d +
				SimplexNoise.noise(x / 32d, y / 32d, z / 32d) * 16d +
				SimplexNoise.noise(x / 32d, y / 16d, z / 32d) * 8d
				);
		return (int) (d);
	}
	
	private static int caveRadius(int y)
	{
		if(y > -220)
		{
			return 4;
		}
		else if(y > -225)
		{
			return 5;
		}
		else if(y > -230)
		{
			return 6;
		}
		else if(y > -235)
		{
			return 7;
		}
		else if(y > -240)
		{
			return 8;
		}
		else if(y > -245)
		{
			return 9;
		}
		else if(y > -250)
		{
			return 10;
		}
		else
		{
			return 0;
		}
	}

}
