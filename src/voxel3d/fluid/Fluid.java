package voxel3d.fluid;

import java.util.Map.Entry;
import java.util.TreeMap;
import voxel3d.global.Debug;

public abstract class Fluid {
	
	private static TreeMap<Integer, Fluid> idInstanceMap = new TreeMap<Integer, Fluid>();
	
	public static void setFluid(Fluid fluid)
	{
		if(idInstanceMap.containsKey(fluid.getLegacyID()))
		{
			Debug.err("Multiple fluids with same ID: " + fluid.getName() + " - " + idInstanceMap.get(fluid.getLegacyID()).getName());
			throw new RuntimeException();
		}
		idInstanceMap.put(fluid.getLegacyID(), fluid);
	}
	
	private static Fluid[] allFluids;
	public static Fluid[] getFluids()
	{
		synchronized(idInstanceMap)
		{
			if(allFluids == null)
			{
				allFluids = new Fluid[idInstanceMap.size()];
				int i = 0;
				for(Entry<Integer, Fluid> entry : idInstanceMap.entrySet())
				{
					allFluids[i] = entry.getValue();
					i++;
				}
			}
		}
		return allFluids;
	}
	
	
	public abstract int getLegacyID();
	public abstract String getName();
	

}
