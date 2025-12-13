package voxel3d.graphics;

import voxel3d.entity.Camera;
import voxel3d.entity.Entity;
import voxel3d.global.Objects;
import voxel3d.global.Settings;
import voxel3d.utility.Color;
import voxel3d.utility.Vector3d;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL20.*;

public class GraphicsWrapper {
	
	public static Window window;
	
	public static void init()
	{
		window = new Window("Game", 1080, 720);
	}
	
	public static void startFrame()
	{
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glClearColor(0, 0, 1, 1.0f); // Blue background should never be visible
	}
	
	//TODO: implement timers, avoid bussy waiting
	public static void endFrame()
	{
		glFlush();
		glFinish();
		
		glfwPollEvents();
		glfwSwapBuffers(window.getID());
		//shader.unbind();
	}
	
	//TODO: use custom shader
	public static void setRenderModeGUI()
	{
		glDisable(GL_CULL_FACE);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glLoadMatrixd(window.getNormalMatrix());
		
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		
		glDisable(GL_DEPTH_TEST);
		glDisable(GL_ALPHA_TEST);
	}
	
	
	//TODO: camera should not be required, use custom skybox shader
	public static void setRenderModeSkybox(Camera camera)
	{
		float[] perspective = new float[4*4];
		float[] view = new float[4*4];
		
		glDisable(GL_DEPTH_TEST);
        glDisable(GL_ALPHA_TEST);
		camera.getMatrix(-camera.position.x, -camera.position.y, -camera.position.z, perspective, view);
		//glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glMatrixMode(GL_PROJECTION);
		glLoadMatrixf(perspective);
		glMatrixMode(GL_MODELVIEW);
		glLoadMatrixf(view);
		glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
	}
	
	public static void renderSkybox(Vector3d x, Vector3d y, Vector3d z, Color color, float alpha, Texture texture)
	{
		glColor4f(color.r,color.g,color.b,alpha);
		GeometryUtility.drawBox(
				new Vector3d(0,0,0), x, y, z, 
				GeometryUtility.skyBoxMapUV,
				GeometryUtility.skyBoxMap,
				texture);
	}
	
	
	public static void setRenderModeVoxelChunk(Camera camera, int ox, int oy, int oz, Color skyColor, Texture atlas)
	{
		Shader shader = Objects.voxelTerrainShader;
		
		glEnable(GL_DEPTH_TEST);
    	glDepthRange(0, 1);
        glDepthFunc(GL_LEQUAL);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    	glEnable(GL_ALPHA_TEST);
    	glAlphaFunc(GL_GREATER, 0.5f);
    	glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
		
        float[] perspective = new float[4*4];
		float[] view = new float[4*4];
        
    	camera.getMatrix(-ox, -oy, -oz, perspective, view);
    	shader.bind();
        glUniformMatrix4fv(glGetUniformLocation(shader.getProgramId(), "projectionMatrix"), false, perspective);
        glUniformMatrix4fv(glGetUniformLocation(shader.getProgramId(), "viewMatrix"), false, view);
        
        glUniform3f(glGetUniformLocation(shader.getProgramId(), "skyColor"), skyColor.r, skyColor.g, skyColor.b);
        glUniform1f(glGetUniformLocation(shader.getProgramId(), "brightness"), Settings.brightness);
		
		atlas.glBind();
	}
	
	public static void RenderVoxelChunk(int x, int y, int z, Mesh mesh)
	{
		Shader shader = Objects.voxelTerrainShader;
		
		int transformMatrixLocation = glGetUniformLocation(shader.getProgramId(), "modelMatrix");
    	float[] transform = new float[4*4];
        transform[0] = 1;
        transform[5] = 1;
        transform[10] = 1;
        transform[15] = 1;
		
		transform[12] = x;
		transform[13] = y;
		transform[14] = z;
		
		glUniformMatrix4fv(transformMatrixLocation, false, transform);
		mesh.draw();
	}
	
	//TODO: use custom shader, positions relative to entity position
	public static void SetRenderModeEntity(Camera camera, Entity entity)
	{
		glUseProgram(0);
		
		float[] perspective = new float[4*4];
		float[] view = new float[4*4];
		
		camera.getMatrix(-entity.position.x, -entity.position.y, -entity.position.z, perspective, view);
		glMatrixMode(GL_PROJECTION);
		glLoadMatrixf(perspective);
    	glMatrixMode (GL_MODELVIEW);
    	glLoadMatrixf(view);
    	glColor4f(1,1,1,1);
	}
	
	//TODO: use custom shader
	public static void SetEntityRenderLight(Color color)
	{
		glColor3f(color.r, color.g, color.b);
	}
	
}
