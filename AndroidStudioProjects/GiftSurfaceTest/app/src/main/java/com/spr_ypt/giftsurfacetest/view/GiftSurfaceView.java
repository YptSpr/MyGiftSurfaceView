package com.spr_ypt.giftsurfacetest.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.spr_ypt.giftsurfacetest.screen.IScreen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GiftSurfaceView extends SurfaceView implements IGiftView, SurfaceHolder.Callback {

    private SurfaceHolder mSurfaceHolder;

    public static final int FPS = 50;

    private volatile boolean isOnPlaying;

    private List<IScreen> screens;

    public GiftSurfaceView(Context context) {
        super(context);
        init();
    }

    public GiftSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GiftSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public GiftSurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setZOrderMediaOverlay(true);
        setWillNotCacheDrawing(true);
        setDrawingCacheEnabled(false);
        setWillNotDraw(true);
        setZOrderOnTop(true);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setFormat(PixelFormat.TRANSPARENT);
        screens = new ArrayList<>();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
//        setZOrderOnTop(true);
        Canvas canvas = surfaceHolder.lockCanvas();
        if (canvas != null) {
            try {
                surfaceHolder.unlockCanvasAndPost(canvas);
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    public void addScreens(IScreen... screens) {
        this.screens = Arrays.asList(screens);
    }

    public void start() {
        if (null == screens || screens.size() == 0) return;
        for (IScreen screen : screens) {
            screen.resetLayerStartTime(System.currentTimeMillis());
        }
        isOnPlaying = true;
        getDrawThread().start();
        getRenderThread().start();

    }

    public void stop() {
        isOnPlaying = false;
    }

    public boolean isOnPlaying() {
        return isOnPlaying;
    }

    @SuppressLint("NewThread")
    private Thread getDrawThread() {
        Thread drawThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isOnPlaying) {
                    try {
                        long startTime = System.currentTimeMillis();
                        synchronized (getHolder()) {
                            Canvas canvas = getHolder().lockCanvas();
                            if (canvas != null) {
                                canvas.drawColor(0, PorterDuff.Mode.CLEAR);
                                drawCanvas(canvas);
                            }
                            getHolder().unlockCanvasAndPost(canvas);
                        }
                        long endTime = System.currentTimeMillis();
                        long time = (long) (1000f / FPS - (endTime - startTime));

                        if (time > 0) {
                            Thread.sleep(time);
                        }
                    } catch (Exception e) {
                        Log.e("spr_ypt", "getDrawThread run: error=" + e.toString());
                        e.printStackTrace();
                    }
                }

                try {
                    synchronized (getHolder()) {
                        Canvas canvas = getHolder().lockCanvas();
                        if (canvas != null) {
                            canvas.drawColor(0, PorterDuff.Mode.CLEAR);
                        }
                        getHolder().unlockCanvasAndPost(canvas);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        drawThread.setName("Thread--Draw--Spr_ypt");
        return drawThread;
    }

    private void drawCanvas(Canvas canvas) {
        if (null == screens || screens.size() == 0) return;
        for (IScreen screen : screens) {
            screen.draw(canvas);
        }
    }

    @SuppressLint("NewThread")
    private Thread getRenderThread() {
        Thread renderThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isOnPlaying) {
                    try {
                        long startTime = System.currentTimeMillis();
                        render(System.currentTimeMillis());
                        long endTime = System.currentTimeMillis();
                        long time = (long) (1000f / FPS * 1.5f - (endTime - startTime));
                        if (time > 0) {
                            Thread.sleep(time);
                        }
                    } catch (Exception e) {
                        Log.e("spr_ypt", "getRenderThread run: error=" + e.toString());
                        e.printStackTrace();
                    }
                }
            }
        });
        renderThread.setName("Thread--Render--Spr_ypt");
        return renderThread;
    }

    private void render(long currTime) {
        if (null == screens || screens.size() == 0) return;
        for (IScreen screen : screens) {
            screen.render(currTime);
        }
    }
}
