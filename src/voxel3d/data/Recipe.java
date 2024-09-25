package voxel3d.data;

import java.util.ArrayList;
import java.util.List;

import voxel3d.block.Block;
import voxel3d.item.Item;

public class Recipe {
	
	public Item[] inItems;
	public Integer[] inValues;
	public Item[] outItems;
	public Integer[] outValues;
	public String[] stations;
	
	public Item icon;
	
	
	public Recipe(Iterable<KeyValuePair> input)
	{
		List<Item> inI = new ArrayList<Item>();
		List<Integer> inV = new ArrayList<Integer>();
		List<Item> outI = new ArrayList<Item>();
		List<Integer> outV = new ArrayList<Integer>();
		List<String> s = new ArrayList<String>();
		for(KeyValuePair pair : input)
		{
			String key = pair.getKey();
			
			if(key.equals("icon")) 
			{
				icon = Item.GetInstanceFromName(pair.getValue());
			}
			
			if(key.equals("outItem")) 
			{
				outI.add(Item.GetInstanceFromName(pair.getValue()));
			}
			
			if(key.equals("outValue")) 
			{
				outV.add(Integer.parseInt(pair.getValue()));
			}
			
			if(key.equals("station")) 
			{
				s.add(pair.getValue());
			}
			
			if(key.equals("item")) 
			{
				inI.add(Item.GetInstanceFromName(pair.getValue()));
			}
			
			if(key.equals("value")) 
			{
				inV.add(Integer.parseInt(pair.getValue()));
			}
		}
		
		inItems = new Item[inI.size()];
		inI.toArray(inItems);
		
		inValues = new Integer[inV.size()];
		inV.toArray(inValues);
		
		outItems = new Item[outI.size()];
		outI.toArray(outItems);
		
		outValues = new Integer[outV.size()];
		outV.toArray(outValues);
		
		stations = new String[s.size()];
		s.toArray(stations);
		
		if(icon == null && outItems.length == 1)
		{
			icon = outItems[0];
		}
		
		if(icon == null && inItems.length == 1)
		{
			icon = inItems[0];
		}
	}
	
	public boolean match(Block station)
	{
		if(stations.length == 0 && station.getHUD() == null)
			return true;
		
		for(int i = 0; i < stations.length; i++)
		{
			if(stations[i].equals(station.getName()))
				return true;
		}
		return false;
		
	}
	

}
