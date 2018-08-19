package com.wuyr.pathlayoutmanagertest.keyframes;

import android.graphics.PointF;

import java.util.Locale;

/**
 * Created by wuyr on 18-5-22 下午10:26.
 * GitHub: https://github.com/wuyr/PathLayoutManager
 */
public class PosTan extends PointF {

    /**
     * 在路径上的位置 (百分比)
     */
    public float fraction;

    /**
     * Item所对应的索引
     */
    public int index;

    /**
     * Item的旋转角度
     */
    private float angle;

    PosTan() {
    }

    private PosTan(int index, float x, float y, float angle) {
        super(x, y);
        this.angle = angle;
        this.index = index;
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
