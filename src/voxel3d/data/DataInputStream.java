package voxel3d.data;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import voxel3d.block.Block;
import voxel3d.entity.Entity;
import voxel3d.item.Item;

public class DataInputStream {
	
	private int ptr;
	private byte[] data;
	
	public DataInputStream(byte[] data)
	{
		this.data = data;
		this.ptr = 0;
	}
	
	public byte readByte() throws IOException
	{
		if(ptr >= data.length)
		{
			throw new IOException("EOF");
		}
		
		byte in = data[ptr];
		ptr++;
		
		return in;
	}
	
	public int readInt() throws IOException
	{
		int v = 0;
		for(int i = 0; i < 4; i++)
		{
			v = v >>> 8;
			v |= (readByte() & 0xff) << 24;
		}
		return v;
	}
	
	public double readDouble() throws IOException
	{
		String str = readString();
		return Double.parseDouble(str);
	}
	
	public String readString() throws IOException
	{
		int length = readInt();
		byte[] b = new byte[length];
		for(int i = 0; i < b.length; i++)
		{
			b[i] = readByte();
		}
		return new String(b, StandardCharsets.UTF_8);
	}
	
	public Block readBlock() throws IOException
	{
		Block block = Block.GetInstanceFromData(this);
		return block;
	}
	
	public Item readItem() throws IOException
	{
		return Item.GetInstanceFromData(this);
	}
	
	public Entity readEntity() throws IOException
	{
		return Entity.GetInstanceFromData(this);
	}
	
	public Collection<Entity> readEntities() throws IOException
	{
		int length = readInt();
		List<Entity> entities = new ArrayList<Entity>(length);
		for(int i = 0; i < length; i++)
		{
			entities.add(readEntity());
		}
		return entities;
	}
	
	public void close() throws IOException
	{
		if(ptr != data.length)
		{
			throw new IOException("Unconsumed bytes: " + ptr + " consumed, " + data.length + " in stream");
		}
	}

}
