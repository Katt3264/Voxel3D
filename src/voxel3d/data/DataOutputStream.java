package voxel3d.data;


import java.nio.charset.StandardCharsets;
import java.util.Collection;

import voxel3d.block.Block;
import voxel3d.entity.Entity;
import voxel3d.item.Item;

public class DataOutputStream {
	
	private int ptr;
	private byte[] data;
	
	public DataOutputStream(int size)
	{
		this.data = new byte[size];
		this.ptr = 0;
	}
	
	public DataOutputStream()
	{
		this(1024);
	}
	
	public byte[] getByteArray()
	{
		byte[] ret = new byte[ptr];
		for(int i = 0; i < ret.length; i++)
		{
			ret[i] = data[i];
		}
		return ret;
	}
	
	private void expand()
	{
		byte[] newArray = new byte[data.length * 2];
		for(int i = 0; i < data.length; i++)
		{
			newArray[i] = data[i];
		}
		data = newArray;
	}
	
	public void writeByte(byte b)
	{
		if(ptr >= data.length)
		{
			expand();
		}
		data[ptr] = b;
		ptr++;
	}
	
	public void writeInt(int v)
	{
		for(int i = 0; i < 4; i++)
		{
			writeByte((byte)(v & 0xff));
			v = v >>> 8;
		}
	}
	
	public void writeDouble(double d)
	{
		writeString("" + d);
	}
	
	public void writeString(String string)
	{
		byte[] b = string.getBytes(StandardCharsets.UTF_8);
		writeInt(b.length);
		for(int i = 0; i < b.length; i++)
		{
			writeByte(b[i]);
		}
	}
	
	public void writeBlock(Block block)
	{
		writeInt(block.getLegacyID());
		block.write(this);
	}
	
	public void writeItem(Item item)
	{
		if(item == null)
		{
			writeString("null");
			return;
		}
		
		writeString(item.getName());
		item.write(this);
	}
	
	public void writeEntity(Entity entity)
	{
		writeString(entity.getType());
		entity.write(this);
	}
	
	public void writeEntities(Collection<Entity> entities)
	{
		writeInt(entities.size());
		for(Entity entity : entities)
		{
			writeEntity(entity);
		}
	}
}
