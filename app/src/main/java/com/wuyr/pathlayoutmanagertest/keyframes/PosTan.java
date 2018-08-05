package com.wuyr.pathlayoutmanagertest.keyframes;

import android.graphics.Point;
import android.graphics.PointF;

import java.util.Locale;

/**
 * Created by wuyr on 18-5-22 下午10:26.
 */
public class PosTan extends PointF {

    public float fraction;
    public float angle;
    public int index;

    public PosTan() {
    }

    public PosTan(int index) {
        this.index = index;
    }

    public PosTan(float angle) {
        this.angle = angle;
    }

    public PosTan(int index, float x, float y, float angle) {
        super(x, y);
        this.angle = angle;
        this.index = index;
    }

    public PosTan(Point p, float angle) {
        super(p);
        this.angle = angle;
    }

    public PosTan(PosTan p, int index, float fraction) {
        this(index, p.x, p.y, p.angle);
        this.fraction = fraction;
    }

    public void set(float x, float y, float angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    public float getChildAngle() {
        return angle - 90F;
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "x: %f\ty: %f\tangle: %f", x, y, angle);
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof PosTan ? (index == ((PosTan) obj).index) : this == obj;
    }
}
