package com.spr_ypt.giftsurfacetest.screen;

import android.graphics.Canvas;

import com.spr_ypt.giftsurfacetest.layer.ILayer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TestScreen implements IScreen {

    private List<ILayer> mMultillayer;
    protected Object lockObject = new Object();


    public TestScreen() {
        initQueue();

    }

    private synchronized void initQueue() {
        mMultillayer = new CopyOnWriteArrayList<>();
    }

    public IScreen addLayer(ILayer... layers) {
        for (ILayer layer : layers) {
            if (!mMultillayer.contains(layer)) {
                mMultillayer.add(layer);
            }
        }
        return this;
    }

    @Override
    public void draw(Canvas canvas) {
        synchronized (lockObject) {
            for (ILayer layer : mMultillayer) {
                layer.draw(canvas);
            }
        }
    }


    @Override
    public void render(long currTime) {
        synchronized (lockObject) {
            for (ILayer layer : mMultillayer) {
                layer.render(currTime);
            }
        }

    }

    @Override
    public void release() {
        synchronized (lockObject) {
            for (ILayer layer : mMultillayer) {
                layer.release();
            }
        }
    }

    @Override
    public void resetLayerStartTime(long currTime) {
        for (ILayer layer : mMultillayer) {
            layer.resetStartTime(currTime);
        }
    }
}
