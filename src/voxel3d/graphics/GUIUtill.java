package voxel3d.graphics;

import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL20.*;

import voxel3d.global.Objects;
import voxel3d.gui.Glyph;

public class GUIUtill {
	
	public static void drawString(String s, float xx, float yy, float height)
	{
		glColor3f(1f, 1f, 1f);
		Objects.glyphAtlas.glBind();
		glBegin(GL_QUADS);
		
		float epsilon  = 0.0001f;
		float epsilon2 = 0.0002f;
		
		for(int i = 0; i < s.length(); i++)
		{
			Glyph glyph = Glyph.getGlyph(s.charAt(i));
			
			float ux = (glyph.uv.x + epsilon) * Glyph.tileSize;
			float uy = (glyph.uv.y + epsilon) * Glyph.tileSize;
			float de = Glyph.tileSize - epsilon2;
			float xSize = height * (6f/8f);
			float ySize = height ;
			float x = xx + i * xSize;
			float y = yy;
			
			glTexCoord2f(ux, uy + de);
			glVertex2d(x, y);
		
			glTexCoord2f(ux, uy );
			glVertex2d(x, y + ySize);
		
			glTexCoord2f(ux + de, uy );
			glVertex2d(x + xSize, y + ySize);
		
			glTexCoord2f(ux + de, uy + de);
			glVertex2d(x + xSize, y);
		}
		
		glEnd();
		Objects.glyphAtlas.glUnbind();
	}
	
	public static void drawSquare(float x, float y, float size, Texture texture)
	{
		glColor3f(1f, 1f, 1f);
		texture.glBind();
		glBegin(GL_QUADS);
		
		glTexCoord2f(0, 1);
		glVertex2d(x, y);
	
		glTexCoord2f(0, 0 );
		glVertex2d(x, y + size);
	
		glTexCoord2f(1, 0 );
		glVertex2d(x + size, y + size);
	
		glTexCoord2f(1, 1);
		glVertex2d(x + size, y);
		
		glEnd();
		texture.glUnbind();
	}
	
	public static void drawRect(float x, float y, float sizeX, float sizeY, Texture texture)
	{
		glColor3f(1f, 1f, 1f);
		texture.glBind();
		glBegin(GL_QUADS);
		
		glTexCoord2f(0, 1);
		glVertex2d(x, y);
	
		glTexCoord2f(0, 0 );
		glVertex2d(x, y + sizeY);
	
		glTexCoord2f(1, 0 );
		glVertex2d(x + sizeX, y + sizeY);
	
		glTexCoord2f(1, 1);
		glVertex2d(x + sizeX, y);
		
		glEnd();
		texture.glUnbind();
	}
	
	public static void drawSquare(float x, float y, float size, Texture texture, float uvX, float uvY, float tileSize)
	{
		glColor3f(1f, 1f, 1f);
		texture.glBind();
		glBegin(GL_QUADS);
		
		float ux = uvX * tileSize;
		float uy = uvY * tileSize;
		float de = tileSize;
		
		glTexCoord2f(ux, uy + de);
		glVertex2d(x, y);
	
		glTexCoord2f(ux, uy);
		glVertex2d(x, y + size);
	
		glTexCoord2f(ux + de, uy);
		glVertex2d(x + size, y + size);
	
		glTexCoord2f(ux + de, uy + de);
		glVertex2d(x + size, y);
		
		glEnd();
		texture.glUnbind();
	}

}
