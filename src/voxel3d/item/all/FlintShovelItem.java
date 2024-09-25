package voxel3d.item.all;

import voxel3d.global.AssetLoader;
import voxel3d.graphics.Texture;
import voxel3d.item.Item;
import voxel3d.item.context.ItemRenderContext;

public class FlintShovelItem extends Item {

	private static Texture texture = AssetLoader.loadItemTexture("Flint shovel");
	private static FlintShovelItem sharedInstance = new FlintShovelItem();
	
	static {
		Item.setItemDeserializerForName(sharedInstance);
	}
	
	public static FlintShovelItem getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public String getName() 
	{
		return "Flint shovel";
	}
	
	@Override
	public void render(ItemRenderContext context)
	{
		context.drawTexture(texture);
	}

}
