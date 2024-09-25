package voxel3d.gui;

import voxel3d.data.ItemValue;
import voxel3d.global.Input;
import voxel3d.global.Objects;
import voxel3d.graphics.Texture;
import voxel3d.item.Item;
import voxel3d.item.context.ItemRenderContext;
import voxel3d.utility.GUIUtill;

public class InventoryGUI {
	
	private ItemSlotGUI[] slots = new ItemSlotGUI[10 * 5];
	
	private float w = 44;
	private float h = 26;
	
	private float xSize = 1.5f;
	private float ySize = (h / w) * xSize;
	
	private float pixelSize = (xSize / w);
	private float cellSize = 4 * pixelSize;
	
	private float x = -(xSize / 2f);
	private float y = -ySize;
	
	
	public InventoryGUI()
	{	
		for(int yy = 0; yy < 4; yy++)
		{
			for(int xx = 0; xx < 10; xx++)
			{
				slots[(yy+1)*10 + xx] = new ItemSlotGUI(x + cellSize*0.5f + xx*cellSize, -y -cellSize*8.0f - yy*cellSize, cellSize);
			}
		}
		
		for(int xx = 0; xx < 10; xx++)
		{
			slots[xx] = new ItemSlotGUI(x + cellSize*0.5f + xx*cellSize, y + cellSize*0.5f, cellSize);
		}

	}
	
	
	public void update(HUDUpdateContext context)
	{	
		
		for(int i = 0; i < slots.length; i++)
		{
			slots[i].update();
			if(slots[i].selected)
				interact(context.getInventory().items[i], context.getInventory().mouseItem, true);
		}
	}
	
	public static boolean interact(ItemValue slot, ItemValue mouse, boolean canPlace)
	{
		if(slot.value == 0) {slot.item = null;}
		if(mouse.value == 0) {mouse.item = null;}
		
		
		if(Input.hit.isButtonPress())
		{
			if(mouse.item != slot.item && canPlace || (mouse.value == 0))
			{
				Item tmpItem = mouse.item;
				int tmpValue = mouse.value;
				mouse.item = slot.item;
				mouse.value = slot.value;
				
				slot.item = tmpItem;
				slot.value = tmpValue;
				return true;
			}
			
			if(mouse.item == slot.item && canPlace)
			{
				int transfer = Math.min(99-slot.value, mouse.value);
				slot.value += transfer;
				mouse.value -= transfer;
				if(mouse.value == 0)
				{
					mouse.item = null;
				}
				return true;
			}
			
			if(mouse.item == slot.item && slot.value + mouse.value <= 99)
			{
				mouse.value += slot.value;
				slot.value = 0;
				slot.item = null;
				return true;
			}
		}
		if(Input.place.isButtonPress() && canPlace)
		{
			if((slot.value == 0 || (slot.value < 99 && slot.item == mouse.item)) && mouse.value != 0)
			{
				mouse.value--;
				
				slot.item = mouse.item;
				slot.value++;
				
				if(mouse.value == 0)
				{
					mouse.item = null;
				}
			}
			return true;
		}
		return false;
	}
	
	public void draw(HUDRenderContext context)
	{
		GUIUtill.drawRect(x, y, xSize, ySize, Objects.inventoryGUI);
		
		for(int i = 0; i < slots.length; i++)
		{
			slots[i].draw(context.getInventory().items[i]);
			
			if(slots[i].selected)
			{
				Item item = context.getInventory().items[i].item;
				if(item != null)
				{
					context.setGuiHint(item.getName());
				}
			}
		}
		
		
		if(context.getInventory().mouseItem.value != 0 && context.getInventory().mouseItem.item != null)
		{
			float size = cellSize;
			float x = (Input.getNormalMouseX() - cellSize) + size * 0.5f;
			float y = (Input.getNormalMouseY()) - size * 0.5f;
			
			ItemRenderContext itemRenderContext = new ItemRenderContext();
			context.getInventory().mouseItem.item.render(itemRenderContext);
			Texture tex = itemRenderContext.texture;
			GUIUtill.drawSquare(x + (2f/20f) * size, y + (2f/20f) * size, size * (16f/20f), tex, 0, 0, 1);
			
			if(context.getInventory().mouseItem.value > 1)
			{
				GUIUtill.drawString("" + context.getInventory().mouseItem.value, x, y, (cellSize)/2f);
			}
			
		}
		
		/*for(int i = 0; i < slots.length; i++)
		{
			if(slots[i].selected && context.getInventory().items[i].item != null)
				GUIUtill.drawString(
						"" + context.getInventory().items[i].item.getName(), 
						Input.getNormalMouseX(), 
						Input.getNormalMouseY(), 
						(cellSize)/2f);
		}*/
	}

}
