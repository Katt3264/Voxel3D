package voxel3d.utility;

public class TextUtill {
	
	//private static final String[] units = {"q", "r", "y", "z", "a", "f", "p", "n", "u", "m", " ", "K", "M", "G", "T", "P", "E", "Z", "Y", "R", "Q"};
	private static final String[] units = {
			"quecto", "ronto", "yocto", "zepto", "atto", "femto", "pico", "nano", "micro", "milli", 
			" ", 
			"Kilo", "Mega", "Giga", "Tera", "Peta", "Exa", "Zetta", "Yotta", "Ronna", "Quetta"};
	
	public static String toUnit(double v)
	{
		double unit = v / 1E-30;
		String s = units[units.length-1];
		
		for(int i = 0; i < units.length; i++)
		{
			if(unit < 1000)
			{
				s = units[i];
				break;
			}
			unit = unit / 1000;
		}
		
		if(unit < 1)
		{
			//return "000,0";
			return String.format("%.4G ", v);
		}
		else if(unit < 1000)
		{
			return String.format("%.4G ", unit) + s;
		}
		else
		{
			return ((long)(unit)) + " " + s;
		}

	}

}
