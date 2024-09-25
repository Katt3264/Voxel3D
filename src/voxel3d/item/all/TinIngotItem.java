package voxel3d.item.all;

import voxel3d.global.AssetLoader;
import voxel3d.graphics.Texture;
import voxel3d.item.Item;
import voxel3d.item.context.ItemRenderContext;

public class TinIngotItem extends Item {

	private static Texture texture = AssetLoader.loadItemTexture("Tin ingot");
	private static TinIngotItem sharedInstance = new TinIngotItem();
	
	static {
		Item.setItemDeserializerForName(sharedInstance);
	}
	
	public static TinIngotItem getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public String getName() 
	{
		return "Tin ingot";
	}
	
	@Override
	public void render(ItemRenderContext context)
	{
		context.drawTexture(texture);
	}

}
