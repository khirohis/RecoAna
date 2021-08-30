package net.hogelab.android.recoana.audio;

import net.hogelab.android.recoana.AppExecutor;

public class RecordingThread {
    private static final String TAG = RecordingThread.class.getSimpleName();

    public interface Callback {
        void onBuffer(byte[] buffer);
        void onFinish(boolean completed);
    }


    private long recordingMillisec;
    private AudioRecorder audioRecorder;

    private long readCount;

    private volatile Callback callback;
    private volatile boolean recording;
    private volatile boolean completed;


    public RecordingThread(long recordingMillisec) {
        this.recordingMillisec = recordingMillisec;
        audioRecorder = new AudioRecorder();
    }


    public Callback getCallback() {
        return callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }


    public void start() {
        readCount = recordingMillisec / RecordSetting.MILLISEC_PER_BUFFER;

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
            if (readCount <= 0) {
                completed = true;
                break;
            }

            byte[] buffer = new byte[RecordSetting.BYTES_PER_BUFFER];
            int readSize = audioRecorder.read(buffer);
            if (readSize == buffer.length) {
                readCount--;

                AppExecutor.getMainExecutor().execute(() -> {
                    callbackOnBuffer(buffer);
                });
            } else {
                // error?
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