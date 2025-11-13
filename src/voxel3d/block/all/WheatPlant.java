package voxel3d.block.all;

import java.io.IOException;
import java.util.Random;

import voxel3d.block.Block;
import voxel3d.block.BlockSimulable;
import voxel3d.block.XBlock;
import voxel3d.block.context.BlockOnBreakContext;
import voxel3d.block.context.BlockOnSimulateContext;
import voxel3d.block.utility.BlockBreakTimeUtility;
import voxel3d.data.DataInputStream;
import voxel3d.data.DataOutputStream;
import voxel3d.item.Item;
import voxel3d.item.all.WheatItem;
import voxel3d.item.all.WheatSeedItem;
import voxel3d.utility.Vector2f;

public class WheatPlant extends XBlock implements BlockSimulable {
	
	private static Vector2f[][] uv = new Vector2f[][] {
		XBlock.getUVFromName("Wheat 0"),
		XBlock.getUVFromName("Wheat 1"),
		XBlock.getUVFromName("Wheat 2"),
		XBlock.getUVFromName("Wheat 3"),
		XBlock.getUVFromName("Wheat 4"),
		XBlock.getUVFromName("Wheat 5"),
		XBlock.getUVFromName("Wheat 6"),
		XBlock.getUVFromName("Wheat 7"),
	};
	
	static {
		Block.setBlockDeserializerForLegacyID(new WheatPlant());
	}
	
	private static double halfLife = 30;
	
	private int stage = 0;
	private Random random = new Random();
	
	public static WheatPlant getInstance()
	{
		return new WheatPlant();
	}
	
	@Override
	public Block getBlockInstance()
	{
		return getInstance();
	}
	
	@Override
	public int getLegacyID()
	{
		return 0x090930e8;
	}
	
	@Override
	public String getName()
	{
		return "Wheat plant";
	}
	
	@Override
	public void onBreak(BlockOnBreakContext context)
	{
		if(stage == uv.length - 1)
		{
			context.dropItem(WheatItem.getInstance());
		}
		else
		{
			context.dropItem(WheatSeedItem.getInstance());
		}
	}
	
	@Override
	public double getBreakTime(Item tool)
	{
		return BlockBreakTimeUtility.getBreakTimeFoliage(tool);
	}
	
	@Override
	public void onSimulate(BlockOnSimulateContext context) 
	{
		if(stage != uv.length - 1 && random.nextDouble() > Math.pow(0.5, context.getDeltaTime() / halfLife))
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
