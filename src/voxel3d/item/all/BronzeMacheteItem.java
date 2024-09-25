package voxel3d.item.all;

import voxel3d.global.AssetLoader;
import voxel3d.graphics.Texture;
import voxel3d.item.Item;
import voxel3d.item.context.ItemRenderContext;

public class BronzeMacheteItem extends Item {

	private static Texture texture = AssetLoader.loadItemTexture("Bronze machete");
	private static BronzeMacheteItem sharedInstance = new BronzeMacheteItem();
	
	static {
		Item.setItemDeserializerForName(sharedInstance);
	}
	
	public static BronzeMacheteItem getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public String getName() 
	{
		return "Bronze machete";
	}
	
	@Override
	public void render(ItemRenderContext context)
	{
		context.drawTexture(texture);
	}

}
