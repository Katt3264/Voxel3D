package voxel3d.generation.biome;

import voxel3d.block.Block;
import voxel3d.block.all.AirBlock;
import voxel3d.generation.SimplexNoise;

public class RainbowBiome extends Biome {
	
	private static String[] stones = new String[] {
			"Red stone",
			"Magenta stone",
			"Blue stone",
			"Cyan stone",
			"Green stone",
			"Yellow stone"
	};
	
	public Block getBlock(int x, int y, int z)
	{
		/*double in = (Math.min(inland, 32))/32d;
		int h = y;
		y -= Biome.getHeightOverworld(x, z) * (1-in) + getHeight(x, z) * in;
		
		if(y > 0)
		{
			return Block.getByName("Air");
		}
		else
		{
			if(h >= 0)
			{
				return Block.getByName(stones[h%stones.length]);
			}
			else
			{
				return Block.getByName("Stone");
			}
		}*/
		
		return AirBlock.getInstance();
	}

	private static int getHeight(int x, int z)
	{
		double h =(
				SimplexNoise.noise(x / 128d, z / 128d) * 16 +
				SimplexNoise.noise(x / 32d, z / 32d) * 2 +
				18
				);
		
		return (int) (h*h/16);
	}

	@Override
	public double getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

}
