package voxel3d.gui;

import voxel3d.data.Crafting;
import voxel3d.data.ItemValue;
import voxel3d.data.Recipe;
import voxel3d.global.Input;
import voxel3d.global.Objects;
import voxel3d.item.Item;
import voxel3d.utility.GUIUtill;

public class CraftingGUI implements HUDInteractable{
	
	private Recipe[] recipes = new Recipe[8];
	private ItemSlotGUI[] craftableSlots = new ItemSlotGUI[8];
	
	private ItemSlotGUI[] craftingIngredients = new ItemSlotGUI[3*3];
	private ItemSlotGUI[] craftOut = new ItemSlotGUI[3*3];
	
	private Button craftButton;
	
	private Button goLeft;
	private Button goRight;
	int selectedIndex = 0;
	int selectedPage = 0;
	
	
	private Recipe selectedRecipe;
	
	private float w = 44;
	private float h = 22;
	
	
	private float xSize = 1.5f;
	private float ySize = (h / w) * xSize;
	
	private float pixelSize = (xSize / w);
	private float cellSize = 4 * pixelSize;
	
	private float x = -(xSize / 2f);
	private float y = 0;
	
	public CraftingGUI()
	{
		
		for(int xx = 0; xx < 8; xx++)
		{
			craftableSlots[xx] = new ItemSlotGUI(x + cellSize*1.5f + xx*cellSize, y + cellSize*4, cellSize);
		}
		
		goLeft = new Button(x + cellSize*0.5f, y + cellSize*4, cellSize, cellSize);
		goLeft.mouseOff = Objects.leftButton;
		goLeft.mouseOn = Objects.leftButtonSelected;
		
		goRight = new Button(x + cellSize*9.5f, y + cellSize*4, cellSize, cellSize);
		goRight.mouseOff = Objects.rightButton;
		goRight.mouseOn = Objects.rightButtonSelected;
		
		for(int xx = 0; xx < 3; xx++)
		{
			for(int yy = 0; yy < 3; yy++)
			{
				craftingIngredients[xx + yy*3] = new ItemSlotGUI(x + cellSize*0.5f + xx*cellSize, y + cellSize*0.5f + yy*cellSize, cellSize);
			}
		}
		
		for(int xx = 0; xx < 3; xx++)
		{
			for(int yy = 0; yy < 3; yy++)
			{
				craftOut[xx + yy*3] = new ItemSlotGUI(x + cellSize*7.5f + xx*cellSize, y + cellSize*0.5f + yy*cellSize, cellSize);
			}
		}
		
		craftButton = new Button(x + cellSize*4.5f, y + cellSize*1.5f, 2*cellSize,cellSize);
		craftButton.mouseOff = Objects.craftButton;
		craftButton.mouseOn = Objects.craftButtonSelected;
		
	}
	
	
	public void update(HUDUpdateContext context)
	{
		Recipe[] avalibleRecipes = Crafting.getRecipesForStation(context.getPlayerFacing());
		
		goLeft.update();
		if(goLeft.selected && Input.hit.isButtonPress()) {selectedPage -= 1;}
		
		goRight.update();
		if(goRight.selected && Input.hit.isButtonPress()) {selectedPage += 1;}
		
		selectedPage = Math.max(0, Math.min(Math.floorDiv(avalibleRecipes.length - 1, 8), selectedPage));
		
		
		for(int i = 0; i < 8; i++)
		{
			if(selectedPage*8 + i < avalibleRecipes.length)
			{
				recipes[i] = avalibleRecipes[selectedPage*8 + i];
			}
			else
			{
				recipes[i] = null;
			}
		}
		
		for(int i = 0; i < craftableSlots.length; i++)
		{
			craftableSlots[i].update();
			if(craftableSlots[i].selected && Input.hit.isButtonPress()) 
			{
				selectedIndex = i;
			}
		}
		
		for(ItemSlotGUI igui : craftingIngredients)
		{
			igui.update();
		}
		
		for(ItemSlotGUI igui : craftOut)
		{
			igui.update();
		}
		
		
		if(selectedPage*8 + selectedIndex < avalibleRecipes.length)
		{
			selectedRecipe = avalibleRecipes[selectedPage*8 + selectedIndex];
		}
		else
		{
			selectedRecipe = null;
		}
		
		craftButton.update();
		if(selectedRecipe != null && craftButton.selected && Input.hit.isButtonPress()) 
		{
			if(context.getInventory().canCraft(selectedRecipe))
			{
				context.getInventory().craft(selectedRecipe);
			}
		}

	}
	
	
	public void draw(HUDRenderContext context)
	{
		GUIUtill.drawRect(x, y, xSize, ySize, Objects.craftingGUI);
		
		
		for(int i = 0; i < craftableSlots.length; i++)
		{
			if(recipes[i] != null)
			{
				ItemValue iv = new ItemValue();
				iv.item = recipes[i].icon;
				//iv.value = recipes[i].outputValue;
				iv.value = 1;
				craftableSlots[i].draw(iv);
			}
			else
			{
				craftableSlots[i].draw(new ItemValue());
			}
			
			if(craftableSlots[i].selected && recipes[i] != null)
			{
				Item item = recipes[i].icon;
				if(item != null)
				{
					context.setGuiHint(item.getName());
				}
			}
			
		}
		
		for(int i = 0; i < craftingIngredients.length; i++)
		{
			if(selectedRecipe != null)
			{
				if(i < selectedRecipe.inItems.length)
				{
					ItemValue iv = new ItemValue();
					iv.item = selectedRecipe.inItems[i];
					iv.value = selectedRecipe.inValues[i];
					craftingIngredients[i].draw(iv);
				}
				else
				{
					craftingIngredients[i].draw(new ItemValue());
				}
			}
			else
			{
				craftingIngredients[i].draw(new ItemValue());
			}
			
			if(craftingIngredients[i].selected && selectedRecipe != null)
			{
				if(selectedRecipe.inItems.length > i)
				{
					Item item = selectedRecipe.inItems[i];
					if(item != null)
					{
						context.setGuiHint(item.getName());
					}
				}
			}
		}
		
		for(int i = 0; i < craftOut.length; i++)
		{
			if(selectedRecipe != null)
			{
				if(i < selectedRecipe.outItems.length)
				{
					ItemValue iv = new ItemValue();
					iv.item = selectedRecipe.outItems[i];
					iv.value = selectedRecipe.outValues[i];
					craftOut[i].draw(iv);
				}
				else
				{
					craftOut[i].draw(new ItemValue());
				}
			}
			else
			{
				craftOut[i].draw(new ItemValue());
			}
			
			if(craftOut[i].selected && selectedRecipe != null)
			{
				if(selectedRecipe.outItems.length > i)
				{
					Item item = selectedRecipe.outItems[i];
					if(item != null)
					{
						context.setGuiHint(item.getName());
					}
				}
			}
		}
		
		craftButton.draw();
		goRight.draw();
		goLeft.draw();
	}
}
