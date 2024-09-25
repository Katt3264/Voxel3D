package voxel3d.item.all;

import voxel3d.block.all.Plant;
import voxel3d.global.AssetLoader;
import voxel3d.graphics.Texture;
import voxel3d.item.Item;
import voxel3d.item.context.ItemRenderContext;
import voxel3d.item.context.ItemUseContext;

public class PlantSeedItem extends Item {

	private static Texture texture = AssetLoader.loadItemTexture("Plant seed");
	private static PlantSeedItem sharedInstance = new PlantSeedItem();
	
	static {
		Item.setItemDeserializerForName(sharedInstance);
	}
	
	public static PlantSeedItem getInstance()
	{
		return sharedInstance;
	}
	
	@Override
	public String getName() 
	{
		return "Plant seed";
	}
	
	@Override
	public void render(ItemRenderContext context)
	{
		context.drawTexture(texture);
	}
	
	@Override
	public void onUse(ItemUseContext context)
	{
		context.placeBlock(Plant.getInstance());
	}

}
