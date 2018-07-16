package com.spr_ypt.giftsurfacetest.layer;

import android.graphics.Canvas;

public interface ILayer {

    void render(long current);

    void draw(Canvas canvas);

    void release();

    void resetStartTime(long currTime);
}
