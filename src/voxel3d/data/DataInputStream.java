package voxel3d.data;

import java.io.IOException;
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
	
	public double readKeyValueDouble(String key) throws IOException
	{
		String line = readLine();
		String[] kv = line.split("=");
		if(kv[0].equals(key))
			return Double.parseDouble(kv[1]);
		
		throw new RuntimeException("key missmatch: " + key + " " + kv[0]);
	}
	
	public String readKeyValue(String key) throws IOException
	{
		String line = readLine();
		String[] kv = line.split("=");
		if(kv[0].equals(key))
			return kv[1];
		
		throw new RuntimeException("key missmatch: " + key + " " + kv[0]);
	}
	
	public String readLine() throws IOException
	{
		String ret = "";
		while(true)
		{
			char c = (char)readByte();
			if(c == '\n')
				return ret;
				
			ret = ret + c;
		}
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
		int length = (int)readKeyValueDouble("entity_size");
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
