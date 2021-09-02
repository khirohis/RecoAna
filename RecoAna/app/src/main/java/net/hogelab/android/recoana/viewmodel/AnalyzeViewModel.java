package net.hogelab.android.recoana.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import net.hogelab.android.recoana.model.repository.RecordedDataRepository;
import net.hogelab.android.recoana.model.datastore.DataStore;

public class AnalyzeViewModel extends AndroidViewModel {
    private static final String TAG = AnalyzeViewModel.class.getSimpleName();

    private final RecordedDataRepository recordedDataRepository;

    private final DataStore recordedDataStore;


    public AnalyzeViewModel(Application application) {
        super(application);

        recordedDataRepository = new RecordedDataRepository(application);

        recordedDataStore = recordedDataRepository.getRecordedDataStore();
    }

    @Override
    protected void onCleared() {
    }


    public DataStore getRecordedDataStore() {
        return recordedDataStore;
    }
}