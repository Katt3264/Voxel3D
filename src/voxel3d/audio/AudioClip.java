package voxel3d.audio;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.stb.STBVorbis.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.stb.STBVorbisInfo;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;



public class AudioClip {
	
	private final int buffer;
	private ShortBuffer pcm;
	
	public static AudioClip get(String path)
	{
		return new AudioClip(path);
	}
	
	private AudioClip (String path)
	{
		buffer = alGenBuffers();
		
		STBVorbisInfo info = STBVorbisInfo.malloc();
		ShortBuffer pcm = readVorbis(path, info);
		
		alBufferData(buffer, info.channels() == 1 ? AL_FORMAT_MONO16 : AL_FORMAT_STEREO16, pcm, info.sample_rate());
	}
	
	private ShortBuffer readVorbis(String filePath, STBVorbisInfo info) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer error = stack.mallocInt(1);
            long decoder = stb_vorbis_open_filename(filePath, error, null);
            if (decoder == NULL) {
                throw new RuntimeException("Failed to open ogg Vorbis file. Error: " + error.get(0));
            }

            stb_vorbis_get_info(decoder, info);

            int channels = info.channels();

            int lengthSamples = stb_vorbis_stream_length_in_samples(decoder);

            ShortBuffer result = MemoryUtil.memAllocShort(lengthSamples * channels);

            result.limit(stb_vorbis_get_samples_short_interleaved(decoder, channels, result) * channels);
            stb_vorbis_close(decoder);

            return result;
        }
    }
	
	public int getID()
	{
		return buffer;
	}
	
	public void destroy() {
        alDeleteBuffers(this.buffer);
        if (pcm != null) {
            MemoryUtil.memFree(pcm);
        }
    }

}
