package voxel3d.gui;

import voxel3d.global.Input;
import voxel3d.graphics.GUIUtill;
import voxel3d.graphics.Texture;

public class Button {
	
	private float x, y, sizeX, sizeY;
	boolean selected = false;
	
	public Texture mouseOn;
	public Texture mouseOff;
	
	public Button(float x, float y, float sizeX, float sizeY)
	{
		this.x = x;
		this.y = y;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
	}
	
	
	public void update()
	{
		if(Input.getNormalMouseX() > x && Input.getNormalMouseX() < x + sizeX && Input.getNormalMouseY() > y && Input.getNormalMouseY() < y + sizeY)
		{
			selected = true;
		}
		else
		{
			selected = false;
		}
	}
	
	public void draw()
	{
		GUIUtill.drawRect(x, y, sizeX, sizeY, selected ? mouseOn : mouseOff);
	}

}
