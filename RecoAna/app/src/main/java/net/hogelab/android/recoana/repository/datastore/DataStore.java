package net.hogelab.android.recoana.repository.datastore;

public interface DataStore {
    enum Mode {
        READ,
        READ_WRITE,
    }

    int ERROR_RESULT = -1;

    Mode getMode();

    boolean open();
    void close();

    long length();
    boolean setLength(long length);

    boolean seek(long position);

    int read(byte[] buffer);
    int read(byte[] buffer, int offset, int length);

    boolean write(byte[] buffer);
    boolean write(byte[] buffer, int offset, int length);
}