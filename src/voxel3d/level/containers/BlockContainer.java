package voxel3d.level.containers;

import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map.Entry;

import voxel3d.block.Block;
import voxel3d.block.BlockSimulable;
import voxel3d.data.DataInputStream;
import voxel3d.data.DataOutputStream;
import voxel3d.data.DataStreamable;
import voxel3d.global.Settings;
import voxel3d.global.Time;
import voxel3d.utility.MathX;
import voxel3d.utility.Vector3I;

public class BlockContainer /*implements DataStreamable*/{
	
	/*private BlockOctree blocks;
	
	private Collection<Entry<Vector3I, BlockSimulable>> simulableBlocks = Collections.<Entry<Vector3I, BlockSimulable>>emptyList();
	
    
    private boolean empty;
    private boolean solid;
    private long lastModified;
	
    public boolean wip = false;
    private boolean generated = false;
    
    public BlockContainer()
    {
    	blocks = new BlockOctree((byte) Settings.CHUNK_SIZE_LOG, Block.GetNotYetLoaded());
    }
    
    public void set(Block[] blocks)
    {
    	for(int x = 0; x < Settings.CHUNK_SIZE; x++)
    	{
    		for(int y = 0; y < Settings.CHUNK_SIZE; y++)
        	{
    			for(int z = 0; z < Settings.CHUNK_SIZE; z++)
    	    	{
    				int index = MathX.getXYZ(x, y, z);
    	    		this.blocks.set(x, y, z, blocks[index]);
    	    	}
        	}
    	}
    	computeProperties();
    }
    
    private void computeProperties()
    {
    	Collection<Entry<Vector3I, BlockSimulable>> simulables = new LinkedList<Entry<Vector3I, BlockSimulable>>();
    	
    	boolean s = true;
    	boolean e = true;
    	
    	for(int x = 0; x < Settings.CHUNK_SIZE; x++)
    	{
    		for(int y = 0; y < Settings.CHUNK_SIZE; y++)
        	{
    			for(int z = 0; z < Settings.CHUNK_SIZE; z++)
    	    	{
					Block block = this.blocks.get(x, y, z);
					if(block.hasGeometry()) {e = false;}
					if(!block.isSolidBlock()) {s = false;}
					if(block instanceof BlockSimulable)
					{
						simulables.add(new SimpleEntry<Vector3I, BlockSimulable>(new Vector3I(x, y, z), (BlockSimulable)block));
					}
		    	}
        	}
    	}
    	this.empty = e;
    	this.solid = s;
    	this.simulableBlocks = simulables;
    	
    	lastModified = Time.getAtomTime();
    	generated = true;
    }
    
    public void blockInternalStateChanged()
    {
    	lastModified = Time.getAtomTime();
    }
    
    public Block getBlock(int x, int y, int z) 
    {
    	if(!generated)
    		return Block.GetNotYetLoaded();
    		
    	synchronized(blocks)
		{
	    	return blocks.get(x, y, z);
		}
    }
    
	public void setBlock(int x, int y, int z, Block block) 
	{
		if(!generated)
    		return;
		
		synchronized(blocks)
		{
			blocks.set(x, y, z, block);
			computeProperties();
	        lastModified = Time.getAtomTime();
		}
    }
	
	public long getLastModified()
	{
		return lastModified;
	}
	
	public boolean getSolid()
	{
		return solid;
	}
	
	public boolean getEmpty()
	{
		return empty;
	}
	
	public boolean isGenerated()
	{
		return generated;
	}
	
	public Collection<Entry<Vector3I, BlockSimulable>> getSimulableBlocks()
	{
		return simulableBlocks;
	}

	@Override
	public void read(DataInputStream stream) throws IOException
	{
		blocks.read(stream);
		computeProperties();
	}

	@Override
	public void write(DataOutputStream stream)
	{
		blocks.write(stream);
	}*/
}
