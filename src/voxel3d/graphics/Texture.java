package voxel3d.graphics;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL40.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

public class Texture {
	
	private final int texture;
	
	public static Texture get(String path)
	{
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Texture(image);
	}
	
	public static Texture get(BufferedImage image)
	{
		return new Texture(image);
	}
	
	private Texture(BufferedImage image)
	{
		byte[] bytes = null;
		
		try {
			
			bytes = new byte[image.getWidth() * image.getHeight() * 4];
			
			for(int i = 0; i < bytes.length; i+=4)
			{
				int argb = image.getRGB((i/4)%image.getWidth(), Math.floorDiv((i/4), image.getWidth())); 
				bytes[i  ] = (byte) ((argb >> 16) & 0xff);
				bytes[i+1] = (byte) ((argb >> 8 ) & 0xff);
				bytes[i+2] = (byte) ((argb >> 0 ) & 0xff);
				bytes[i+3] = (byte) ((argb >> 24) & 0xff);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		
		ByteBuffer buf = BufferUtils.createByteBuffer(bytes.length);
		
		buf.put(bytes);
		buf.flip();
		
		texture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texture);
        
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        
        //glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST_MIPMAP_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        
        //glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAX_LEVEL, 4);
        
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buf);
        
        //glGenerateMipmap(GL_TEXTURE_2D);
	}
	
	public void glBind()
	{
		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, texture);
	}
	
	public void glUnbind()
	{
		glBindTexture(GL_TEXTURE_2D, 0);
		//glDisable(GL_TEXTURE_2D);
	}

}
