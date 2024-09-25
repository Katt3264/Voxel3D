package voxel3d.utility;

import voxel3d.global.Objects;

public class FloatList {
	
	private int ptr = 0;
	private float[] values;
	
	public FloatList()
	{
		values = Objects.floatArrayPool.get();
	}
	
	public void free()
	{
		Objects.floatArrayPool.free(values);
		values = null;
	}
	
	public void add(float value)
	{
		values[ptr] = value;
		ptr++;
		
		if(ptr == values.length)
		{
			expand();
		}
	}
	
	private void expand()
	{
		float[] newValues = new float[ptr*2];
		for(int i = 0; i < ptr; i++)
		{
			newValues[i] = values[i];
		}
		values = newValues;
	}
	
	/*public float[] getAll()
	{
		float[] ret = new float[ptr];
		
		for(int i = 0; i < ret.length; i++)
		{
			ret[i] = values[i];
		}
		
		return ret;
	}*/
	
	public float[] getRaw()
	{		
		return values;
	}
	
	public int size()
	{
		return ptr;
	}
	
	
}
