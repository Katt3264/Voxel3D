package voxel3d.data;

import java.io.IOException;

import voxel3d.global.Debug;
import voxel3d.item.Item;

public class Inventory implements DataStreamable{
	
	
	public ItemValue[] items = new ItemValue[10 * 5];
	
	public ItemValue mouseItem = new ItemValue();
	
	public int selectedHotbar = 0;
	
	public Inventory()
	{		
		for(int i = 0; i < items.length; i++)
		{
			items[i] = new ItemValue();
		}
	}
	
	public boolean addItem(Item item)
	{
		for(int i = 0; i < items.length; i++)
		{
			if((items[i].item == item && items[i].value < 99 && items[i].value > 0))
			{
				items[i].item = item;
				items[i].value++;
				return true;
			}
		}
		
		for(int i = 0; i < items.length; i++)
		{
			if(items[i].value == 0)
			{
				items[i].item = item;
				items[i].value++;
				return true;
			}
		}
		return false;
	}
	
	public boolean canCraft(Recipe recipe)
	{
		for(int c = 0; c < recipe.inItems.length; c++)
		{
			int value = recipe.inValues[c];
			for(int i = 0; i < items.length; i++)
			{
				if(items[i].item == recipe.inItems[c])
				{
					value -= items[i].value;
				}
			}
			if(value > 0)
			{
				return false;
			}
		}
		return true;
	}
	
	public void craft(Recipe recipe)
	{
		for(int c = 0; c < recipe.inItems.length; c++)
		{
			int value = recipe.inValues[c];
			for(int i = 0; i < items.length; i++)
			{
				if(items[i].item == recipe.inItems[c])
				{
					int consume = Math.min(value, items[i].value);
					
					items[i].value -= consume;
					value -= consume;
					
					if(items[i].value == 0)
					{
						items[i].item = null;
					}
				}
			}
		}
		
		for(int c = 0; c < recipe.outItems.length; c++)
		{
			for(int i = 0; i < recipe.outValues[c]; i++)
			{
				boolean b = addItem(recipe.outItems[c]);
				if(!b)
				{
					Debug.err("inventory full, item discarded");
				}
			}
		}
	}

	@Override
	public void read(DataInputStream stream) throws IOException 
	{
		for(int i = 0; i < items.length; i++)
		{
			items[i].item = stream.readItem();
			items[i].value = stream.readInt();
		}
		mouseItem.item = stream.readItem();
		mouseItem.value = stream.readInt();
		
		stream.close();
	}

	@Override
	public void write(DataOutputStream stream) 
	{
		for(int i = 0; i < items.length; i++)
		{
			stream.writeItem(items[i].item);
			stream.writeInt(items[i].value);
		}
		stream.writeItem(mouseItem.item);
		stream.writeInt(mouseItem.value);
	}

}
