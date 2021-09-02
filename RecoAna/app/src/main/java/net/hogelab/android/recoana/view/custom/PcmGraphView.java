package net.hogelab.android.recoana.view.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import net.hogelab.android.recoana.AppExecutor;

import java.nio.ByteBuffer;

public class PcmGraphView extends View {
    private static final String TAG = PcmGraphView.class.getSimpleName();

    private Canvas offscreenCanvas;
    private Bitmap offscreenBitmap;
    private Paint offscreenPaint;

    private int position;
    private byte[] pcmData;

    public PcmGraphView(Context context) {
        super(context);
    }

    public PcmGraphView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PcmGraphView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PcmGraphView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        offscreenBitmap = Bitmap.createBitmap(480, 512, Bitmap.Config.ARGB_8888);
        offscreenCanvas = new Canvas(offscreenBitmap);
        offscreenPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setFilterBitmap(true);

        Rect destRect = new Rect(0, 0, 120, 512);
        canvas.drawBitmap(offscreenBitmap, null, destRect, paint);

        paint.setColor(Color.RED);
        canvas.drawLine(0, 0, 0, 512, paint);

        String time;
        int dec = position % 10;
        if (dec == 0) {
            time = position + "0ms";
        } else {
            time = "+" + dec + "0";
        }

        paint.setColor(Color.BLUE);
        paint.setTextSize(20);
        canvas.drawText(time, 10, 20, paint);
    }


    public void setPcmData(int position, byte[] pcmData) {
        this.position = position;
        this.pcmData = pcmData;

        AppExecutor.getMainExecutor().execute(() -> drawPcmData(pcmData));
    }


    private void drawPcmData(byte[] pcmData) {
        offscreenCanvas.drawColor(0, PorterDuff.Mode.CLEAR);

        int frames = pcmData.length / 2;
        ByteBuffer buffer = ByteBuffer.wrap(pcmData);
        for (int i = 0; i < frames; i++) {
            int value = buffer.getShort() / 128;
            offscreenCanvas.drawLine(i, 256, i, 256 - value, offscreenPaint);
        }

        postInvalidate();
    }
}