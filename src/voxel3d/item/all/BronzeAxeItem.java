package voxel3d.item.all;

import voxel3d.global.AssetLoader;
import voxel3d.graphics.Texture;
import voxel3d.item.Item;
import voxel3d.item.context.ItemRenderContext;

public class BronzeAxeItem extends Item {

	private static Texture texture = AssetLoader.loadItemTexture("Bronze axe");
	private static BronzeAxeItem sharedInstance = new BronzeAxeItem();
	
	static {
		Item.setItemDeserializerForName(sharedInstance);
	}
	
	public static BronzeAxeItem getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public String getName() 
	{
		return "Bronze axe";
	}
	
	@Override
	public void render(ItemRenderContext context)
	{
		context.drawTexture(texture);
	}

}
