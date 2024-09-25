package voxel3d.item.all;

import voxel3d.global.AssetLoader;
import voxel3d.graphics.Texture;
import voxel3d.item.Item;
import voxel3d.item.context.ItemRenderContext;

public class GoldMacheteItem extends Item {

	private static Texture texture = AssetLoader.loadItemTexture("Gold machete");
	private static GoldMacheteItem sharedInstance = new GoldMacheteItem();
	
	static {
		Item.setItemDeserializerForName(sharedInstance);
	}
	
	public static GoldMacheteItem getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public String getName() 
	{
		return "Gold machete";
	}
	
	@Override
	public void render(ItemRenderContext context)
	{
		context.drawTexture(texture);
	}

}
