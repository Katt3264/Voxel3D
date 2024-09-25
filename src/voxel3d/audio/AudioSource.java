package voxel3d.audio;

import static org.lwjgl.openal.AL10.*;


public class AudioSource {
	
	private final int sourceId;

    public AudioSource(boolean global) 
    {
        this.sourceId = alGenSources();
        //alSourcei(sourceId, AL_LOOPING, loop ? AL_TRUE : AL_FALSE);
        
        if(global)
        {
        	alSourcei(sourceId, AL_SOURCE_RELATIVE, AL_TRUE);
        }
        else
        {
        	alSourcei(sourceId, AL_SOURCE_RELATIVE, AL_FALSE);
        }
        
    }

    public void destroy() 
    {
        stop();
        alDeleteSources(sourceId);
    }

    public boolean isPlaying() 
    {
        return alGetSourcei(sourceId, AL_SOURCE_STATE) == AL_PLAYING;
    }

    public void pause() 
    {
        alSourcePause(sourceId);
    }

    public void play() 
    {
        alSourcePlay(sourceId);
    }

    public void playAudioClip(AudioClip clip) 
    {
        stop();
        alSourcei(sourceId, AL_BUFFER, clip.getID());
        play();
    }

    public void setGain(float gain) 
    {
        alSourcef(sourceId, AL_GAIN, gain);
    }

    public void setPosition(float x, float y, float z) 
    {
        alSource3f(sourceId, AL_POSITION, x, y, z);
    }
    
    public void setVelocity(float x, float y, float z) 
    {
        alSource3f(sourceId, AL_VELOCITY, x, y, z);
    }

    public void stop() 
    {
        alSourceStop(sourceId);
    }

}
