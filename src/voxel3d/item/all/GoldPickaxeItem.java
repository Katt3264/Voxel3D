package voxel3d.item.all;

import voxel3d.global.AssetLoader;
import voxel3d.graphics.Texture;
import voxel3d.item.Item;
import voxel3d.item.context.ItemRenderContext;

public class GoldPickaxeItem extends Item {

	private static Texture texture = AssetLoader.loadItemTexture("Gold pickaxe");
	private static GoldPickaxeItem sharedInstance = new GoldPickaxeItem();
	
	static {
		Item.setItemDeserializerForName(sharedInstance);
	}
	
	public static GoldPickaxeItem getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public String getName() 
	{
		return "Gold pickaxe";
	}
	
	@Override
	public void render(ItemRenderContext context)
	{
		context.drawTexture(texture);
	}

}
