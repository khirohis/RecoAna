package net.hogelab.android.recoana.view.custom;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;

import androidx.annotation.NonNull;

public class OffscreenCanvas {
    private static final String TAG = PcmGraphView.class.getSimpleName();

    private final Paint paint;

    private int width;
    private int height;
    private Bitmap bitmap;
    private Canvas canvas;

    public OffscreenCanvas(int width, int height) {
        this(width, height, Bitmap.Config.ARGB_8888);
    }

    public OffscreenCanvas(int width, int height, Bitmap.Config config) {
        paint = new Paint();

        setup(width, height, config);
    }


    public void setup(int width, int height) {
        setup(width, height, Bitmap.Config.ARGB_8888);
    }

    public void setup(int width, int height, Bitmap.Config config) {
        this.width = width;
        this.height = height;
        bitmap = Bitmap.createBitmap(width, height, config);
        canvas = new Canvas(bitmap);
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @NonNull
    public Bitmap getBitmap() {
        return bitmap;
    }

    @NonNull
    public Canvas getCanvas() {
        return canvas;
    }

    @NonNull
    public Paint getPaint() {
        return paint;
    }


    public void clear() {
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
    }

    public void drawLine(float startX, float startY, float endX, float endY) {
        canvas.drawLine(startX, startY, endX, endY, paint);
    }
}