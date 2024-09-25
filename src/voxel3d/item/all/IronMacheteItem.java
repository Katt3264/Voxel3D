package voxel3d.item.all;

import voxel3d.global.AssetLoader;
import voxel3d.graphics.Texture;
import voxel3d.item.Item;
import voxel3d.item.context.ItemRenderContext;

public class IronMacheteItem extends Item {

	private static Texture texture = AssetLoader.loadItemTexture("Iron machete");
	private static IronMacheteItem sharedInstance = new IronMacheteItem();
	
	static {
		Item.setItemDeserializerForName(sharedInstance);
	}
	
	public static IronMacheteItem getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public String getName() 
	{
		return "Iron machete";
	}
	
	@Override
	public void render(ItemRenderContext context)
	{
		context.drawTexture(texture);
	}

}
