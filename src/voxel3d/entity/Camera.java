package voxel3d.entity;

import voxel3d.global.*;
import voxel3d.utility.Vector3d;

public class Camera {
	
	public double fov;
	
	public Vector3d position = new Vector3d();
	public Vector3d forward = new Vector3d();
	public Vector3d up = new Vector3d();
	
	private float nearClippingPlane = 0.1f;
	private float farClippingPlane = 500;
	
	public Camera(double fov)
	{
		this.fov = fov;
		forward.set(0, 0, 1);
		forward.set(0, 1, 0);
	}
	
	public void getMatrix(double offsetX, double offsetY, double offsetZ, float[] projection, float[] view)
	{
		Vector3d pos = new Vector3d();
		pos.set(position.x + offsetX, position.y + offsetY, position.z + offsetZ);
		
		for(int i = 0; i < 16; i++)
		{
			projection[i] = 0;
			view[i] = 0;
		}
		
		float ymax, xmax;
	    ymax = (float) (nearClippingPlane * Math.tan(fov * Math.PI / 360.0));
	    xmax = (float) (ymax * (Objects.window.getAspectRatio()));
	    
	    float twoZNear, deltaW, deltaH, deltaZ;
	    twoZNear = (float) (2.0 * nearClippingPlane);
	    deltaW = xmax * 2;
	    deltaH = ymax * 2;
	    deltaZ = (float) (farClippingPlane - nearClippingPlane);
	   
	    projection[0] = twoZNear / deltaW;
	    projection[1] = 0;
	    projection[2] = 0;
	    projection[3] = 0;
	    
	    projection[4] = 0;
	    projection[5] = twoZNear / deltaH;
	    projection[6] = 0;
	    projection[7] = 0;
	    
	    projection[8]  = (0 + 0) / deltaW;
	    projection[9]  = (0 + 0) / deltaH;
	    projection[10] = (-farClippingPlane - nearClippingPlane) / deltaZ;
	    projection[11] = -1;
	    
	    projection[12] = 0;
	    projection[13] = 0;
	    projection[14] = (-twoZNear * farClippingPlane) / deltaZ;
	    projection[15] = 0;
		
	    //inverted view matrix? might not be correct, janky math
	    view[0]  = (float) (up.y*forward.z - up.z*forward.y);
	    view[4]  = (float) (up.z*forward.x - up.x*forward.z);
	    view[8]  = (float) (up.x*forward.y - up.y*forward.x);
	    view[12] = (float) ((view[0] ) * -pos.x + (view[4]) * -pos.y + (view[8]) * -pos.z);
	    
	    view[1]  = (float) up.x;
	    view[5]  = (float) up.y;
	    view[9]  = (float) up.z;
	    view[13] = (float) -(up.x * pos.x + up.y * pos.y + up.z * pos.z);
	    
	    view[2]  = (float) -forward.x;
	    view[6]  = (float) -forward.y; 
	    view[10] = (float) -forward.z; 
	    view[14] = (float) (forward.x * pos.x + forward.y * pos.y + forward.z * pos.z); 
	    
	    view[15] = 1;
	}
}
