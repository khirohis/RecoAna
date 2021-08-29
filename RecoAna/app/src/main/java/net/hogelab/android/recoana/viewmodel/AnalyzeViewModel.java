package net.hogelab.android.recoana.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

public class AnalyzeViewModel extends AndroidViewModel {
    private static final String TAG = AnalyzeViewModel.class.getSimpleName();

    public AnalyzeViewModel(Application application) {
        super(application);
    }

    @Override
    protected void onCleared() {
    }
}