package voxel3d.generation;

import java.util.Random;

import voxel3d.block.Block;
import voxel3d.block.all.*;

public class OreGen {
	
	private static Random random = new Random();
	
	
	public static Block getBlock(int x, int y, int z)
	{
		Block block = getOre(x >> 1, y >> 1, z >> 1);
		
		
		return block;
	}
	
	
	private static Block getOre(int x, int y, int z)
	{
		int m = 32;
		int v = getRandom(x, y, z, m);
		
		if(v == 0)
		{
			return TinOreBlock.getInstance();
		}
		else if(v == 1)
		{
			return CopperOreBlock.getInstance();
		}
		else if(v == 2)
		{
			return IronOreBlock.getInstance();
		}
		else if(v == 3)
		{
			return GoldOreBlock.getInstance();
		}
		else if(v == 14)
		{
			return RubyOreBlock.getInstance();
		}
		else if(v == 15)
		{
			return EmeraldOreBlock.getInstance();
		}
		else if(v == 16)
		{
			return SapphireOreBlock.getInstance();
		}
		return StoneBlock.getInstance();
	}
	
	
	private static int getRandom(int x, int y, int z, int l)
	{
		random.setSeed(x * 563 + y * 214767 + z * 73529 );
		return random.nextInt(l);
	}

}
