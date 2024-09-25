package voxel3d.data;

public class KeyValuePair {
	
	private String s;
	
	public KeyValuePair(String s)
	{
		this.s = s;
	}
	
	public KeyValuePair(String key, String value)
	{
		this.s = key + "=" + value;
	}
	
	public String getKey()
	{
		return s.split("=")[0];
	}
	
	public String getValue()
	{
		return s.split("=")[1];
	}
	
	public String getString()
	{
		return s;
	}

}
