package com.spr_ypt.giftsurfacetest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.spr_ypt.giftsurfacetest.layer.ILayer;
import com.spr_ypt.giftsurfacetest.layer.TestLayer;

public class LayerTestView extends View {
    public LayerTestView(Context context) {
        super(context);
    }

    public LayerTestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LayerTestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LayerTestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        ILayer layer= new TestLayer();
        layer.render(2000);
        layer.draw(canvas);
    }
}
