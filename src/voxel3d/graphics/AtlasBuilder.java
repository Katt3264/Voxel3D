package voxel3d.graphics;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import voxel3d.utility.Vector2f;

public class AtlasBuilder {
	
	private List<BufferedImage> images = new ArrayList<BufferedImage>();
	private List<Vector2f[]> positions = new ArrayList<Vector2f[]>();
	private HashMap<String, Vector2f[]> vectors = new HashMap<String, Vector2f[]>();
	
	private int w = 0;
	private int h = 0;
	
	public void add(BufferedImage image, String name)
	{
		images.add(image);
		Vector2f[] vec = new Vector2f[] {new Vector2f(0f, 0f), new Vector2f(0f, 0f)};
		vectors.put(name, vec);
		positions.add(vec);
		w += image.getWidth();
		h = Math.max(h, image.getHeight());
	}
	
	public Vector2f[] get(String name)
	{
		return vectors.get(name);
	}
	
	public Texture getTexture()
	{
		BufferedImage atlas = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		
		int startX = 0;
		
		for(int i = 0; i < images.size(); i++)
		{
			BufferedImage source = images.get(i);
			positions.get(i)[0].x = ((float)startX / (float)atlas.getWidth());
			positions.get(i)[0].y = 0;
			positions.get(i)[1].x = ((float)(startX + source.getWidth()) / (float)atlas.getWidth());
			positions.get(i)[1].y = ((float)source.getHeight() / (float)atlas.getHeight());
			draw(source, atlas, startX);
			startX += source.getWidth();
		}
		
		return Texture.get(atlas);
	}
	
	private static void draw(BufferedImage source, BufferedImage dest, int startX)
	{
		for(int x = 0; x < source.getWidth(); x++)
		{
			for(int y = 0; y < source.getHeight(); y++)
			{
				dest.setRGB(startX + x, y, source.getRGB(x, y));
			}
		}
	}

}
