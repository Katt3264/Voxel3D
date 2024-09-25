package voxel3d.item.all;

import voxel3d.global.AssetLoader;
import voxel3d.graphics.Texture;
import voxel3d.item.Item;
import voxel3d.item.context.ItemRenderContext;

public class IronPickaxeItem extends Item {

	private static Texture texture = AssetLoader.loadItemTexture("Iron pickaxe");
	private static IronPickaxeItem sharedInstance = new IronPickaxeItem();
	
	static {
		Item.setItemDeserializerForName(sharedInstance);
	}
	
	public static IronPickaxeItem getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public String getName() 
	{
		return "Iron pickaxe";
	}
	
	@Override
	public void render(ItemRenderContext context)
	{
		context.drawTexture(texture);
	}

}
