package com.spr_ypt.giftsurfacetest.layer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TestLayer implements ILayer {

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    Paint mPaint;

    private int duration = 5000;

    private long startTime = 0;

    private int round = 0;

    public TestLayer() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void render(long current) {
        round = (int) (current - startTime) % duration / 20;
    }

    @Override
    public void draw(Canvas canvas) {
        lock.readLock().lock();
        RectF rectF = new RectF(canvas.getWidth() / 4, canvas.getHeight() / 4, canvas.getWidth() * 3 / 4, canvas.getHeight() * 3 / 4);
        Log.e("spr_ypt", "draw: rectF=" + rectF.toString() + "||round=" + round);
        canvas.drawRoundRect(rectF, round, round, mPaint);
        lock.readLock().unlock();
    }

    @Override
    public void release() {
        lock.readLock().unlock();
    }

    @Override
    public void resetStartTime(long currTime) {
        startTime = currTime;
    }
}
