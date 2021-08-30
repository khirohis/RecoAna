package net.hogelab.android.recoana.audio;

import android.media.AudioRecord;

public class AudioRecorder {
    private static final String TAG = AudioRecorder.class.getSimpleName();

    private AudioRecord audioRecord;

    public AudioRecorder() {
        audioRecord = new AudioRecord(
                RecordSetting.AUDIO_SOURCE,
                RecordSetting.SAMPLING_RATE,
                RecordSetting.AUDIO_CHANNELS,
                RecordSetting.AUDIO_FORMAT,
                RecordSetting.BYTES_PER_BUFFER);
    }

    public void start() {
        audioRecord.startRecording();
    }

    public void stop() {
        audioRecord.stop();
    }

    public void release() {
        audioRecord.release();
    }


    public int read(byte[] buffer) {
        return read(buffer, 0, buffer.length);
    }

    public int read(byte[] buffer, int offset, int size) {
        int readSize = audioRecord.read(buffer, offset, size);

        return readSize;
    }
}