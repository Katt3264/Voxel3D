package voxel3d.item.all;

import voxel3d.global.AssetLoader;
import voxel3d.graphics.Texture;
import voxel3d.item.Item;
import voxel3d.item.context.ItemRenderContext;
import voxel3d.item.context.ItemUseContext;

public class CarrotItem extends Item {

	private static Texture texture = AssetLoader.loadItemTexture("Carrot");
	private static CarrotItem sharedInstance = new CarrotItem();
	
	static {
		Item.setItemDeserializerForName(sharedInstance);
	}
	
	public static CarrotItem getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public String getName() 
	{
		return "Carrot";
	}
	
	@Override
	public void render(ItemRenderContext context)
	{
		context.drawTexture(texture);
	}
	
	@Override
	public void onUse(ItemUseContext context)
	{
		context.consume(30);
	}

}
