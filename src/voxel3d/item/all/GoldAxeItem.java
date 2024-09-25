package voxel3d.item.all;

import voxel3d.global.AssetLoader;
import voxel3d.graphics.Texture;
import voxel3d.item.Item;
import voxel3d.item.context.ItemRenderContext;

public class GoldAxeItem extends Item {

	private static Texture texture = AssetLoader.loadItemTexture("Gold axe");
	private static GoldAxeItem sharedInstance = new GoldAxeItem();
	
	static {
		Item.setItemDeserializerForName(sharedInstance);
	}
	
	public static GoldAxeItem getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public String getName() 
	{
		return "Gold axe";
	}
	
	@Override
	public void render(ItemRenderContext context)
	{
		context.drawTexture(texture);
	}

}
