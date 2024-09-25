package voxel3d.item.all;

import voxel3d.global.AssetLoader;
import voxel3d.graphics.Texture;
import voxel3d.item.Item;
import voxel3d.item.context.ItemRenderContext;

public class IronIngotItem extends Item {

	private static Texture texture = AssetLoader.loadItemTexture("Iron ingot");
	private static IronIngotItem sharedInstance = new IronIngotItem();
	
	static {
		Item.setItemDeserializerForName(sharedInstance);
	}
	
	public static IronIngotItem getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public String getName() 
	{
		return "Iron ingot";
	}
	
	@Override
	public void render(ItemRenderContext context)
	{
		context.drawTexture(texture);
	}

}
