package voxel3d.model;

import voxel3d.graphics.GeometryUtility;
import voxel3d.graphics.Texture;
import voxel3d.utility.Transform3d;
import voxel3d.utility.Vector3d;

public class DeerModel {
	
	
	private static final float uvPixelSize = 1f / 64f;
	private static final float[][][] headUV = GeometryUtility.generateBoxUV(uvPixelSize * 28f, uvPixelSize * 31f, uvPixelSize * 4f, uvPixelSize * 6f, uvPixelSize * 6f);
	private static final float[][][] neckUV = GeometryUtility.generateBoxUV(uvPixelSize * 12f, uvPixelSize * 31f, uvPixelSize * 3f, uvPixelSize * 8f, uvPixelSize * 4f);
	private static final float[][][] bodyUV = GeometryUtility.generateBoxUV(uvPixelSize * 0f, uvPixelSize * 0f, uvPixelSize * 6f, uvPixelSize * 8f, uvPixelSize * 18f);
	private static final float[][][] noseUV = GeometryUtility.generateBoxUV(uvPixelSize * 50f, uvPixelSize * 37f, uvPixelSize * 3f, uvPixelSize * 3f, uvPixelSize * 3f);
	private static final float[][][] legUV = GeometryUtility.generateBoxUV(uvPixelSize * 0f, uvPixelSize * 28f, uvPixelSize * 2f, uvPixelSize * 12f, uvPixelSize * 3f);
	
	
	private static final double pixelSize = 2d/32d;
	private static final double legHeight = pixelSize * 12d;
	private static final double bodyHeight = pixelSize * 8d;
	private static final double bodyLenght = pixelSize * 18d;
	
	
	public Vector3d forward = new Vector3d(0,0,1);
	private Vector3d right = new Vector3d(1,0,0);
	private Vector3d up = new Vector3d(0,1,0);
	public Vector3d position = new Vector3d(0,0,0);
	
	
	private final Transform3d root;
	private final Transform3d bodyPivot;
	private final Transform3d bodyTransform;
	private final Transform3d neckPivot;
	private final Transform3d neckTransform;
	private final Transform3d headPivot;
	private final Transform3d headTransform;
	private final Transform3d noseTransform;
	
