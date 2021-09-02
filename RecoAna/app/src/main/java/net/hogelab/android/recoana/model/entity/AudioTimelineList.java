package net.hogelab.android.recoana.model.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import net.hogelab.android.recoana.audio.RecordSetting;
import net.hogelab.android.recoana.model.datastore.DataStore;

public class AudioTimelineList {
    private static final String TAG = AudioTimelineList.class.getSimpleName();

    private final DataStore dataStore;
    private int blockSize;

    private long length;
    private int blockCount;

    public AudioTimelineList(@NonNull DataStore dataStore) {
        this.dataStore = dataStore;
        blockSize = RecordSetting.BYTES_PER_BUFFER;
    }


    public void initialize() {
        if (!dataStore.open()) {
            // TODO: error handling
        }

        length = dataStore.length();
        blockCount = (int) ((length - 1) / blockSize) + 1;
    }

    public void terminate() {
        dataStore.close();
    }


    public int getBlockSize() {
        return blockSize;
    }

    public int getBlockCount() {
        return blockCount;
    }


    @Nullable
    public byte[] get(int index) {
        // TODO: check out of index

        byte[] buffer = new byte[blockSize];
        int offset = blockSize * index;
        dataStore.seek(offset);
        dataStore.read(buffer);

        return buffer;
    }
}