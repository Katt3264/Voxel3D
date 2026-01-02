package voxel3d.block.all;

import java.io.IOException;

import voxel3d.block.Block;
import voxel3d.block.XBlock;
import voxel3d.block.context.BlockOnBreakContext;
import voxel3d.block.context.BlockOnSimulateContext;
import voxel3d.block.utility.BlockBreakTimeUtility;
import voxel3d.data.DataInputStream;
import voxel3d.data.DataOutputStream;
import voxel3d.item.Item;
import voxel3d.item.all.CarrotItem;
import voxel3d.item.all.PlantSeedItem;
import voxel3d.utility.Vector2f;

public class Plant extends XBlock {
	
	private static Vector2f[][] uv = new Vector2f[][] {
		XBlock.getUVFromName("Plant 0"),
		XBlock.getUVFromName("Plant 1"),
		XBlock.getUVFromName("Plant 2"),
		XBlock.getUVFromName("Plant 3"),
		XBlock.getUVFromName("Plant complete"),
	};
	
	static {
		Block.setBlockDeserializerForLegacyID(new Plant());
	}
	
	private int stage = 0;
	
	public static Plant getInstance()
	{
		return new Plant();
	}
	
	@Override
	public Block getBlockInstance()
	{
		return getInstance();
	}
	
	@Override
	public int getLegacyID()
	{
		return 0x3e17af20;
	}
	
	@Override
	public String getName()
	{
		return "Plant";
	}
	
	public float getRedLight()
	{
		if(stage == uv.length - 1)
		{
			return 0.25f;
		}
		else
		{
			return 0;
		}
	}
	
	public float getGreenLight()
	{
		if(stage == uv.length - 1)
		{
			return 0.25f;
		}
		else
		{
			return 0;
		}
	}
	
	@Override
	public void onBreak(BlockOnBreakContext context)
	{
		if(stage == uv.length - 1)
		{
			context.dropItem(CarrotItem.getInstance());
		}
		else
		{
			context.dropItem(PlantSeedItem.getInstance());
		}
	}
	
	@Override
	public double getBreakTime(Item tool)
	{
		return BlockBreakTimeUtility.getBreakTimeFoliage(tool);
	}
	
	@Override
	public void onRandomUpdate(BlockOnSimulateContext context) 
	{
		if(stage != uv.length - 1)
		{
			stage++;
			context.internalStateChange();
		}
	}
	
	@Override
	public void read(DataInputStream stream) throws IOException
	{
		stage = stream.readInt();
	}

	@Override
	public void write(DataOutputStream stream) 
	{
		stream.writeInt(stage);
	}

	@Override
	protected Vector2f[] getUV() 
	{
		return uv[stage];
	}

	

}
