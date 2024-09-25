package voxel3d.audio;

import static org.lwjgl.openal.AL10.*;

import voxel3d.entity.Camera;


public class AudioListener {
	
	public AudioListener() 
	{
        alListener3f(AL_POSITION, 0, 0, 0);
        alListener3f(AL_VELOCITY, 0, 0, 0);
    }

    public void setOrientation(Camera camera) {
        float[] data = new float[6];
        data[0] = (float)-camera.forward.x;
        data[1] = (float)-camera.forward.y;
        data[2] = (float)-camera.forward.z;
        data[3] = (float)camera.up.x;
        data[4] = (float)camera.up.y;
        data[5] = (float)camera.up.z;
        alListenerfv(AL_ORIENTATION, data);
    }

    public void setPosition(float x, float y, float z) {
        alListener3f(AL_POSITION, x, y, z);
    }

    public void setVelocity(float x, float y, float z) {
        alListener3f(AL_VELOCITY, x, y, z);
    }

}
