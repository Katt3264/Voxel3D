package voxel3d.utility;

import java.util.ArrayList;
import java.util.Collection;

public class Transform3d {
	
	private Collection<Transform3d> children = new ArrayList<Transform3d>();
	
	public Vector3d right = new Vector3d(1, 0, 0);
	public Vector3d up = new Vector3d(0, 1, 0);
	public Vector3d forward = new Vector3d(0, 0, 1);
	public Vector3d position = new Vector3d(0, 0, 0);
	
	
	public Vector3d globalRight = new Vector3d(1, 0, 0);
	public Vector3d globalUp = new Vector3d(0, 1, 0);
	public Vector3d globalForward = new Vector3d(0, 0, 1);
	public Vector3d globalPosition = new Vector3d(0, 0, 0);

	public Transform3d(Transform3d parent)
	{
		if(parent == null)
			return;
		
		parent.children.add(this);
	}
	
	public void resolveGlobal(Transform3d parent)
	{
		if(parent == null)
		{
			globalRight.set(right);
			globalUp.set(up);
			globalForward.set(forward);
			globalPosition.set(position);
		}
		else
		{
			transformVector(position, globalPosition, parent.globalRight, parent.globalUp, parent.globalForward);
			globalPosition.add(parent.globalPosition);
			
			transformVector(right, globalRight, parent.globalRight, parent.globalUp, parent.globalForward);
			transformVector(up, globalUp, parent.globalRight, parent.globalUp, parent.globalForward);
			transformVector(forward, globalForward, parent.globalRight, parent.globalUp, parent.globalForward);
		}
		
		for(Transform3d t : children)
		{
			t.resolveGlobal(this);
		}
	}
	
	private void transformVector(Vector3d source, Vector3d target, Vector3d right, Vector3d up, Vector3d forward)
	{
		target.x = source.x * right.x + source.y * up.x + source.z * forward.x;
		target.y = source.x * right.y + source.y * up.y + source.z * forward.y;
		target.z = source.x * right.z + source.y * up.z + source.z * forward.z;
	}
}
