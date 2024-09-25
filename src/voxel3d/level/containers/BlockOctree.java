package voxel3d.level.containers;

import java.io.IOException;

import voxel3d.block.Block;
import voxel3d.data.DataInputStream;
import voxel3d.data.DataOutputStream;
import voxel3d.data.DataStreamable;

public class BlockOctree implements DataStreamable {

	private Object[] nodes;
	private Block value;
	private byte depth;
	
	public BlockOctree(byte depth, Block value) 
	{
		this.depth = depth;
		this.value = value;
	}
	
	
	public void set(int x, int y, int z, Block value)
	{
		
		int mask = 1 << (depth-1);
		
		int index = 0;
		if((x & mask) != 0)
			index += 1;
		if((y & mask) != 0)
			index += 2;
		if((z & mask) != 0)
			index += 4;
		
		if(this.nodes == null)
		{
			nodes = depth == 1 ? new Block[8] : new BlockOctree[8];
			for(int i = 0; i < nodes.length; i++)
			{
				nodes[i] = depth == 1 ? this.value : new BlockOctree((byte) (depth - 1), this.value);
			}
			this.value = null;
		}
		
		if(depth == 1)
		{
			((Block[])nodes)[index] = value;
		}
		else
		{
			((BlockOctree[])nodes)[index].set(x, y, z, value);
		}
		
		
		boolean homogenious = true;
		for(int i = 0; i < nodes.length; i++)
		{
			if(depth == 1)
			{
				if(((Block[])nodes)[i] != value || ((Block[])nodes)[i] == null)
					homogenious = false;
			}
			else
			{
				if(((BlockOctree[])nodes)[i].value != value || ((BlockOctree[])nodes)[i].value == null)
					homogenious = false;
			}
		}
		
		if(homogenious)
		{
			this.value = value;
			this.nodes = null;
		}
	}
	
	public Block get(int x, int y, int z)
	{
		if(depth == 0 || this.value != null)
			return value;
		
		
		int mask = 1 << (depth-1);
		
		int index = 0;
		if((x & mask) != 0)
			index += 1;
		if((y & mask) != 0)
			index += 2;
		if((z & mask) != 0)
			index += 4;
		
		return depth == 1 ? ((Block[])nodes)[index] : ((BlockOctree[])nodes)[index].get(x, y, z);
	}
	
	@Override
	public void read(DataInputStream stream) throws IOException 
	{
		
		if(stream.readByte() == 0)
		{
			value = stream.readBlock();
			nodes = null;
		}
		else
		{
			if(depth == 1)
			{
				value = null;
				nodes = new Block[8];
				for(int i = 0; i < nodes.length; i++)
				{
					nodes[i] = stream.readBlock();
				}
			}
			else
			{
				value = null;
				nodes = new BlockOctree[8];
				for(int i = 0; i < nodes.length; i++)
				{
					nodes[i] = new BlockOctree((byte) (depth - 1), null);
					((BlockOctree) nodes[i]).read(stream);
				}
			}
		}
	}

	@Override
	public void write(DataOutputStream stream) 
	{
			
		if(nodes == null)
		{
			stream.writeByte((byte) 0);
			stream.writeBlock(value);
		}
		else
		{
			stream.writeByte((byte) 1);
			for(int i = 0; i < nodes.length; i++)
			{
				if(depth == 1)
				{
					stream.writeBlock((Block) nodes[i]);
				}
				else
				{
					((BlockOctree) nodes[i]).write(stream);
				}
			}
		}
	}
	
	
	
}
