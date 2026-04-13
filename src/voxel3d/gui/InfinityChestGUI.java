package voxel3d.gui;

import voxel3d.data.ItemValue;
import voxel3d.global.Input;
import voxel3d.global.Objects;
import voxel3d.graphics.GUIUtill;
import voxel3d.item.Item;

public class InfinityChestGUI implements HUDInteractable {
	
	private ItemSlotGUI[] slots = new ItemSlotGUI[pageSize];
	private ItemValue[] ivSlots = new ItemValue[pageSize];
	
	private Button goLeft;
	private Button goRight;
	
	private float w = 44;
	private float h = 30;
	
	private float xSize = 1.5f;
	private float ySize = (h / w) * xSize;
	
	private float pixelSize = (xSize / w);
	private float cellSize = 4 * pixelSize;
	
	private float x = -(xSize / 2f);
	private float y = 0;
	
	private static int pageSize = 10*5;
	private int page = 0;
	
	public InfinityChestGUI()
	{	
		for(int yy = 0; yy < 5; yy++)
		{
			for(int xx = 0; xx < 10; xx++)
			{
				slots[yy*10 + xx] = new ItemSlotGUI(x + cellSize*0.5f + xx*cellSize, -y +cellSize*4.5f - yy*cellSize, cellSize);
				ivSlots[yy*10 + xx] = new ItemValue();
			}
		}
		goLeft = new Button(x + cellSize*0.5f, y + cellSize*6, cellSize, cellSize);
		goLeft.mouseOff = Objects.leftButton;
		goLeft.mouseOn = Objects.leftButtonSelected;
		
		goRight = new Button(x + cellSize*9.5f, y + cellSize*6, cellSize, cellSize);
		goRight.mouseOff = Objects.rightButton;
		goRight.mouseOn = Objects.rightButtonSelected;
	}
	
	
	public void update(HUDUpdateContext context)
	{	
		int itemCount = 0;
		for(@SuppressWarnings("unused") Item item : Item.getAllItems())
		{
			itemCount++;
		}
		
		goLeft.update();
		if(goLeft.selected && Input.hit.isButtonPress()) {page -= 1;}
		
		goRight.update();
		if(goRight.selected && Input.hit.isButtonPress()) {page += 1;}
		
		page = Math.max(0, Math.min(Math.floorDiv(itemCount - 1, pageSize), page));
		
		for(int i = 0; i < slots.length; i++)
		{
			ItemValue iv = ivSlots[i];
			iv.item = null;
			iv.value = 0;
		}
		
		int index = 0;
		int startIndex = page * pageSize;
		for(Item item : Item.getAllItems())
		{
			if(index >= startIndex && index < startIndex+pageSize)
			{
				int i = index - startIndex;
				ItemValue iv = ivSlots[i];
				iv.item = item;
				iv.value = 1;
			}
			index++;
		}
		
		for(int i = 0; i < slots.length; i++)
		{
			slots[i].update();
			if(slots[i].selected)
				InventoryGUI.interactInfinity(ivSlots[i], context.getInventory().mouseItem);
		}
	}
	
	
	public void draw(HUDRenderContext context)
	{
		GUIUtill.drawRect(x, y, xSize, ySize, Objects.infinityChestGUI);
		
		goRight.draw();
		goLeft.draw();
		
		for(int i = 0; i < slots.length; i++)
		{
			slots[i].draw(ivSlots[i]);
			
			if(slots[i].selected && ivSlots[i].item != null)
			{
				Item item = ivSlots[i].item;
				context.setGuiHint(item.getName());
			}
		}
	}

}
