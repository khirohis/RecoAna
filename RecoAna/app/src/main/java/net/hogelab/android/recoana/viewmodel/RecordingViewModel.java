package net.hogelab.android.recoana.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import net.hogelab.android.recoana.audio.RecordSetting;
import net.hogelab.android.recoana.audio.RecordingThread;
import net.hogelab.android.recoana.model.repository.RecordedDataRepository;
import net.hogelab.android.recoana.model.datastore.DataStore;

public class RecordingViewModel extends AndroidViewModel
        implements RecordingThread.Callback {
    private static final String TAG = RecordingViewModel.class.getSimpleName();

    public enum RecordingStatus {
        WAITING,
        RECORDING,
        SUCCESS_STOP,
        INTERRUPTION_STOP,
        ERROR_STOP,
    }


    private final MutableLiveData<RecordingStatus> recordingStatusData = new MutableLiveData<>(RecordingStatus.WAITING);

    private final RecordedDataRepository recordedDataRepository;

    private final DataStore recordingDataStore;
    private final RecordingThread recordingThread;


    public RecordingViewModel(Application application) {
        super(application);

        recordedDataRepository = new RecordedDataRepository(application);

        recordingDataStore = recordedDataRepository.getTemporaryDataStore();

        recordingThread = new RecordingThread();
        recordingThread.setCallback(this);
    }

    @Override
    protected void onCleared() {
    }


    @Override
    public void onBuffer(byte[] buffer) {
        Log.d(TAG, "onBuffer: buffer length=" + buffer.length);

        if (!recordingDataStore.write(buffer)) {
            Log.d(TAG, "onBuffer: recordingDataStore.write returned false.");

            // TODO: error handling
        }
    }

    @Override
    public void onFinish(boolean completed) {
        if (completed) {
            recordingStatusData.postValue(RecordingStatus.SUCCESS_STOP);
        } else {
            recordingStatusData.postValue(RecordingStatus.INTERRUPTION_STOP);
        }

        recordingDataStore.close();
        recordedDataRepository.commitTemporaryDataStore();
    }


    public LiveData<RecordingStatus> getRecordingStatusData() {
        return recordingStatusData;
    }


    public void startRecording() {
        if (!recordingDataStore.open()) {
            Log.d(TAG, "startRecording: recordingDataStore.open returned false.");

            // TODO: error handling

            return;
        }

        if (!recordingDataStore.setLength(0)) {
            Log.d(TAG, "startRecording: recordingDataStore.setLength returned false.");

            // TODO: error handling

            return;
        }

        recordingStatusData.postValue(RecordingStatus.RECORDING);
        recordingThread.start(RecordSetting.DEFAULT_RECORDING_MILLISECONDS);
    }

    public void stopRecording() {
        recordingThread.stop();
    }
}