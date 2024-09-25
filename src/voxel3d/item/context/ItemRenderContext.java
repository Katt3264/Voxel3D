package voxel3d.item.context;

import voxel3d.global.Objects;
import voxel3d.graphics.Texture;

public class ItemRenderContext {
	
	public Texture texture;
	
	public void drawTexture(Texture tex)
	{
		this.texture = tex != null ? tex : Objects.missingTexture;
	}
	

}
