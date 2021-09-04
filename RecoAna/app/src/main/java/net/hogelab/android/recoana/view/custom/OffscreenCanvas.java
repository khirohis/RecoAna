package net.hogelab.android.recoana.view.custom;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;

import androidx.annotation.NonNull;

public class OffscreenCanvas {
    private static final String TAG = PcmGraphView.class.getSimpleName();

    private final int width;
    private final int height;
    private final Bitmap bitmap;

    private final Paint paint;
    private final Canvas canvas;

    public OffscreenCanvas(int width, int height, Bitmap.Config config) {
        this.width = width;
        this.height = height;
        bitmap = Bitmap.createBitmap(width, height, config);

        paint = new Paint();
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