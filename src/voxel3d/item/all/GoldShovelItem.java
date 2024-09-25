package voxel3d.item.all;

import voxel3d.global.AssetLoader;
import voxel3d.graphics.Texture;
import voxel3d.item.Item;
import voxel3d.item.context.ItemRenderContext;

public class GoldShovelItem extends Item {

	private static Texture texture = AssetLoader.loadItemTexture("Gold shovel");
	private static GoldShovelItem sharedInstance = new GoldShovelItem();
	
	static {
		Item.setItemDeserializerForName(sharedInstance);
	}
	
	public static GoldShovelItem getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public String getName() 
	{
		return "Gold shovel";
	}
	
	@Override
	public void render(ItemRenderContext context)
	{
		context.drawTexture(texture);
	}

}
