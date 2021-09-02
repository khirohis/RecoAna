package net.hogelab.android.recoana.audio;

import android.media.AudioFormat;
import android.media.MediaRecorder;

public class RecordSetting {
//    public static final int AUDIO_SOURCE = MediaRecorder.AudioSource.DEFAULT;
    public static final int AUDIO_SOURCE = MediaRecorder.AudioSource.VOICE_COMMUNICATION;
    public static final int SAMPLING_RATE = 48000; // 48kHz
    public static final int AUDIO_CHANNELS = AudioFormat.CHANNEL_IN_MONO;
    public static final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;

    // 480 frames
    public static final int FRAMES_PER_BUFFER = 480;

    // buffer size = FRAMES_PER_BUFFER * channels(1) * 16bit(2bytes)
    public static final int BYTES_PER_BUFFER = FRAMES_PER_BUFFER * 1 * 2;

    // milliseconds per buffer = 1sec / (SAMPLING_RATE / FRAMES_PER_BUFFER)
    public static final int MILLISEC_PER_BUFFER = 1000 / (SAMPLING_RATE / FRAMES_PER_BUFFER);

    // default recording milliseconds
    public static final int DEFAULT_RECORDING_MILLISECONDS = 5 * 1000;
}