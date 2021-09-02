package net.hogelab.android.recoana.model.datastore;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileDataStore implements DataStore {
    private static final String TAG = FileDataStore.class.getSimpleName();


    private File file;
    private Mode mode;
    private RandomAccessFile randomAccessFile;


    public FileDataStore(String filePath) {
        this(filePath, Mode.READ_WRITE);
    }

    public FileDataStore(File file) {
        this(file, Mode.READ_WRITE);
    }

    public FileDataStore(String filePath, Mode mode) {
        this(new File(filePath), mode);
    }

    public FileDataStore(File file, Mode mode) {
        this.file = file;
        this.mode = mode;
    }


    @Override
    public Mode getMode() {
        return mode;
    }

    @Override
    public boolean open() {
        close();

        String fileOpenMode = getFileOpenMode(mode);
        try {
            randomAccessFile = new RandomAccessFile(file, fileOpenMode);

            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public void close() {
        if (randomAccessFile != null) {
            try {
                randomAccessFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            randomAccessFile = null;
        }
    }


    @Override
    public long length() {
        try {
            return randomAccessFile.length();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ERROR_RESULT;
    }

    @Override
    public boolean setLength(long length) {
        try {
            randomAccessFile.setLength(length);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }


    @Override
    public boolean seek(long position) {
        try {
            randomAccessFile.seek(position);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }


    @Override
    public int read(byte[] buffer) {
        return read(buffer, 0, buffer.length);
    }

    @Override
    public int read(byte[] buffer, int offset, int length) {
        try {
            return randomAccessFile.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ERROR_RESULT;
    }


    @Override
    public boolean write(byte[] buffer) {
        try {
            randomAccessFile.write(buffer);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean write(byte[] buffer, int offset, int length) {
        try {
            randomAccessFile.write(buffer, offset, length);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }


    @NonNull
    private String getFileOpenMode(Mode mode) {
        String fileOpenMode;

        switch (mode) {

            case READ:
                fileOpenMode = "r";
                break;

            case READ_WRITE:
            default:
                fileOpenMode = "rw";
                break;
        }

        return fileOpenMode;
    }
}
