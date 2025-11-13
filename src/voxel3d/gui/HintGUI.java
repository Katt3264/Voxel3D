package voxel3d.gui;

import voxel3d.global.Input;
import voxel3d.global.Objects;
import voxel3d.graphics.GUIUtill;

public class HintGUI {
	
	private static final float height = 0.05f;
	
	
	public HintGUI()
	{	
		

	}
	
	
	public void update(HUDUpdateContext context)
	{	
		
	}
	
	
	public void draw(HUDRenderContext context)
	{
		
		if(context.getGuiHint().length() == 0)
			return;
		
		float characters = context.getGuiHint().length();
		float width = height * Glyph.widthToHeight * characters;
		
		float x = Input.getNormalMouseX() - (width / 2f);
		float y = Input.getNormalMouseY() - height * 2f;
		
		float padding = height * 1f/8f;
		float padding2 = 2f * height * 1f/8f;
		
		GUIUtill.drawRect(x-padding, y-padding, width+padding2, height+padding2, Objects.hintGUI);
		GUIUtill.drawString("" + context.getGuiHint(), x, y, height);
	}

}
