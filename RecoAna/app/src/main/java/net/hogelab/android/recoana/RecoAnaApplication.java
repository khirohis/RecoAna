package net.hogelab.android.recoana;

import android.app.Application;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

public class RecoAnaApplication extends Application {
    private static final String TAG = RecoAnaApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        Log.v(TAG, "onCreate");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        Log.v(TAG, "onTerminate");
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        Log.v(TAG, "onConfigurationChanged: newConfig=" + newConfig.toString());
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

        Log.v(TAG, "onLowMemory");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);

        Log.v(TAG, "onTrimMemory: level=" + level);
    }
}