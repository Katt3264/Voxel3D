package voxel3d.item.all;

import voxel3d.global.AssetLoader;
import voxel3d.graphics.Texture;
import voxel3d.item.Item;
import voxel3d.item.context.ItemRenderContext;

public class CopperIngotItem extends Item {

	private static Texture texture = AssetLoader.loadItemTexture("Copper ingot");
	private static CopperIngotItem sharedInstance = new CopperIngotItem();
	
	static {
		Item.setItemDeserializerForName(sharedInstance);
	}
	
	public static CopperIngotItem getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public String getName() 
	{
		return "Copper ingot";
	}
	
	@Override
	public void render(ItemRenderContext context)
	{
		context.drawTexture(texture);
	}

}
