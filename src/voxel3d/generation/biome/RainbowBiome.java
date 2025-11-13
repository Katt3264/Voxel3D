package voxel3d.generation.biome;

import voxel3d.block.Block;
import voxel3d.block.all.*;
import voxel3d.generation.Fields;

public class RainbowBiome extends Biome {
	
	private static Block[] stones = new Block[] {
		RedStoneBlock.getInstance(),
		MagentaStoneBlock.getInstance(),
		BlueStoneBlock.getInstance(),
		CyanStoneBlock.getInstance(),
		GreenStoneBlock.getInstance(),
		YellowStoneBlock.getInstance(),
	};
	
	private double size;
	public RainbowBiome(double size)
	{
		this.size = size;
	}
	
	public Block getBlock(int x, int y, int z)
	{
		//double in = (Math.min(inland, 32))/32d;
		int height = (int) Math.floor(getHeight(x, z));
		
		if(y > height)
		{
			return AirBlock.getInstance();
		}
		else
		{
			if(y >= 0)
			{
				return stones[y % stones.length].getBlockInstance();
			}
			else
			{
				return StoneBlock.getInstance();
			}
		}
	}

	private static double getHeight(int x, int z)
	{
		double val = Fields.OctaveMap2D(x, z, 128);
		val = Math.pow(val, 2.0)*0.5;
		//val = val*val;
		return val * 512;
	}

	@Override
	public double getSize() 
	{
		return size;
	}

}
