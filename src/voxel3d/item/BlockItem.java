package voxel3d.item;

import voxel3d.block.Block;
import voxel3d.global.AssetLoader;
import voxel3d.graphics.Texture;
import voxel3d.item.context.ItemRenderContext;
import voxel3d.item.context.ItemUseContext;

public class BlockItem extends Item {

	private Texture texture;
	private final Block block;
	
	public BlockItem(Block block)
	{
		this.block = block;
		this.texture = AssetLoader.loadItemTexture(block.getName());
	}
	
	@Override
	public String getName() 
	{
		return block.getName();
	}
	
	@Override
	public void render(ItemRenderContext context)
	{
		context.drawTexture(texture);
	}
	
	@Override
	public void onUse(ItemUseContext context)
	{
		context.placeBlock(block.getBlockInstance());
	}

}
