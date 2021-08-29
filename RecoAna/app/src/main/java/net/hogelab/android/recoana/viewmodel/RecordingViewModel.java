package net.hogelab.android.recoana.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

public class RecordingViewModel extends AndroidViewModel {
    private static final String TAG = RecordingViewModel.class.getSimpleName();

    public RecordingViewModel(Application application) {
        super(application);
    }

    @Override
    protected void onCleared() {
    }
}