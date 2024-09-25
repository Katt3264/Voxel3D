package voxel3d.item.all;

import voxel3d.global.AssetLoader;
import voxel3d.graphics.Texture;
import voxel3d.item.Item;
import voxel3d.item.context.ItemRenderContext;

public class BronzeIngotItem extends Item {

	private static Texture texture = AssetLoader.loadItemTexture("Bronze ingot");
	private static BronzeIngotItem sharedInstance = new BronzeIngotItem();
	
	static {
		Item.setItemDeserializerForName(sharedInstance);
	}
	
	public static BronzeIngotItem getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public String getName() 
	{
		return "Bronze ingot";
	}
	
	@Override
	public void render(ItemRenderContext context)
	{
		context.drawTexture(texture);
	}

}
