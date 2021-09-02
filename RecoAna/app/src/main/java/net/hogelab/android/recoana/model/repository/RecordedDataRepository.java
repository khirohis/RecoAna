package net.hogelab.android.recoana.model.repository;

import android.content.Context;

import androidx.annotation.Nullable;

import net.hogelab.android.recoana.model.datastore.DataStore;
import net.hogelab.android.recoana.model.datastore.FileDataStore;

import java.io.File;

public class RecordedDataRepository {
    private static final String TAG = RecordedDataRepository.class.getSimpleName();

    private static final String RECORDED_FILE_NAME = "recorded_audio000.pcm";
    private static final String TEMPORARY_FILE_NAME = "temporary_audio.pcm";

    private final Context context;

    public RecordedDataRepository(Context context) {
        this.context = context;
    }

    public boolean isRecordedDataExists() {
        File file = new File(getRecordedFilePath());

        return file.exists();
    }

    public boolean isTemporaryDataExists() {
        File file = new File(getTemporaryFilePath());

        return file.exists();
    }

    @Nullable
    public DataStore getRecordedDataStore() {
        if (isRecordedDataExists()) {
            return new FileDataStore(getRecordedFilePath(), DataStore.Mode.READ);
        }

        return null;
    }

    @Nullable
    public DataStore getTemporaryDataStore() {
        return new FileDataStore(getTemporaryFilePath(), DataStore.Mode.READ_WRITE);
    }


    public boolean commitTemporaryDataStore() {
        File recordedFile = new File(getRecordedFilePath());
        if (recordedFile.exists()) {
            if (!recordedFile.delete()) {
                return false;
            }
        }

        File temporaryFile = new File(getTemporaryFilePath());
        if (temporaryFile.exists()) {
            return temporaryFile.renameTo(recordedFile);
        }

        return false;
    }


    private String getRecordedFilePath() {
        File dataDir = context.getFilesDir();
        File file = new File(dataDir, RECORDED_FILE_NAME);

        return file.getAbsolutePath();
    }

    private String getTemporaryFilePath() {
        File dataDir = context.getFilesDir();
        File file = new File(dataDir, TEMPORARY_FILE_NAME);

        return file.getAbsolutePath();
    }
}