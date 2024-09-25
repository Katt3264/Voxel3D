package voxel3d.data;

import java.util.LinkedList;
import java.util.List;

import voxel3d.block.Block;

public class Crafting {
	
	private static List<Recipe> recipes = new LinkedList<Recipe>();
	
	public static void init(Iterable<Iterable<KeyValuePair>> data)
	{
		for(Iterable<KeyValuePair> pairs : data)
		{
			recipes.add(new Recipe(pairs));
		}
	}
	
	public static Recipe[] getRecipesForStation(Block station)
	{
		int c = 0;
		for(Recipe recipe : recipes)
		{
			if(recipe.match(station))
				c++;
		}
		
		Recipe[] res = new Recipe[c];
		c = 0;
		for(Recipe recipe : recipes)
		{
			if(recipe.match(station))
			{
				res[c] = recipe;
				c++;
			}
		}
		return res;
	}

}
