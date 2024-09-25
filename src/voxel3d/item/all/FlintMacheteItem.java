package voxel3d.item.all;

import voxel3d.global.AssetLoader;
import voxel3d.graphics.Texture;
import voxel3d.item.Item;
import voxel3d.item.context.ItemRenderContext;

public class FlintMacheteItem extends Item {

	private static Texture texture = AssetLoader.loadItemTexture("Flint machete");
	private static FlintMacheteItem sharedInstance = new FlintMacheteItem();
	
	static {
		Item.setItemDeserializerForName(sharedInstance);
	}
	
	public static FlintMacheteItem getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public String getName() 
	{
		return "Flint machete";
	}
	
	@Override
	public void render(ItemRenderContext context)
	{
		context.drawTexture(texture);
	}

}
