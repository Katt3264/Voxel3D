package voxel3d.audio;

import static org.lwjgl.openal.AL.*;
import static org.lwjgl.openal.ALC10.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.lwjgl.openal.ALC.*;
import static org.lwjgl.openal.AL11.*;

import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;

import voxel3d.entity.all.Player;
import voxel3d.utility.Vector3d;

public class AudioManager {
	
	private long context;
	private long device;
	private AudioListener listener;
	
	private List<AudioSource> globalSources = new ArrayList<AudioSource>();
	private List<AudioSource> localSources = new ArrayList<AudioSource>();
	
	public AudioManager()
	{
		init();
		listener = new AudioListener();
	}
	
	private void init()
	{
		String defaultDeviceName = alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER);
		device = alcOpenDevice(defaultDeviceName);
		ALCCapabilities deviceCaps = createCapabilities(device);
		
		int[] attributes = {0};
		context = alcCreateContext(device, attributes);
		
		alcMakeContextCurrent(context);
		ALCapabilities alc = createCapabilities(deviceCaps);
		
		if(!alc.OpenAL10) 
		{
			throw new IllegalStateException("Audio library not supported!");
		}
		
		alDistanceModel(AL_EXPONENT_DISTANCE);
	}
	
	public void playGlobalSound(AudioClip clip)
	{
		Iterator<AudioSource> iter = globalSources.iterator();
		while(iter.hasNext())
		{
			AudioSource source = iter.next();
		    if(!source.isPlaying())
		    {
		    	source.destroy();
		        iter.remove();
		    }
		}
		
		if(globalSources.size() < 32)
		{
			AudioSource source = new AudioSource(true);
			source.playAudioClip(clip);
			globalSources.add(source);
		}
	}
	
	public AudioSource playSound(Vector3d pos, AudioClip clip)
	{
		Iterator<AudioSource> iter = localSources.iterator();
		while(iter.hasNext())
		{
			AudioSource source = iter.next();
		    if(!source.isPlaying())
		    {
		    	source.destroy();
		        iter.remove();
		    }
		}
		
		if(localSources.size() < 32)
		{
			AudioSource source = new AudioSource(false);
			source.setPosition((float)pos.x, (float)pos.y, (float)pos.z);
			source.playAudioClip(clip);
			localSources.add(source);
			return source;
		}
		return null;
	}
	
	public void setListener(Player player)
	{
		listener.setOrientation(player.camera);
		listener.setPosition((float)player.camera.position.x, (float)player.camera.position.y, (float)player.camera.position.z);
		listener.setVelocity((float)player.velocity.x, (float)player.velocity.y, (float)player.velocity.z);
	}
	
	public void destroy()
	{
		alcDestroyContext(context);
		alcCloseDevice(device);
	}

}
