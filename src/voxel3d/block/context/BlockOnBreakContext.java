package voxel3d.block.context;

import java.util.ArrayList;
import java.util.Collection;

import voxel3d.item.Item;

public class BlockOnBreakContext {
	
	public int x, y, z;
	
	public Collection<Item> drops;
	
	public BlockOnBreakContext(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void dropItem(Item item)
	{
		if(drops == null)
		{
			drops = new ArrayList<Item>();
		}
		
		drops.add(item);
	}

}
