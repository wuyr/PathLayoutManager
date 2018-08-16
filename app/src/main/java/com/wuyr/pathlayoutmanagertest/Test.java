package com.wuyr.pathlayoutmanagertest;

/**
 * @author wuyr
 * @since 2018-07-27 下午4:25
 */
public class Test {
    /*

    y-y1    x-x1
    ---- = -----
    y2-y1  x2-x1

     */

    public static void main(String[] args) throws Exception {
        //(y-y2)/(y1-y2) = (x-x2)/(x1-x2)
//        float[] scales = new float[]{1F, 0F, .5F, .5F, 1F, 1F};
//        for (float i = 0F; i < 1F; i += .01F) {
////            getScale(i, scales);
//            System.out.print(i + "---->" + getScale(i, scales) + ",\n\n");
////            getScale2(i, scales);
////            System.out.print(new DecimalFormat("0.00").format(i));
//        }
    }


    private static float getScale(float fraction, float[] scaleRatio) {
        boolean isHasMin = false;
        boolean isHasMax = false;
        float minScale = 0;
        float maxScale = 0;
        float scalePosition;
        float minFraction = 1, maxFraction = 1;
        //必须从小到大遍历，才能找到最贴近fraction的scale
        for (int i = 1; i < scaleRatio.length; i += 2) {
            scalePosition = scaleRatio[i];
            if (scalePosition <= fraction) {
                minScale = scaleRatio[i - 1];
                minFraction = scaleRatio[i];
                isHasMin = true;
            } else {
                break;
            }
        }
        //必须从大到小遍历，才能找到最贴近fraction的scale
        for (int i = scaleRatio.length - 1; i >= 1; i -= 2) {
            scalePosition = scaleRatio[i];
            if (scalePosition >= fraction) {
                maxScale = scaleRatio[i - 1];
                maxFraction = scaleRatio[i];
                isHasMax = true;
            } else {
                break;
            }
        }
        if (!isHasMin) {
            minScale = 1;
        }
        if (!isHasMax) {
            maxScale = 1;
        }
        System.out.printf("\n\n%f, %f, %f, %f\n", minScale, minFraction, maxScale, maxFraction);
        fraction = solveTwoPointForm(minFraction, maxFraction, fraction);
        System.out.println(fraction);
        float distance = maxScale - minScale;
        float scale = distance * fraction;
        float result = minScale + scale;
        return isFinite(result) ? result : minScale;
    }

    private static float solveTwoPointForm(float startX, float endX, float currentX) {
        return (currentX - startX) / (endX - startX);
    }

    private static boolean isFinite(float f) {
        return !Float.isNaN(f) && !Float.isInfinite(f);
    }
}
