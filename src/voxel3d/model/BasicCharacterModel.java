package voxel3d.model;

import voxel3d.graphics.GeometryUtility;
import voxel3d.graphics.Texture;
import voxel3d.utility.Transform3d;
import voxel3d.utility.Vector3d;

public class BasicCharacterModel {
	
	public double rightArmSwingForward = 0;
	public double leftArmSwingForward = 0;

	public double rightlegSwingForward = 0;
	public double leftlegSwingForward = 0;

	public double bodySwingForward = 0;
	public double headSwingForward = 0;
	
	public Vector3d forward = new Vector3d(0,0,1);
	private Vector3d right = new Vector3d(1,0,0);
	private Vector3d up = new Vector3d(0,1,0);
	public Vector3d position = new Vector3d(0,0,0);
	
	private static final float uvPixelSize = 1f / 64f;
	private static final float[][][] basicCharacterHeadUV = GeometryUtility.generateBoxUV(uvPixelSize * 0f, uvPixelSize * 0f, uvPixelSize * 8f, uvPixelSize * 8f, uvPixelSize * 8f);
	private static final float[][][] basicCharacterBodyUV = GeometryUtility.generateBoxUV(uvPixelSize * 16f, uvPixelSize * 16f, uvPixelSize * 8f, uvPixelSize * 12f, uvPixelSize * 4f);
	private static final float[][][] basicCharacterArmUV = GeometryUtility.generateBoxUV(uvPixelSize * 40f, uvPixelSize * 16f, uvPixelSize * 4f, uvPixelSize * 12f, uvPixelSize * 4f);
	private static final float[][][] basicCharacterLegUV = GeometryUtility.generateBoxUV(uvPixelSize * 0f, uvPixelSize * 16f, uvPixelSize * 4f, uvPixelSize * 12f, uvPixelSize * 4f);
	
	private static final double pixelSize = 2d/32d;
	private static final double extremidyHeight = 12d * pixelSize;
	private static final double extremidyWidth = 4d * pixelSize;
	private static final double bodyHeight = 12d * pixelSize;
	private static final double bodyWidth = 8d * pixelSize;
	private static final double headSize = 8d * pixelSize;
	
	
	private final Transform3d root;
	private final Transform3d bodyPivot;
	private final Transform3d bodyTransform;
	private final Transform3d headPivot;
	private final Transform3d headTransform;
	private final Transform3d rightLegPivot;
	private final Transform3d rightLegTransform;
	private final Transform3d leftLegPivot;
	private final Transform3d leftLegTransform;
	private final Transform3d rightArmPivot;
	private final Transform3d rightArmTransform;
	private final Transform3d leftArmPivot;
	private final Transform3d leftArmTransform;
	
	public BasicCharacterModel()
	{
		root = new Transform3d(null);
		
		bodyPivot = new Transform3d(root);
		bodyPivot.position.set(0, extremidyHeight, 0);
		
		headPivot = new Transform3d(bodyPivot);
		headPivot.position.set(0, bodyHeight, 0);
		
		rightArmPivot = new Transform3d(bodyPivot);
		rightArmPivot.position.set(bodyWidth * 0.5 + extremidyWidth * 0.5, extremidyHeight, 0);
		
		leftArmPivot = new Transform3d(bodyPivot);
		leftArmPivot.position.set(-bodyWidth * 0.5 + -extremidyWidth * 0.5, extremidyHeight, 0);
		
		rightLegPivot = new Transform3d(root);
		rightLegPivot.position.set(extremidyWidth * 0.5, extremidyHeight, 0);
		
		leftLegPivot = new Transform3d(root);
		leftLegPivot.position.set(-extremidyWidth * 0.5, extremidyHeight, 0);
		
		bodyTransform = new Transform3d(bodyPivot);
		bodyTransform.position.set(0, extremidyHeight * 0.5, 0);
		bodyTransform.right.set(bodyWidth, 0, 0);
		bodyTransform.up.set(0, bodyHeight, 0);
		bodyTransform.forward.set(0, 0, extremidyWidth);
		
		headTransform = new Transform3d(headPivot);
		headTransform.position.set(0, headSize * 0.5, 0);
		headTransform.right.set(headSize, 0, 0);
		headTransform.up.set(0, headSize, 0);
		headTransform.forward.set(0, 0, headSize);
		
		rightLegTransform = new Transform3d(rightLegPivot);
		rightLegTransform.position.set(0, -extremidyHeight * 0.5, 0);
		rightLegTransform.right.set(extremidyWidth, 0, 0);
		rightLegTransform.up.set(0, extremidyHeight, 0);
		rightLegTransform.forward.set(0, 0, extremidyWidth);
		
		leftLegTransform = new Transform3d(leftLegPivot);
		leftLegTransform.position.set(0, -extremidyHeight * 0.5, 0);
		leftLegTransform.right.set(extremidyWidth, 0, 0);
		leftLegTransform.up.set(0, extremidyHeight, 0);
		leftLegTransform.forward.set(0, 0, extremidyWidth);
		
		rightArmTransform = new Transform3d(rightArmPivot);
		rightArmTransform.position.set(0, -extremidyHeight * 0.5, 0);
		rightArmTransform.right.set(extremidyWidth, 0, 0);
		rightArmTransform.up.set(0, extremidyHeight, 0);
		rightArmTransform.forward.set(0, 0, extremidyWidth);
		
		leftArmTransform = new Transform3d(leftArmPivot);
		leftArmTransform.position.set(0, -extremidyHeight * 0.5, 0);
		leftArmTransform.right.set(extremidyWidth, 0, 0);
		leftArmTransform.up.set(0, extremidyHeight, 0);
		leftArmTransform.forward.set(0, 0, extremidyWidth);
		
	}
	
	public double t = 0;
	public double a = 0;
	
	public void resolveTransform()
	{
		right.setCross(up, forward);
		
		double angle = Math.sin(t * Math.PI * 2.0) * a * 2.0;
		double angleHead = Math.sin(t* Math.PI * 2.0 * 2.0) * a;
		
		root.position.set(position);
		root.right = right;
		root.up = up;
		root.forward = forward;
		setTransformAxisRotation(rightArmPivot, angle);
		setTransformAxisRotation(leftArmPivot, -angle);
		
		setTransformAxisRotation(rightLegPivot, -angle);
		setTransformAxisRotation(leftLegPivot, angle);
		
		setTransformAxisRotation(headPivot, angleHead);
		
		root.resolveGlobal(null);
	}
	
	public void render(Texture tex)
	{
		drawTransformBox(bodyTransform, basicCharacterBodyUV, tex);
		drawTransformBox(headTransform, basicCharacterHeadUV, tex);
		
		drawTransformBox(rightArmTransform, basicCharacterArmUV, tex);
		drawTransformBox(leftArmTransform, basicCharacterArmUV, tex);
		
		drawTransformBox(rightLegTransform, basicCharacterLegUV, tex);
		drawTransformBox(leftLegTransform, basicCharacterLegUV, tex);
	}
	
	private void setTransformAxisRotation(Transform3d target, double angle)
	{
		angle = Math.toRadians(angle);
		target.forward.set(0, Math.sin(angle), Math.cos(angle));
		target.up.set(0, Math.cos(angle), -Math.sin(angle));
	}
	
	private void drawTransformBox(Transform3d transform, float[][][] uv, Texture tex)
	{
		GeometryUtility.drawBox(transform.globalPosition, transform.globalRight, transform.globalUp, transform.globalForward, uv, GeometryUtility.boxMap, tex);
	}

}
