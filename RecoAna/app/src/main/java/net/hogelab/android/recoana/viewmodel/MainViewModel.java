package net.hogelab.android.recoana.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import net.hogelab.android.recoana.repository.RecordedDataRepository;

public class MainViewModel extends AndroidViewModel {
    private static final String TAG = MainViewModel.class.getSimpleName();

    public enum RecordedStatus {
        NOT_RECORDED,
        RECORDED,
    }

    private final MutableLiveData<RecordedStatus> recordedStatusData = new MutableLiveData<>(RecordedStatus.NOT_RECORDED);

    private final RecordedDataRepository recordedDataRepository;


    public MainViewModel(Application application) {
        super(application);

        recordedDataRepository = new RecordedDataRepository(application);
        if (recordedDataRepository.isRecordedDataExists()) {
            recordedStatusData.postValue(RecordedStatus.RECORDED);
        }
    }

    @Override
    protected void onCleared() {
    }


    public LiveData<RecordedStatus> getRecordedStatusData() {
        return recordedStatusData;
    }


    public void updateModels() {
        if (recordedDataRepository.isRecordedDataExists()) {
            recordedStatusData.postValue(RecordedStatus.RECORDED);
        } else {
            recordedStatusData.postValue(RecordedStatus.NOT_RECORDED);
        }
    }
}