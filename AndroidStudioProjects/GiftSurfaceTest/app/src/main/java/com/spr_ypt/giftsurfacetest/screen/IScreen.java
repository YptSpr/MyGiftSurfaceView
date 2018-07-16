package com.spr_ypt.giftsurfacetest.screen;

import android.graphics.Canvas;

public interface IScreen {
    void draw(Canvas canvas);

    void render(long currTime);

    void release();

    void resetLayerStartTime(long currTime);
}
