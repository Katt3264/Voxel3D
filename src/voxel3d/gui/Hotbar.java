package voxel3d.gui;

import voxel3d.data.Inventory;
import voxel3d.global.Input;
import voxel3d.global.Objects;
import voxel3d.graphics.Texture;
import voxel3d.item.context.ItemRenderContext;
import voxel3d.utility.GUIUtill;

public class Hotbar {
	
	public Hotbar()
	{
	}
	
	public void update(HUDUpdateContext context)
	{
		Inventory inventory = context.getInventory();
		if(Input.num1.isButtonDown()) {inventory.selectedHotbar = 0;}
		if(Input.num2.isButtonDown()) {inventory.selectedHotbar = 1;}
		if(Input.num3.isButtonDown()) {inventory.selectedHotbar = 2;}
		if(Input.num4.isButtonDown()) {inventory.selectedHotbar = 3;}
		if(Input.num5.isButtonDown()) {inventory.selectedHotbar = 4;}
		if(Input.num6.isButtonDown()) {inventory.selectedHotbar = 5;}
		if(Input.num7.isButtonDown()) {inventory.selectedHotbar = 6;}
		if(Input.num8.isButtonDown()) {inventory.selectedHotbar = 7;}
		if(Input.num9.isButtonDown()) {inventory.selectedHotbar = 8;}
		if(Input.num0.isButtonDown()) {inventory.selectedHotbar = 9;}
	}
	
	public void draw(HUDRenderContext context)
	{
		for(int i = 0; i < 10; i++)
		{
			if(context.getInventory().selectedHotbar == i)
			{
				GUIUtill.drawSquare((i-5)*0.2f, -1, 0.2f, Objects.hotbarSlotSelected);
			}
			else
			{
				GUIUtill.drawSquare((i-5)*0.2f, -1, 0.2f, Objects.hotbarSlot);
			}
			
			if(context.getInventory().items[i].value != 0 && context.getInventory().items[i].item != null)
			{
				ItemRenderContext itemRenderContext = new ItemRenderContext();
				context.getInventory().items[i].item.render(itemRenderContext);
				Texture tex = itemRenderContext.texture;
				GUIUtill.drawSquare((i-5)*0.2f + (4f/200f), -1 + (4f/200f), (0.2f) * (16f/20f), tex, 0, 0, 1);
				
				if(context.getInventory().items[i].value > 1)
				{
					GUIUtill.drawString("" + context.getInventory().items[i].value, (i-5)*0.2f + (4f/200f), -1 + (4f/200f), (0.2f) * (16f/20f) * 0.5f);
				}
			}
		}
	}

}
