package voxel3d.utility;

import static org.lwjgl.opengl.GL20.*;

import voxel3d.graphics.Texture;

public class GeometryUtility {
	
	private static final Vector3d right = new Vector3d();
	private static final Vector3d left = new Vector3d();
	private static final Vector3d up = new Vector3d();
	private static final Vector3d down = new Vector3d();
	private static final Vector3d forward = new Vector3d();
	private static final Vector3d back = new Vector3d();
	
	static {
		right.set(1, 0, 0);
		left.set(-1, 0, 0);
		up.set(0, 1, 0);
		down.set(0, -1, 0);
		forward.set(0, 0, 1);
		back.set(0, 0, -1);
	}
	
	
	
	
	
	public static final float[][] uv = new float[][]
	{
		{1f,1f},
		{1f,0f},
		{0f,0f},
		{0f,1f}
	};
	
	public static final double[][][] boxMap = new double[][][]
	{
		generateFace(right, back, up),
		generateFace(left, forward, up),
		
		generateFace(up, right, back),
		generateFace(down, right, forward),
		
		generateFace(forward, right, up),
		generateFace(back, left, up),
		
	};
	
	public static final double[][][] skyBoxMap = new double[][][]
	{
		generateFace(left, back, up),
		generateFace(right, forward, up),
		
		generateFace(down, left, forward),
		generateFace(up, left, back),
		
		generateFace(back, right, up),
		generateFace(forward, left, up),
		
	};
	
	public static final float[][][] skyBoxMapUV = new float[][][]
	{
		generateFaceUV(0,1f/2f,1f/3f,1f/2f),		// left
		generateFaceUV(2f/3f,1f/2f,1f/3f,1f/2f),		// right
		
		generateFaceUV(0,0,1f/3f,1f/2f),     // y-
		generateFaceUV(1f/3f,0,1f/3f,1f/2f), // y+
		
		generateFaceUV(2f/3f,0,1f/3f,1f/2f),		// centre back
		generateFaceUV(1f/3f,1f/2f,1f/3f,1f/2f),	// centre forward
				
	};
	
	public static void drawBox(Vector3d position, Vector3d x, Vector3d y, Vector3d z, Texture tex)
	{
		drawBox(position, x, y, z, new Texture[] {tex,tex,tex,tex,tex,tex});
	}
	
	public static void drawBox(Vector3d position, Vector3d x, Vector3d y, Vector3d z, Texture[] texs)
	{
		//glColor3f(1f, 1f, 1f);
		for(int f = 0; f < 6; f++)
		{
			texs[f].glBind();
			glBegin(GL_QUADS);
			for(int v = 0; v < 4; v++)
			{
				double xp = position.x + (boxMap[f][v][0] * x.x + boxMap[f][v][1] * y.x + boxMap[f][v][2] * z.x);
				double yp = position.y + (boxMap[f][v][0] * x.y + boxMap[f][v][1] * y.y + boxMap[f][v][2] * z.y);
				double zp = position.z + (boxMap[f][v][0] * x.z + boxMap[f][v][1] * y.z + boxMap[f][v][2] * z.z);
				
				glTexCoord2f(uv[v][0], uv[v][1]);
				glVertex3d(xp, yp, zp);
			}
			glEnd();
			texs[f].glUnbind();
		}
		
	}
	
	public static void drawBox(Vector3d position, Vector3d x, Vector3d y, Vector3d z, float[][][] uvs, double[][][] vertices, Texture tex)
	{
		tex.glBind();
		for(int f = 0; f < 6; f++)
		{
			glBegin(GL_QUADS);
			for(int v = 0; v < 4; v++)
			{
				double xp = position.x + (vertices[f][v][0] * x.x + vertices[f][v][1] * y.x + vertices[f][v][2] * z.x);
				double yp = position.y + (vertices[f][v][0] * x.y + vertices[f][v][1] * y.y + vertices[f][v][2] * z.y);
				double zp = position.z + (vertices[f][v][0] * x.z + vertices[f][v][1] * y.z + vertices[f][v][2] * z.z);
				
				glTexCoord2f(uvs[f][v][0], uvs[f][v][1]);
				glVertex3d(xp, yp, zp);
			}
			glEnd();
			
		}
		tex.glUnbind();
	}
	
	private static final float vertexEpsilon = 1.0e-6f;
	private static double[][] generateFace(Vector3d position, Vector3d right, Vector3d up)
	{
		double[][] vertices = new double[4][3];
		
		double[][] biases = new double[][]
		{
			{-1f-vertexEpsilon,-1f-vertexEpsilon},
			{-1f-vertexEpsilon,1f+vertexEpsilon},
			{1f+vertexEpsilon,1f+vertexEpsilon},
			{1f+vertexEpsilon,-1f-vertexEpsilon}
		};
		
		for(int i = 0; i < biases.length; i++)
		{
			double xp = (position.x + biases[i][0] * right.x + biases[i][1] * up.x)*0.5;
			double yp = (position.y + biases[i][0] * right.y + biases[i][1] * up.y)*0.5;
			double zp = (position.z + biases[i][0] * right.z + biases[i][1] * up.z)*0.5;
			vertices[i][0] = xp;
			vertices[i][1] = yp;
			vertices[i][2] = zp;
		}
		return vertices;
	}
	
	/*private static double[][][] generateBox(double sizeX, double sizeY, double sizeZ)
	{
		sizeX *= 0.5;
		sizeY *= 0.5;
		sizeZ *= 0.5;
		double[][] front = generateFace(x+sizeZ, y+sizeZ, sizeX, sizeY);
		double[][] back = generateFaceUV(x+sizeZ+sizeX+sizeZ, y+sizeZ, sizeX, sizeY);
		double[][] right = generateFaceUV(x, y+sizeZ, sizeZ, sizeY);
		double[][] left = generateFaceUV(x+sizeZ+sizeX, y+sizeZ, sizeZ, sizeY);
		double[][] up = generateFaceUV(x+sizeZ, y, sizeX, sizeZ);
		double[][] down = generateFaceUV(x+sizeZ+sizeX, y, sizeX, sizeZ);
		double[][][] vertices = new double[][][]
		{
			right,
			left,
			up,
			down,
			front,
			back
		};
		return vertices;
	}*/
	
	private static final float uvEpsilon = 1.0e-3f;
	private static float[][] generateFaceUV(float x, float y, float sizeX, float sizeY)
	{
		return new float[][] 
		{
			{x+sizeX-uvEpsilon, y+sizeY-uvEpsilon},
			{x+sizeX-uvEpsilon, y+uvEpsilon},
			{x+uvEpsilon, y+uvEpsilon},
			{x+uvEpsilon, y+sizeY-uvEpsilon}
		};
	}
	
	public static float[][][] generateBoxUV(float x, float y, float sizeX, float sizeY, float sizeZ)
	{
		float[][] front = generateFaceUV(x+sizeZ, y+sizeZ, sizeX, sizeY);
		float[][] back = generateFaceUV(x+sizeZ+sizeX+sizeZ, y+sizeZ, sizeX, sizeY);
		float[][] right = generateFaceUV(x, y+sizeZ, sizeZ, sizeY);
		float[][] left = generateFaceUV(x+sizeZ+sizeX, y+sizeZ, sizeZ, sizeY);
		float[][] up = generateFaceUV(x+sizeZ, y, sizeX, sizeZ);
		float[][] down = generateFaceUV(x+sizeZ+sizeX, y, sizeX, sizeZ);
		float[][][] uvs = new float[][][]
		{
			right,
			left,
			up,
			down,
			front,
			back
		};
		return uvs;
	}

}
