package voxel3d.gui;

import voxel3d.block.Block;
import voxel3d.global.Objects;
import voxel3d.graphics.GUIUtill;

public class BlockHintGUI {
	
	
	public BlockHintGUI()
	{	
		

	}
	
	
	public void update(HUDUpdateContext context)
	{	
		
	}
	
	
	public void draw(HUDRenderContext context)
	{
		Block block = context.getPlayerFacing();
		//if(block == null)
			//return;
		
		String[] text = block.getInfo().split("\n");
		float columns = 0;
		float rows = text.length;
		for(String s : text)
		{
			columns = Math.max(columns, s.length());
		}
		
		float textHeight = 0.1f;
		
		
		float height = textHeight * rows;
		float width = textHeight * Glyph.widthToHeight * columns;
		
		float padding = textHeight * 1f/8f;
		float padding2 = 2f * textHeight * 1f/8f;
		
		float x = -(width / 2f);
		float y = 1;
		
		
		GUIUtill.drawRect(x-padding, y-padding2-height, width+padding2, height+padding2, Objects.hintGUI);
		
		for(int line = 0; line < rows; line++)
		{
			GUIUtill.drawString(text[line], x, y - (line+1) * textHeight - padding, textHeight);
		}
	}

}
