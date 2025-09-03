package voxel3d.graphics;

import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.lwjgl.system.MemoryUtil;

import voxel3d.utility.FloatList;

public class Mesh {
	
	private static List<Mesh> allMeshes = new LinkedList<Mesh>();
	private static long globalCall = 0;
	
	private long thisCall = 0;
	private boolean alive = true;
	private int vbo_vertex;
	private int vbo_uv;
	private int vbo_color;
	
	//private static final int vertex_size = 3;
	//private static final int color_size = 3;
	//private static final int uv_size = 2;
	private static final int floatByteSize = 4;
	
	public final int triangles;
	
	
	public Mesh(FloatList vertices, FloatList uvs, FloatList colors)
	{
		thisCall = globalCall;
		
		this.triangles = vertices.size() / 3;
		
		FloatBuffer verticesBuffer = MemoryUtil.memAllocFloat(vertices.size());
		verticesBuffer.put(vertices.getRaw(), 0, vertices.size());
		verticesBuffer.flip();
		vbo_vertex = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo_vertex);
		glBufferData(GL_ARRAY_BUFFER, vertices.size() * floatByteSize, GL_STATIC_DRAW);
		glBufferSubData(GL_ARRAY_BUFFER, 0, verticesBuffer);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		MemoryUtil.memFree(verticesBuffer);
		
		FloatBuffer uvBuffer = MemoryUtil.memAllocFloat(uvs.size());
		uvBuffer.put(uvs.getRaw(), 0, uvs.size());
		uvBuffer.flip();
		vbo_uv = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo_uv);
		glBufferData(GL_ARRAY_BUFFER, uvs.size() * floatByteSize, GL_STATIC_DRAW);
		glBufferSubData(GL_ARRAY_BUFFER, 0, uvBuffer);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		MemoryUtil.memFree(uvBuffer);
		
		FloatBuffer colorBuffer = MemoryUtil.memAllocFloat(colors.size());
		colorBuffer.put(colors.getRaw(), 0, colors.size());
		colorBuffer.flip();
		vbo_color = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo_color);
		glBufferData(GL_ARRAY_BUFFER, colors.size() * floatByteSize, GL_STATIC_DRAW);
		glBufferSubData(GL_ARRAY_BUFFER, 0, colorBuffer);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		MemoryUtil.memFree(colorBuffer);
		
		allMeshes.add(this);
	}
	
	public void draw()
	{
		if(!alive)
			return;
		
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo_vertex);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * 4, 0);
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo_color);
		glVertexAttribPointer(1, 4, GL_FLOAT, false, 4 * 4, 0);
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo_uv);
		glVertexAttribPointer(2, 3, GL_FLOAT, false, 2 * 4, 0);
		
		glDrawArrays(GL_TRIANGLES, 0, triangles);
		
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	public void keepAlive()
	{
		thisCall = globalCall;
	}
	
	public boolean isAlive()
	{
		return alive;
	}
	
	private void free()
	{
		if(!alive)
			return;
		
		glDeleteBuffers(vbo_vertex);
		glDeleteBuffers(vbo_color);
		glDeleteBuffers(vbo_uv);
		
		alive = false;
	}
	
	public static void cleanup()
	{
		Iterator<Mesh> m = allMeshes.iterator();
		while(m.hasNext())
		{
			Mesh mesh = m.next();
			if(mesh.thisCall < globalCall - 3)
			{
				mesh.free();
				m.remove();
			}
		}
		
		globalCall++;
	}
}
