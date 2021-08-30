package net.hogelab.android.recoana.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import net.hogelab.android.recoana.audio.RecordSetting;
import net.hogelab.android.recoana.audio.RecordingThread;
import net.hogelab.android.recoana.repository.RecordedFileRepository;

public class RecordingViewModel extends AndroidViewModel
        implements RecordingThread.Callback {
    private static final String TAG = RecordingViewModel.class.getSimpleName();

    public enum RecordingStatus {
        WAITING,
        RECORDING,
        SUCCESS_STOP,
        INTERRUPTION_STOP,
        FAILURE_STOP,
    }


    private RecordedFileRepository recordedFileRepository;

    private MutableLiveData<RecordingStatus> recordingStatusData = new MutableLiveData<>(RecordingStatus.WAITING);

    private RecordingThread recordingThread;


    public RecordingViewModel(Application application) {
        super(application);

        recordedFileRepository = new RecordedFileRepository();

        recordingThread = new RecordingThread(RecordSetting.DEFAULT_RECORDING_MILLISECONDS);
        recordingThread.setCallback(this);
    }

    @Override
    protected void onCleared() {
    }


    @Override
    public void onBuffer(byte[] buffer) {
    }

    @Override
    public void onFinish(boolean completed) {
        if (completed) {
            recordingStatusData.postValue(RecordingStatus.SUCCESS_STOP);
        } else {
            recordingStatusData.postValue(RecordingStatus.INTERRUPTION_STOP);
        }
    }


    public LiveData<RecordingStatus> getRecordingStatusData() {
        return recordingStatusData;
    }


    public void startRecording() {
        recordingThread.start();
    }

    public void stopRecording() {
        recordingThread.stop();
    }
}