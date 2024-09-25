package voxel3d.item.all;

import voxel3d.global.AssetLoader;
import voxel3d.graphics.Texture;
import voxel3d.item.Item;
import voxel3d.item.context.ItemRenderContext;

public class FlintPickaxeItem extends Item {

	private static Texture texture = AssetLoader.loadItemTexture("Flint pickaxe");
	private static FlintPickaxeItem sharedInstance = new FlintPickaxeItem();
	
	static {
		Item.setItemDeserializerForName(sharedInstance);
	}
	
	public static FlintPickaxeItem getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public String getName() 
	{
		return "Flint pickaxe";
	}
	
	@Override
	public void render(ItemRenderContext context)
	{
		context.drawTexture(texture);
	}

}
