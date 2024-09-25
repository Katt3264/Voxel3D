package voxel3d.utility;

public class Resolver<T> {

	private Resolvable<T> resolvable;
	private T value;
	
	public Resolver(Resolvable<T> resolvable)
	{
		this.resolvable = resolvable;
	}
	
	/*public Resolver(T value)
	{
		this.value = value;
	}*/
	
	public void setResolvable(Resolvable<T> resolvable)
	{
		this.resolvable = resolvable;
	}
	
	public T get()
	{
		if(resolvable != null)
		{
			T newT = resolvable.checkResolved();
			if(newT != null)
			{
				resolvable = null;
				value = newT;
			}
		}
		return value;
	}
	
}
