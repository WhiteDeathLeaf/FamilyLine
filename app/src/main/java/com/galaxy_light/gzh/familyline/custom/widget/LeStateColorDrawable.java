package com.galaxy_light.gzh.familyline.custom.widget;

import android.graphics.drawable.ColorDrawable;

/**
 *Created by gzh on 2017-9-22
 */
public abstract class LeStateColorDrawable extends ColorDrawable {

    private boolean mPressed = false;

    public LeStateColorDrawable(int color) {
        super(color);
    }

    @Override
    protected boolean onStateChange(int[] state) {

        boolean pressed = isPressed(state);
        if (mPressed != pressed) {
            mPressed = pressed;
            onIsPressed(mPressed);
        }
        return true;
    }

    protected abstract void onIsPressed(boolean isPressed);

    @Override
    public boolean setState(int[] stateSet) {
        return super.setState(stateSet);
    }

    @Override
    public boolean isStateful() {
        return true;
    }

    private boolean isPressed(int[] state) {
        boolean pressed = false;
        for (int i = 0, j = state != null ? state.length : 0; i < j; i++) {
            if (state[i] == android.R.attr.state_pressed) {
                pressed = true;
                break;
            }
        }
        return pressed;
    }
}
