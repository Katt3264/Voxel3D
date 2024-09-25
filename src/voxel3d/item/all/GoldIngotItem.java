package voxel3d.item.all;

import voxel3d.global.AssetLoader;
import voxel3d.graphics.Texture;
import voxel3d.item.Item;
import voxel3d.item.context.ItemRenderContext;

public class GoldIngotItem extends Item {

	private static Texture texture = AssetLoader.loadItemTexture("Gold ingot");
	private static GoldIngotItem sharedInstance = new GoldIngotItem();
	
	static {
		Item.setItemDeserializerForName(sharedInstance);
	}
	
	public static GoldIngotItem getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public String getName() 
	{
		return "Gold ingot";
	}
	
	@Override
	public void render(ItemRenderContext context)
	{
		context.drawTexture(texture);
	}

}
