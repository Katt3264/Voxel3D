package voxel3d.utility;

import voxel3d.global.Settings;

public class FloatArrayPool {
	
	private float[][] buff = new float[Settings.floatPoolBufferSize][];
	private int top = -1;
	
	
	public float[] get()
	{
		float[] data = null;
		
		synchronized(buff)
		{
			if(top != -1)
			{
				data = buff[top];
				buff[top] = null;
				top--;
			}
		}
		
		if(data == null)
		{
			data = new float[Settings.floatPoolArraySize];
		}
		else
		{
			//no need to clean this array
		}
		
		return data;
	}
	
	public void free(float[] data)
	{
		if(data == null)
			return;
		
		//System.out.println("free - " + (top + 1));
		synchronized(buff)
		{
			if(top + 1 < buff.length)
			{
				buff[top + 1] = data;
				top++;
				return;
			}
		}
		//System.out.println("FloatArrayPool could not free buffer");
	}

}
