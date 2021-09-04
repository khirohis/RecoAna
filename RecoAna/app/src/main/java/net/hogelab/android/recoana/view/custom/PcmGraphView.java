package net.hogelab.android.recoana.view.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import net.hogelab.android.recoana.AppExecutor;

import java.nio.ByteBuffer;

public class PcmGraphView extends View {
    private static final String TAG = PcmGraphView.class.getSimpleName();

    private int viewWidth;
    private int viewHeight;
    private OffscreenCanvas offscreenCanvas;

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

        Log.v(TAG, "onFinishInflate");

        // initialize
        offscreenCanvas = new OffscreenCanvas(480, 1024);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        Log.v(TAG, "onAttachedToWindow");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        Log.v(TAG, "onMeasure(" + widthMeasureSpec + ", " + heightMeasureSpec + ")");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        Log.v(TAG, "onLayout(" + changed + ", " + left + ", " + top + ", " + right + ", " + bottom + ")");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        Log.v(TAG, "onDetachedFromWindow");
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.v(TAG, "onDraw");

        Paint paint = new Paint();
        paint.setFilterBitmap(true);

        int width = getWidth();
        int height = getHeight();
        Rect destRect = new Rect(0, 0, width, height);
        Log.v(TAG, "bitmap draw Rect: " + destRect.toShortString());

        canvas.drawBitmap(offscreenCanvas.getBitmap(), null, destRect, paint);

        float strokeWidth;
        String timeText = null;

        int dec = position % 10;
        if (dec == 0) {
            if (dec % 10 == 0) {
                strokeWidth = 4.0f;
            } else {
                strokeWidth = 2.0f;
            }
            timeText = position + "0ms";
        } else {
            strokeWidth = 1.0f;
//            timeText = dec + "0";
        }

        paint.setColor(Color.RED);
        paint.setStrokeWidth(strokeWidth);
        canvas.drawLine(0, 0, 0, height, paint);

        if (timeText != null) {
            paint.setColor(Color.BLUE);
            paint.setTextSize(20);
            canvas.drawText(timeText, 10, 20, paint);
        }
    }


    public void setPcmData(int position, byte[] pcmData) {
        this.position = position;
        this.pcmData = pcmData;

        AppExecutor.getMainExecutor().execute(() -> drawPcmData(pcmData));
    }


    private void drawPcmData(byte[] pcmData) {
        offscreenCanvas.clear();

        int frames = pcmData.length / 2;
        ByteBuffer buffer = ByteBuffer.wrap(pcmData);
        for (int i = 0; i < frames; i++) {
            int value = buffer.getShort() / 64;
            offscreenCanvas.drawLine(i, 512, i, 512 - value);
        }

        postInvalidateOnAnimation();
    }
}