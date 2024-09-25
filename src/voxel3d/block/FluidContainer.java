package voxel3d.block;

import voxel3d.fluid.Fluid;
import voxel3d.global.Debug;

public class FluidContainer {
	
	public double size;
	public final double[] elements;
	private final Fluid[] fluids;
	
	public FluidContainer(double size)
	{
		this.size = size;
		fluids = Fluid.getFluids();
		elements = new double[fluids.length];
	}
	
	public void equalize(FluidContainer other, double deltaTime)
	{
		double totalSize = size + other.size;
		
		for(int i = 0; i < elements.length; i++)
		{
			double total = other.elements[i] + elements[i];
			double s = total * (size / totalSize);
			
			double deltaMass = elements[i] - s;
			deltaMass *= 1.0 / 16.0;
			
			other.elements[i] += deltaMass;
			
			elements[i] -= deltaMass;
		}
	}
	
	public double getMass()
	{
		double totalMass = 0;
		for(int i = 0; i < elements.length; i++)
		{
			totalMass += elements[i];
		}
		return totalMass;
	}
	
	
	
	public void clear()
	{
		for(int i = 0; i < elements.length; i++)
		{
			elements[i] = 0;
		}
	}
	
	public String getInfo()
	{
		String ret = "\n";
		for(int i = 0; i < fluids.length; i++)
		{
			ret += fluids[i].getName() + " " + String.format("%.3f",elements[i]) + "\n";
		}
		ret += "Mass:" + String.format("%.3f",getMass()) + "\n";
		ret += "Size:" + String.format("%.3f",size) + "\n";
		return ret;
	}
	
	public void addFluid(Fluid fluid, double amount)
	{
		for(int i = 0; i < fluids.length; i++)
		{
			if(fluids[i] == fluid)
			{
				elements[i] += amount;
				return;
			}
		}
		Debug.err("No fluid in container of name: " + fluid.getName());
	}
	
	public double getFluid(Fluid fluid)
	{
		for(int i = 0; i < fluids.length; i++)
		{
			if(fluids[i] == fluid)
			{
				return elements[i];
			}
		}
		Debug.err("No fluid in container of name: " + fluid.getName());
		return 0;
	}
	
}
