package voxel3d.graphics;

import static org.lwjgl.opengl.GL40.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Scanner;

public class Shader {
	
	private int programId;
    private int vertexId;
    private int fragId;
    private InputStream vertexPath;
    private InputStream fragPath;
    private boolean linked = false;
    
    public Shader(String vertexPath, String fragPath){
        //this.vertexPath = getClass().getClassLoader().getResourceAsStream(vertexPath);
        //this.fragPath = getClass().getClassLoader().getResourceAsStream(fragPath);
        try {
        	this.vertexPath = new FileInputStream(new File(vertexPath));
        	this.fragPath = new FileInputStream(new File(fragPath));
        	link();
        } catch(Exception e)
        {
        	e.printStackTrace();
        }
    }
    
    private void link() throws Exception {
        linked = true;
        programId = glCreateProgram();
        if (programId == 0) {
            throw new Exception("Unable to create shader program!");
        }
        
        vertexId = createShader(vertexPath, GL_VERTEX_SHADER);
        fragId = createShader(fragPath, GL_FRAGMENT_SHADER);

        //IMPORTANT! 
        
        glBindAttribLocation(programId, 0, "position");
        glBindAttribLocation(programId, 1, "color");
        glBindAttribLocation(programId, 2, "uv");
        
        glLinkProgram(programId);
        if (glGetProgrami(programId, GL_LINK_STATUS) == 0) {
            throw new Exception("Error linking shader: " + glGetProgramInfoLog(programId));
        }

        if (vertexId == 0) {
            glDetachShader(programId, vertexId);
        }
        if (fragId == 0) {
            glDetachShader(programId, fragId); //changed from vertexId
        }

        glValidateProgram(programId);
        if (glGetProgrami(programId, GL_VALIDATE_STATUS) == 0) {
            System.err.println("Warning: " + glGetProgramInfoLog(programId));
        }
    }

    public void bind() {
        glUseProgram(programId);
    }

    public void unbind() {
        glUseProgram(0);
    }

    public void destroy() {
        unbind();
        if (programId != 0) {
            glDeleteProgram(programId);
        }
    }

    private int createShader(InputStream input, int shaderType) throws Exception {
        String code = readFile(input);
        int shaderId = glCreateShader(shaderType);
        if (shaderId == 0) {
            throw new Exception("Error creating shader: " + glGetShaderInfoLog(shaderId));
        }

        glShaderSource(shaderId, code);
        glCompileShader(shaderId);
        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
            throw new Exception("Error creating shader: " + glGetShaderInfoLog(shaderId));
        }

        glAttachShader(programId, shaderId);

        return shaderId;
    }

    private String readFile(InputStream input) {
        StringBuilder sb = new StringBuilder();
        try (Scanner sc = new Scanner(input)) {
            while (sc.hasNextLine()) {
                sb.append(sc.nextLine());
                sb.append(System.lineSeparator());
            }
        }

        return sb.toString();
    }

    public boolean isLinked() {
        return linked;
    }

    public int getProgramId() {
        return programId;
    }

    public int getLocation(String name) {
        return glGetUniformLocation(programId, name);
    }

}
