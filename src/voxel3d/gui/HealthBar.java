package voxel3d.gui;

import voxel3d.global.Objects;
import voxel3d.utility.GUIUtill;

public class HealthBar {
	
	
	public void draw(HUDRenderContext context)
	{
		for(int i = 0; i < 20; i++)
		{
			int health = 0;
			if(context.getPlayer().health > i*5) {health++;}
			if(context.getPlayer().health - 100 > i*5) {health++;}
			
			if(health == 0)
			{
				GUIUtill.drawSquare((i-10)*0.1f, -1 + 0.2f, 0.1f, Objects.healthEmpty);
			}
			else if(health == 1)
			{
				GUIUtill.drawSquare((i-10)*0.1f, -1 + 0.2f, 0.1f, Objects.health);
			} 
			else if(health == 2)
			{
				GUIUtill.drawSquare((i-10)*0.1f, -1 + 0.2f, 0.1f, Objects.healthGold);
			}
			
			int hunger = 0;
			if(context.getPlayer().hunger > i*5) {hunger++;}
			if(context.getPlayer().hunger - 100 > i*5) {hunger++;}
			
			if(hunger == 0)
			{
				GUIUtill.drawSquare((i-10)*0.1f, -1 + 0.3f, 0.1f, Objects.hungerEmpty);
			}
			else if(hunger == 1)
			{
				GUIUtill.drawSquare((i-10)*0.1f, -1 + 0.3f, 0.1f, Objects.hunger);
			}
			else if(hunger == 2)
			{
				GUIUtill.drawSquare((i-10)*0.1f, -1 + 0.3f, 0.1f, Objects.hungerGold);
			}
		}
	}

}
