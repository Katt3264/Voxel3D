package voxel3d.item.all;

import voxel3d.global.AssetLoader;
import voxel3d.graphics.Texture;
import voxel3d.item.Item;
import voxel3d.item.context.ItemRenderContext;

public class FlintAxeItem extends Item {

	private static Texture texture = AssetLoader.loadItemTexture("Flint axe");
	private static FlintAxeItem sharedInstance = new FlintAxeItem();
	
	static {
		Item.setItemDeserializerForName(sharedInstance);
	}
	
	public static FlintAxeItem getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public String getName() 
	{
		return "Flint axe";
	}
	
	@Override
	public void render(ItemRenderContext context)
	{
		context.drawTexture(texture);
	}

}
