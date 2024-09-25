package voxel3d.block.utility;

import voxel3d.item.Item;
import voxel3d.item.all.*;

public class BlockBreakTimeUtility {
	
	public static double getBreakTimeWood(Item tool)
	{
		if(tool instanceof FlintAxeItem)
			return 1;
		
		if(tool instanceof BronzeAxeItem)
			return 0.75;
		
		if(tool instanceof IronAxeItem)
			return 0.5;
		
		if(tool instanceof GoldAxeItem)
			return 0.25;
		
		return 2;
	}
	
	public static double getBreakTimeStone(Item tool)
	{
		if(tool instanceof FlintPickaxeItem)
			return 1;
		
		if(tool instanceof BronzePickaxeItem)
			return 0.75;
		
		if(tool instanceof IronPickaxeItem)
			return 0.5;
		
		if(tool instanceof GoldPickaxeItem)
			return 0.25;
		
		return 2;
	}
	
	public static double getBreakTimeSoil(Item tool)
	{
		if(tool instanceof FlintShovelItem)
			return 1;
		
		if(tool instanceof BronzeShovelItem)
			return 0.75;
		
		if(tool instanceof IronShovelItem)
			return 0.5;
		
		if(tool instanceof GoldShovelItem)
			return 0.25;
		
		return 2;
	}
	
	public static double getBreakTimeFoliage(Item tool)
	{
		if(tool instanceof FlintMacheteItem)
			return 0.4;
		
		if(tool instanceof BronzeMacheteItem)
			return 0.3;
		
		if(tool instanceof IronMacheteItem)
			return 0.2;
		
		if(tool instanceof GoldMacheteItem)
			return 0.1;
		
		return 1;
	}

}
