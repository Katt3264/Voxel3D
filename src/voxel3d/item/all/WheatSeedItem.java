package voxel3d.item.all;

import voxel3d.block.all.WheatPlant;
import voxel3d.global.AssetLoader;
import voxel3d.graphics.Texture;
import voxel3d.item.Item;
import voxel3d.item.context.ItemRenderContext;
import voxel3d.item.context.ItemUseContext;

public class WheatSeedItem extends Item {

	private static Texture texture = AssetLoader.loadItemTexture("Wheat seed");
	private static WheatSeedItem sharedInstance = new WheatSeedItem();
	
	static {
		Item.setItemDeserializerForName(sharedInstance);
	}
	
	public static WheatSeedItem getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public String getName() 
	{
		return "Wheat seed";
	}
	
	@Override
	public void render(ItemRenderContext context)
	{
		context.drawTexture(texture);
	}
	
	@Override
	public void onUse(ItemUseContext context)
	{
		context.placeBlock(WheatPlant.getInstance());
	}

}
