package voxel3d.item.all;

import voxel3d.global.AssetLoader;
import voxel3d.graphics.Texture;
import voxel3d.item.Item;
import voxel3d.item.context.ItemRenderContext;

public class BronzePickaxeItem extends Item {

	private static Texture texture = AssetLoader.loadItemTexture("Bronze pickaxe");
	private static BronzePickaxeItem sharedInstance = new BronzePickaxeItem();
	
	static {
		Item.setItemDeserializerForName(sharedInstance);
	}
	
	public static BronzePickaxeItem getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public String getName() 
	{
		return "Bronze pickaxe";
	}
	
	@Override
	public void render(ItemRenderContext context)
	{
		context.drawTexture(texture);
	}

}
