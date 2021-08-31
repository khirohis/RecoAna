package net.hogelab.android.recoana.audio;

import android.util.Log;

import net.hogelab.android.recoana.AppExecutor;

public class RecordingThread {
    private static final String TAG = RecordingThread.class.getSimpleName();

    public interface Callback {
        void onBuffer(byte[] buffer);
        void onFinish(boolean completed);
    }


    private volatile Callback callback;

    private long recordingBufferCount;
    private long readCount;

    private AudioRecorder audioRecorder;
    private volatile boolean recording;
    private volatile boolean completed;


    public RecordingThread() {
    }


    public Callback getCallback() {
        return callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }


    public void start(long recordingMillisec) {
        recordingBufferCount = recordingMillisec / RecordSetting.MILLISEC_PER_BUFFER;
        readCount = 0;

        audioRecorder = new AudioRecorder();
        recording = true;
        completed = false;

        AppExecutor.getWorkerExecutor().execute(this::recordingLoop);
    }

    public void stop() {
        recording = false;
        completed = false;
    }


    private void recordingLoop() {
        audioRecorder.start();

        while (recording) {
            if (readCount >= recordingBufferCount) {
                completed = true;
                break;
            }

            byte[] buffer = new byte[RecordSetting.BYTES_PER_BUFFER];
            int readSize = audioRecorder.read(buffer);
            if (readSize == buffer.length) {
                readCount++;

                AppExecutor.getMainExecutor().execute(() -> {
                    callbackOnBuffer(buffer);
                });
            } else {
                // TODO: error handling
                Log.d(TAG, "AudioRecorder#read returned " + readSize);
                break;
            }
        }

        audioRecorder.stop();

        AppExecutor.getMainExecutor().execute(this::callbackOnFinish);
    }


    private void callbackOnBuffer(byte[] buffer) {
        if (callback != null) {
            callback.onBuffer(buffer);
        }
    }

    private void callbackOnFinish() {
        if (callback != null) {
            callback.onFinish(completed);
        }
    }
}