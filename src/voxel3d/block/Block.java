package voxel3d.block;

import java.io.IOException;
import java.util.TreeMap;

import voxel3d.block.all.*;
import voxel3d.block.context.*;
import voxel3d.data.DataInputStream;
import voxel3d.data.DataOutputStream;
import voxel3d.data.DataStreamable;
import voxel3d.global.Debug;
import voxel3d.gui.HUDInteractable;
import voxel3d.item.BlockItem;
import voxel3d.item.Item;
import voxel3d.level.ChunkMeshBuilder;
import voxel3d.physics.AABB;
import voxel3d.utility.Vector3d;


public abstract class Block implements DataStreamable {
	
	private static TreeMap<Integer, Block> idInstanceMap = new TreeMap<Integer, Block>();
	
	public static void setBlockDeserializerForLegacyID(Block block)
	{
		if(idInstanceMap.containsKey(block.getLegacyID()))
		{
			Debug.err("Multiple blocks with same ID: " + block.getName() + " - " + idInstanceMap.get(block.getLegacyID()).getName());
			throw new RuntimeException();
		}
		
		idInstanceMap.put(block.getLegacyID(), block);
		
		Item.setItemDeserializerForName(new BlockItem(block));
	}
	
	public static Block GetOutOfBounds()
	{
		return AirBlock.getInstance();
	}
	
	public static Block GetNotYetLoaded()
	{
		return DirtBlock.getInstance();
	}
	
	public static Block GetInstanceFromData(DataInputStream stream) throws IOException
	{
		int legacyBlockID = stream.readInt();
		Block instancer = idInstanceMap.get(legacyBlockID);
		if(instancer == null)
		{
			System.err.println("Unknown block with ID: " + String.format("0x%08X", legacyBlockID));
			throw new RuntimeException();
		}
		instancer = instancer.getDataStreamableInstance();
		instancer.read(stream);
		return instancer;
	}
	
	public Item getItem()
	{
		return Item.GetInstanceFromName(getName());
	}
	
	public Block getDataStreamableInstance()
	{
		return this;
	}
	
	public void setFaceDirection(Vector3d direction)
	{
		
	}
	
	public abstract int getLegacyID();
	public abstract String getName();
	public abstract boolean hasCollisionbox();
	public abstract boolean hasSelectionBox();
	public abstract boolean isSolidBlock();
	public abstract boolean isLightTransparent();
	public abstract boolean hasGeometry();
	public abstract void getSelectionBox(int x, int y, int z, AABB aabb);
	public abstract void getCollisionBox(int x, int y, int z, AABB aabb);
	
	@Override
	public void read(DataInputStream stream) throws IOException
	{
		
	}

	@Override
	public void write(DataOutputStream stream) 
	{
		
	}
	
	public float getRedLight()
	{
		return 0;
	}
	
	public float getGreenLight()
	{
		return 0;
	}
	
	public float getBlueLight()
	{
		return 0;
	}
	
	public float getSkyLight()
	{
		return 0;
	}
	
	public void render(ChunkMeshBuilder mb, int x, int y, int z)
	{
		
	}
	
	public double getBreakTime(Item tool)
	{
		Debug.unimplemented("break time " + getName());
		return 0.5;
	}
	
	public void onBreak(BlockOnBreakContext context)
	{
		context.dropItem(getItem());
	}
	
	public HUDInteractable getHUD()
	{
		return null;
	}
	
	public void onUse(BlockOnUseContext context)
	{
		
	}
	
	public String getInfo()
	{
		return getName();
	}

}
