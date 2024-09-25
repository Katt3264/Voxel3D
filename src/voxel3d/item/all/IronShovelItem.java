package voxel3d.item.all;

import voxel3d.global.AssetLoader;
import voxel3d.graphics.Texture;
import voxel3d.item.Item;
import voxel3d.item.context.ItemRenderContext;

public class IronShovelItem extends Item {

	private static Texture texture = AssetLoader.loadItemTexture("Iron shovel");
	private static IronShovelItem sharedInstance = new IronShovelItem();
	
	static {
		Item.setItemDeserializerForName(sharedInstance);
	}
	
	public static IronShovelItem getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public String getName() 
	{
		return "Iron shovel";
	}
	
	@Override
	public void render(ItemRenderContext context)
	{
		context.drawTexture(texture);
	}

}
