package com.wuyr.pathlayoutmanagertest;

import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by wuyr on 18-6-27 下午12:03.
 */
public class FigureOutView extends AppCompatActivity {

    private View mView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.setDebugLevel(LogUtil.ERROR);
        LogUtil.setIsShowClassName(false);
        setContentView(R.layout.act_figure_out);
        mView = findViewById(R.id.view);
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.print("===");
            }
        });
        SeekBar seekBar = findViewById(R.id.seek_bar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            Toast toast = Toast.makeText(FigureOutView.this, "", Toast.LENGTH_SHORT);

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float[] points = new float[2];
//                points[0] = -mView.getLeft();
//                points[1] = -mView.getTop();
//                mView.setRotation(progress);
                mView.setTranslationX(-progress);
//                mView.setTranslationY(-progress);
                Matrix matrix = getViewMatrix(mView);
                if (matrix != null) {
                    LogUtil.print("开始转换。。。" );
                    matrix.mapPoints(points);

                    Matrix m = new Matrix();
                    float[] p = new float[2];
                    LogUtil.print(Arrays.toString(p));
//                    m.postTranslate(-progress,0);
                    m.postRotate(90,100,100);
                    m.mapPoints(p);
                    LogUtil.print(Arrays.toString(p));
                }
                LogUtil.printf("转换后：%s\t在View范围内？ %s\n========\n========", Arrays.toString(points), pointInView(mView, points));
                toast.setText("白点在View中吗？  " + (pointInView(mView, points) ? "是的" : "不不不不"));
                toast.show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private Matrix getViewMatrix(View view) {
        try {
            Method getInverseMatrix = View.class.getDeclaredMethod("getInverseMatrix");
            getInverseMatrix.setAccessible(true);
            return (Matrix) getInverseMatrix.invoke(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean pointInView(View view, float[] points) {
        try {
            Method pointInView = View.class.getDeclaredMethod("pointInView", float.class, float.class);
            pointInView.setAccessible(true);
            return (boolean) pointInView.invoke(view, points[0], points[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
