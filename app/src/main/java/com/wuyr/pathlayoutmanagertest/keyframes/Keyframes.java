package com.wuyr.pathlayoutmanagertest.keyframes;

import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.FloatRange;

/**
 * Created by wuyr on 17-11-22 上午12:45.
 */

/**
 * 关键帧,参考自SDK
 */
public class Keyframes {

    private static final float PRECISION = .5F;
    private int mNumPoints;
    private float[] mX;
    private float[] mY;
    private float[] mAngle;
    private PosTan mTemp;

    public Keyframes(Path path) {
        initPath(path);
        mTemp = new PosTan();
    }

    private void initPath(Path path) {
        if (path == null || path.isEmpty()) {
            throw new NullPointerException("path is empty!");
        }
        final PathMeasure pathMeasure = new PathMeasure(path, false);
        mX = new float[0];
        mY = new float[0];
        mAngle = new float[0];
        do {
            final float pathLength = pathMeasure.getLength();
            final int numPoints = (int) (pathLength / PRECISION) + 1;
            final float[] x = new float[numPoints];
            final float[] y = new float[numPoints];
            final float[] angle = new float[numPoints];
            final float[] position = new float[2];
            final float[] tangent = new float[2];
            for (int i = 0; i < numPoints; ++i) {
                final float distance = (i * pathLength) / (numPoints - 1);
                pathMeasure.getPosTan(distance, position, tangent);
                x[i] = position[0];
                y[i] = position[1];
                angle[i] = fixAngle((float) (Math.atan2(tangent[1], tangent[0]) * 180F / Math.PI));
            }
            mNumPoints += numPoints;

            float[] tmpX = new float[mX.length + x.length];
            System.arraycopy(mX, 0, tmpX, 0, mX.length);
            System.arraycopy(x, 0, tmpX, mX.length, x.length);
            mX = tmpX;

            float[] tmpY = new float[mY.length + y.length];
            System.arraycopy(mY, 0, tmpY, 0, mY.length);
            System.arraycopy(y, 0, tmpY, mY.length, y.length);
            mY = tmpY;

            float[] tmpAngle = new float[mAngle.length + angle.length];
            System.arraycopy(mAngle, 0, tmpAngle, 0, mAngle.length);
            System.arraycopy(angle, 0, tmpAngle, mAngle.length, angle.length);
            mAngle = tmpAngle;
        } while (pathMeasure.nextContour());
    }

    /**
     * 调整角度，使其在0 ~ 360之间
     *
     * @param rotation 当前角度
     * @return 调整后的角度
     */
    private float fixAngle(float rotation) {
        float angle = 360F;
        if (rotation < 0) {
            rotation += angle;
        }
        if (rotation > angle) {
            rotation %= angle;
        }
        return rotation;
    }

    public PosTan getValue(@FloatRange(from = 0F, to = 1F) float fraction) {
        if (fraction >= 1F || fraction < 0) {
            return null;
        } else {
            int index = (int) (mNumPoints * fraction);
            mTemp.set(mX[index], mY[index], mAngle[index]);
            return mTemp;
        }
    }

    public PosTan getValue(int index) {
        mTemp.set(mX[index], mY[index], mAngle[index]);
        return mTemp;
    }

    public int getDataSize() {
        return mX.length;
    }

    public int getPathLength() {
//        因为PRECISION = 0.5
//        return (int) (mNumPoints * PRECISION);
        return mNumPoints / 2;
    }

    public void release() {
        mX = null;
        mY = null;
        mAngle = null;
        mTemp = null;
    }
}
