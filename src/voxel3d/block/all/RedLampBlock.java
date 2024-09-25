package voxel3d.block.all;

import java.io.IOException;

import voxel3d.block.Block;
import voxel3d.block.SolidCubeBlock;
import voxel3d.block.context.BlockOnUseContext;
import voxel3d.data.DataInputStream;
import voxel3d.data.DataOutputStream;
import voxel3d.utility.Vector2f;

public class RedLampBlock extends SolidCubeBlock {

	private static Vector2f[] uvOn = SolidCubeBlock.getUVFromName("Red lamp");
	private static Vector2f[] uvOff = SolidCubeBlock.getUVFromName("Red lamp off");
	
	private byte active = 0;
	
	static {
		Block.setBlockDeserializerForLegacyID(new RedLampBlock());
	}
	
	public static RedLampBlock getInstance()
	{
		return new RedLampBlock();
	}
	
	@Override
	public Block getDataStreamableInstance()
	{
		return getInstance();
	}
	
	@Override
	public Vector2f[] getUV(int index) 
	{
		if(active == 0)
			return uvOff;
		else
			return uvOn;
	}

	@Override
	public int getLegacyID() {
		return 0x25282fbd;
	}

	@Override
	public String getName() {
		return "Red lamp";
	}
	
	@Override
	public float getRedLight()
	{
		if(active == 0)
			return 0;
		else
			return 1;
	}
	
	@Override
	public void onUse(BlockOnUseContext context)
	{
		active = (byte) (1 - active);
		context.modifiedVissibleProperty();
	}
	
	@Override
	public void read(DataInputStream stream) throws IOException
	{
		active = stream.readByte();
	}

	@Override
	public void write(DataOutputStream stream) 
	{
		stream.writeByte(active);
	}

}
