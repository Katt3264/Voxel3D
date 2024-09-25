package voxel3d.gui;

import voxel3d.block.all.ChestBlock;
import voxel3d.global.Objects;
import voxel3d.item.Item;
import voxel3d.utility.GUIUtill;

public class ChestGUI implements HUDInteractable {
	
	private ItemSlotGUI[] slots = new ItemSlotGUI[10 * 5];
	
	private float w = 44;
	private float h = 24;
	
	private float xSize = 1.5f;
	private float ySize = (h / w) * xSize;
	
	private float pixelSize = (xSize / w);
	private float cellSize = 4 * pixelSize;
	
	private float x = -(xSize / 2f);
	private float y = 0;
	
	
	public ChestGUI()
	{	
		for(int yy = 0; yy < 5; yy++)
		{
			for(int xx = 0; xx < 10; xx++)
			{
				slots[yy*10 + xx] = new ItemSlotGUI(x + cellSize*0.5f + xx*cellSize, -y +cellSize*4.5f - yy*cellSize, cellSize);
			}
		}
	}
	
	
	public void update(HUDUpdateContext context)
	{	
		ChestBlock block = (ChestBlock) context.getPlayerFacing();
		
		for(int i = 0; i < slots.length; i++)
		{
			slots[i].update();
			if(slots[i].selected)
				InventoryGUI.interact(block.internal[i], context.getInventory().mouseItem, true);
		}
	}
	
	
	public void draw(HUDRenderContext context)
	{
		ChestBlock block = (ChestBlock) context.getPlayerFacing();
		
		GUIUtill.drawRect(x, y, xSize, ySize, Objects.chestGUI);
		
		
		for(int i = 0; i < slots.length; i++)
		{
			slots[i].draw(block.internal[i]);
			
			if(slots[i].selected && block.internal[i].item != null)
			{
				Item item = block.internal[i].item;
				context.setGuiHint(item.getName());
			}
		}
	}

}
