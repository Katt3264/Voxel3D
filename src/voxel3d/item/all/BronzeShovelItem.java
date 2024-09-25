package voxel3d.item.all;

import voxel3d.global.AssetLoader;
import voxel3d.graphics.Texture;
import voxel3d.item.Item;
import voxel3d.item.context.ItemRenderContext;

public class BronzeShovelItem extends Item {

	private static Texture texture = AssetLoader.loadItemTexture("Bronze shovel");
	private static BronzeShovelItem sharedInstance = new BronzeShovelItem();
	
	static {
		Item.setItemDeserializerForName(sharedInstance);
	}
	
	public static BronzeShovelItem getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public String getName() 
	{
		return "Bronze shovel";
	}
	
	@Override
	public void render(ItemRenderContext context)
	{
		context.drawTexture(texture);
	}

}
