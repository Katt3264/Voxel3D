package voxel3d.block.all;

import java.io.IOException;

import voxel3d.block.Block;
import voxel3d.block.SolidCubeBlock;
import voxel3d.block.context.BlockOnBreakContext;
import voxel3d.block.utility.BlockBreakTimeUtility;
import voxel3d.data.DataInputStream;
import voxel3d.data.DataOutputStream;
import voxel3d.data.ItemValue;
import voxel3d.global.Objects;
import voxel3d.gui.HUDInteractable;
import voxel3d.item.Item;
import voxel3d.utility.Vector2f;

public class ChestBlock extends SolidCubeBlock {

	private static Vector2f[][] uv = SolidCubeBlock.getUVsFromTopSide("Chest top", "Chest");
	
	public ItemValue[] internal = new ItemValue[10*5];
	
	private ChestBlock()
	{
		for(int i = 0; i < internal.length; i++)
		{
			internal[i] = new ItemValue();
		}
	}
	
	static {
		Block.setBlockDeserializerForLegacyID(getInstance());
	}
	
	public static ChestBlock getInstance()
	{
		return new ChestBlock();
	}
	
	@Override
	public Block getBlockInstance()
	{
		return getInstance();
	}
	
	@Override
	public Vector2f[] getUV(int index) 
	{
		return uv[index];
	}

	@Override
	public int getLegacyID() {
		return 0xbb5005ed;
	}

	@Override
	public String getName() {
		return "Chest";
	}
	
	
	@Override
	public void onBreak(BlockOnBreakContext context)
	{
		context.dropItem(getItem());
		
		for(int s = 0; s < internal.length; s++)
		{
			for(int i = 0; i < internal[s].value; i++)
			{
				context.dropItem(internal[s].item);
			}
			internal[s].item = null;
			internal[s].value = 0;
		}
	}
	
	@Override
	public double getBreakTime(Item tool)
	{
		return BlockBreakTimeUtility.getBreakTimeWood(tool);
	}
	
	@Override
	public HUDInteractable getHUD()
	{
		return Objects.chestHUD;
	}
	
	@Override
	public void read(DataInputStream stream) throws IOException
	{
		for(int i = 0; i < internal.length; i++)
		{
			internal[i].item = stream.readItem();
			internal[i].value = stream.readInt();
		}
	}

	@Override
	public void write(DataOutputStream stream) 
	{
		for(int i = 0; i < internal.length; i++)
		{
			stream.writeItem(internal[i].item);
			stream.writeInt(internal[i].value);
		}
	}

}
