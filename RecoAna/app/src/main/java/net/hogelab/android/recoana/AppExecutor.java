package net.hogelab.android.recoana;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppExecutor {

    private static final Looper mainLooper = Looper.getMainLooper();
    private static final Handler mainHandler = new Handler(mainLooper);
    private static final Executor mainExecutor = mainHandler::post;
    private static final ExecutorService workerExecutor = Executors.newCachedThreadPool();

    @NonNull
    public static Handler getMainHandler() {
        return mainHandler;
    }

    @NonNull
    public static Executor getMainExecutor() {
        return mainExecutor;
    }

    @NonNull
    public static ExecutorService getWorkerExecutor() {
        return workerExecutor;
    }
}