package voxel3d.item.all;

import voxel3d.global.AssetLoader;
import voxel3d.graphics.Texture;
import voxel3d.item.Item;
import voxel3d.item.context.ItemRenderContext;

public class IronAxeItem extends Item {

	private static Texture texture = AssetLoader.loadItemTexture("Iron axe");
	private static IronAxeItem sharedInstance = new IronAxeItem();
	
	static {
		Item.setItemDeserializerForName(sharedInstance);
	}
	
	public static IronAxeItem getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public String getName() 
	{
		return "Iron axe";
	}
	
	@Override
	public void render(ItemRenderContext context)
	{
		context.drawTexture(texture);
	}

}
