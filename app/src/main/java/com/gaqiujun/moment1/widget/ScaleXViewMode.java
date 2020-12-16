package com.gaqiujun.moment1.widget;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class ScaleXViewMode {
    private float mScaleRatio = 0.001f;

    public ScaleXViewMode() {
    }

    public ScaleXViewMode(float scaleRatio) {
        mScaleRatio = scaleRatio;
    }

    public void applyToView(View v, RecyclerView parent) {
        float halfWidth = v.getWidth() * 0.5f;
        float parentHalfWidth = parent.getWidth() * 0.5f;
        float x = v.getX();
        float rot = parentHalfWidth - halfWidth - x;
        float scale = 1.0f - Math.abs(rot) * mScaleRatio;
//        boolean isCenter = (boolean) v.getTag("tagIsCenter");
        v.setScaleX(scale);
        v.setScaleY(scale);
//        ViewCompat.setScaleX(v, scale);
//        ViewCompat.setScaleY(v, scale);
    }
}
