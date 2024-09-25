package voxel3d.item.all;

import voxel3d.global.AssetLoader;
import voxel3d.graphics.Texture;
import voxel3d.item.Item;
import voxel3d.item.context.ItemRenderContext;

public class StickItem extends Item {

	private static Texture texture = AssetLoader.loadItemTexture("Stick");
	private static StickItem sharedInstance = new StickItem();
	
	static {
		Item.setItemDeserializerForName(sharedInstance);
	}
	
	public static StickItem getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public String getName() 
	{
		return "Stick";
	}
	
	@Override
	public void render(ItemRenderContext context)
	{
		context.drawTexture(texture);
	}

}
