package voxel3d;


public class Launcher {
	
	public static void main(String[] args) throws Exception
	{
		//String command = "java -XX:+UseShenandoahGC -XstartOnFirstThread -classpath library/*:bin/ voxel3d/Main";
		String command = "java -XX:+UseG1GC -XstartOnFirstThread -classpath library/*:bin/ voxel3d/Main";
		try {

			Runtime.getRuntime().exec(command);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//JOptionPane.showMessageDialog(null,e.getMessage(), "ERROR",1);
			e.printStackTrace();
		}
	}

}
