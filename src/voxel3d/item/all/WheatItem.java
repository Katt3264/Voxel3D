package voxel3d.item.all;

import voxel3d.global.AssetLoader;
import voxel3d.graphics.Texture;
import voxel3d.item.Item;
import voxel3d.item.context.ItemRenderContext;

public class WheatItem extends Item {

	private static Texture texture = AssetLoader.loadItemTexture("Wheat");
	private static WheatItem sharedInstance = new WheatItem();
	
	static {
		Item.setItemDeserializerForName(sharedInstance);
	}
	
	public static WheatItem getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public String getName() 
	{
		return "Wheat";
	}
	
	@Override
	public void render(ItemRenderContext context)
	{
		context.drawTexture(texture);
	}

}
