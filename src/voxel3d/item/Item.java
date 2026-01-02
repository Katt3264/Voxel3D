package voxel3d.item;

import java.io.IOException;
import java.util.TreeMap;

import voxel3d.data.DataInputStream;
import voxel3d.data.DataOutputStream;
import voxel3d.data.DataStreamable;
import voxel3d.global.Debug;
import voxel3d.global.Objects;
import voxel3d.item.all.*;
import voxel3d.item.context.ItemRenderContext;
import voxel3d.item.context.ItemUseContext;


public abstract class Item implements DataStreamable {
	
	private static TreeMap<String, Item> nameInstanceMap = new TreeMap<String, Item>();
	
	
	public static void setItemDeserializerForName(Item item)
	{
		if(nameInstanceMap.containsKey(item.getName()))
		{
			Debug.err("item duplicate name: " + item.getName());
			throw new RuntimeException();
		}
		
		nameInstanceMap.put(item.getName(), item);
	}
	
	public static Item GetUnknown()
	{
		return StickItem.getInstance();
	}
	
	public static Item GetInstanceFromData(DataInputStream stream) throws IOException
	{
		String itemName = stream.readKeyValue("name");
		Item instancer = nameInstanceMap.get(itemName);
		if(instancer == null)
		{
			if(!itemName.equals("null"))
			{
				Debug.err("no item name: " + itemName);
			}
			return null;
		}
		instancer = instancer.getDataStreamableInstance();
		instancer.read(stream);
		return instancer;
	}
	
	public static Item GetInstanceFromName(String name)
	{
		Item instancer = nameInstanceMap.get(name);
		if(instancer == null)
		{
			if(!name.equals("null"))
			{
				Debug.err("no item name: " + name);
			}
			return null;
		}
		return instancer.getDataStreamableInstance();
	}
	
	public Item getDataStreamableInstance()
	{
		return this;
	}
	
	public abstract String getName();
	
	
	public void read(DataInputStream stream) throws IOException
	{
		
	}

	public void write(DataOutputStream stream) 
	{
		
	}
	
	
	public void onUse(ItemUseContext context)
	{
		
	}
	
	public void render(ItemRenderContext context)
	{
		context.drawTexture(Objects.chunkAtlas);
	}


}
