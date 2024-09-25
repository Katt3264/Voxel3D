package voxel3d.item.all;

import voxel3d.global.AssetLoader;
import voxel3d.graphics.Texture;
import voxel3d.item.Item;
import voxel3d.item.context.ItemRenderContext;

public class FlintItem extends Item {

	private static Texture texture = AssetLoader.loadItemTexture("Flint");
	private static FlintItem sharedInstance = new FlintItem();
	
	static {
		Item.setItemDeserializerForName(sharedInstance);
	}
	
	public static FlintItem getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public String getName() 
	{
		return "Flint";
	}
	
	@Override
	public void render(ItemRenderContext context)
	{
		context.drawTexture(texture);
	}

}
