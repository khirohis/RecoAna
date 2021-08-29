package net.hogelab.android.recoana.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

public class PreferencesViewModel extends AndroidViewModel {
    private static final String TAG = PreferencesViewModel.class.getSimpleName();

    public PreferencesViewModel(Application application) {
        super(application);
    }

    @Override
    protected void onCleared() {
    }
}