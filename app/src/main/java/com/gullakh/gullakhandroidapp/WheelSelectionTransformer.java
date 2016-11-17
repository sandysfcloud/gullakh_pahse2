package com.gullakh.gullakhandroidapp;

import android.graphics.drawable.Drawable;

public interface WheelSelectionTransformer {
    void transform(Drawable drawable, WheelView.ItemState itemState);
}
