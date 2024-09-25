package voxel3d.gui;

import voxel3d.data.ItemValue;
import voxel3d.global.Input;
import voxel3d.global.Objects;
import voxel3d.graphics.Texture;
import voxel3d.item.context.ItemRenderContext;
import voxel3d.utility.GUIUtill;

public class ItemSlotGUI {
	
	private float x, y, size;
	boolean selected = false;
	
	public ItemSlotGUI(float x, float y, float size)
	{
		this.x = x;
		this.y = y;
		this.size = size;
	}
	
	
	public void update()
	{
		if(Input.getNormalMouseX() > x && Input.getNormalMouseX() < x + size && Input.getNormalMouseY() > y && Input.getNormalMouseY() < y + size)
		{
			selected = true;
		}
		else
		{
			selected = false;
		}
	}
	
	public void draw(ItemValue item)
	{
		GUIUtill.drawSquare(x, y, size, selected ? Objects.itemSlotSelected : Objects.itemSlot, 0, 0, 1);
		
		if(item.item != null && item.value != 0)
		{
			ItemRenderContext itemRenderContext = new ItemRenderContext();
			item.item.render(itemRenderContext);
			Texture tex = itemRenderContext.texture;
			GUIUtill.drawSquare(x + (2f/20f) * size, y + (2f/20f) * size, size * (16f/20f), tex, 0, 0, 1);
			
			if(item.value > 1)
			{
				GUIUtill.drawString("" + item.value, x + (2f/20f) * size, y + (2f/20f) * size, size * (16f/20f) * 0.5f);
			}
		}
		else if(item.item == null && item.value != 0)
		{
			//GUIUtill.drawSquare(x + (2f/20f) * size, y + (2f/20f) * size, size * (16f/20f), Item.getItemByName("Missing").getTexture(), new Vector2f(0,0), 1);

			{
				GUIUtill.drawString("" + item.value, x + (2f/20f) * size, y + (2f/20f) * size, size * (16f/20f) * 0.5f);
			}
		}
	}
}
