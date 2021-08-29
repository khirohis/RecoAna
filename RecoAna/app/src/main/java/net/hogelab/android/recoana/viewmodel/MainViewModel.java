package net.hogelab.android.recoana.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

public class MainViewModel extends AndroidViewModel {
    private static final String TAG = MainViewModel.class.getSimpleName();

    public MainViewModel(Application application) {
        super(application);
    }

    @Override
    protected void onCleared() {
    }
}
