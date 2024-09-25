package voxel3d.global;

public class Timer {
	
	private long[] records;
	private int entry = 0;
	
	private long start;
	
	public Timer(int size)
	{
		 records = new long[size];
	}
	
	public void start()
	{
		start = System.nanoTime();
	}
	
	public void stop()
	{
		records[entry] = System.nanoTime() - start;
		entry = (entry + 1) % records.length;
	}
	
	public long getAverage()
	{
		long avg = 0;
		for(int i = 0; i < records.length; i++)
		{
			avg += records[i] / records.length;
		}
		return avg;
	}
	
	public long getWorst()
	{
		long avg = 0;
		for(int i = 0; i < records.length; i++)
		{
			avg = Math.max(avg, records[i]);
		}
		return avg;
	}
	

}