	private final Transform3d rightFrontLegPivot;
	private final Transform3d rightFrontLegTransform;
	private final Transform3d leftFrontLegPivot;
	private final Transform3d leftFrontLegTransform;
	private final Transform3d rightBackLegPivot;
	private final Transform3d rightBackLegTransform;
	private final Transform3d leftBackLegPivot;
	private final Transform3d leftBackLegTransform;
	
	
	public DeerModel()
	{
		root = new Transform3d(null);
		
		bodyPivot = new Transform3d(root);
		bodyPivot.position.set(0, legHeight, 0);
		
		neckPivot = new Transform3d(bodyPivot);
		neckPivot.position.set(0, bodyHeight, bodyLenght/2d);
		
		headPivot = new Transform3d(neckPivot);
		headPivot.position.set(0, pixelSize * 2d, 0);
		
		rightFrontLegPivot = new Transform3d(bodyPivot);
		rightFrontLegPivot.position.set(pixelSize * (3d - 1d), 0, pixelSize * (9d - 1.5d));
		
		leftFrontLegPivot = new Transform3d(bodyPivot);
		leftFrontLegPivot.position.set(pixelSize * -(3d - 1d), 0, pixelSize * (9d - 1.5d));
		
		rightBackLegPivot = new Transform3d(bodyPivot);
		rightBackLegPivot.position.set(pixelSize * (3d - 1d), 0, pixelSize * -(9d - 1.5d));
		
		leftBackLegPivot = new Transform3d(bodyPivot);
		leftBackLegPivot.position.set(pixelSize * -(3d - 1d), 0, pixelSize * -(9d - 1.5d));
		
		
		bodyTransform = new Transform3d(bodyPivot);
		bodyTransform.position.set(0, bodyHeight/2d, 0);
		bodyTransform.right.multiply(pixelSize * 6d);
		bodyTransform.up.multiply(bodyHeight);
		bodyTransform.forward.multiply(bodyLenght);
		
		rightFrontLegTransform = new Transform3d(rightFrontLegPivot);
		rightFrontLegTransform.position.set(0, pixelSize * -6d, 0);
		rightFrontLegTransform.right.multiply(pixelSize * 2d);
		rightFrontLegTransform.up.multiply(pixelSize * 12d);
		rightFrontLegTransform.forward.multiply(pixelSize * 3d);
		
		leftFrontLegTransform = new Transform3d(leftFrontLegPivot);
		leftFrontLegTransform.position.set(0, pixelSize * -6d, 0);
		leftFrontLegTransform.right.multiply(pixelSize * 2d);
		leftFrontLegTransform.up.multiply(pixelSize * 12d);
		leftFrontLegTransform.forward.multiply(pixelSize * 3d);
		
		rightBackLegTransform = new Transform3d(rightBackLegPivot);
		rightBackLegTransform.position.set(0, pixelSize * -6d, 0);
		rightBackLegTransform.right.multiply(pixelSize * 2d);
		rightBackLegTransform.up.multiply(pixelSize * 12d);
		rightBackLegTransform.forward.multiply(pixelSize * 3d);
		
		leftBackLegTransform = new Transform3d(leftBackLegPivot);
		leftBackLegTransform.position.set(0, pixelSize * -6d, 0);
		leftBackLegTransform.right.multiply(pixelSize * 2d);
		leftBackLegTransform.up.multiply(pixelSize * 12d);
		leftBackLegTransform.forward.multiply(pixelSize * 3d);
		
		double co = Math.cos(Math.toRadians(30d));
		double si = Math.sin(Math.toRadians(30d));
		neckTransform = new Transform3d(neckPivot);
		neckTransform.position.set(0, pixelSize * 1d, 0);
		neckTransform.right.multiply(pixelSize * 3d);
		neckTransform.up.set(0, co, si);
		neckTransform.up.multiply(pixelSize * 8d);
		neckTransform.forward.set(0, -si, co);
		neckTransform.forward.multiply(pixelSize * 4d);
		
		headTransform = new Transform3d(headPivot);
		headTransform.position.set(0, pixelSize * 3d, pixelSize * 3d);
		headTransform.right.multiply(pixelSize * 4d);
		headTransform.up.multiply(pixelSize * 6d);
		headTransform.forward.multiply(pixelSize * 6d);
		
		noseTransform = new Transform3d(headPivot);
		noseTransform.position.set(0, pixelSize * 1.5d, pixelSize * 7.5d);
		noseTransform.right.multiply(pixelSize * 3d);
		noseTransform.up.multiply(pixelSize * 3d);
		noseTransform.forward.multiply(pixelSize * 3d);
		
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
		
		setTransformAxisRotation(rightFrontLegPivot, angle);
		setTransformAxisRotation(leftFrontLegPivot, -angle);
		
		setTransformAxisRotation(rightBackLegPivot, -angle);
		setTransformAxisRotation(leftBackLegPivot, angle);
		
		setTransformAxisRotation(headPivot, angleHead);
		
		root.resolveGlobal(null);
	}
	
	public void render(Texture tex)
	{
		drawTransformBox(bodyTransform, bodyUV, tex);
		drawTransformBox(rightFrontLegTransform, legUV, tex);
		drawTransformBox(leftFrontLegTransform, legUV, tex);
		drawTransformBox(rightBackLegTransform, legUV, tex);
		drawTransformBox(leftBackLegTransform, legUV, tex);
		drawTransformBox(neckTransform, neckUV, tex);
		drawTransformBox(headTransform, headUV, tex);
		drawTransformBox(noseTransform, noseUV, tex);
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
