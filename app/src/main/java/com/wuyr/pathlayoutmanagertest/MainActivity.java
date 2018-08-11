package com.wuyr.pathlayoutmanagertest;

import android.graphics.Path;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuyr on 18-5-21 下午11:25.
 */
public class MainActivity extends AppCompatActivity {

    private PathLayoutManager mPathLayoutManager;
    private CanvasView mView, mCanvasView;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LogUtil.setDebugLevel(LogUtil.ERROR);
        LogUtil.setIsShowClassName(false);

        setContentView(R.layout.act_main_view);

        initView();
    }

    private void initView() {
        mView = findViewById(R.id.view);
        mCanvasView = findViewById(R.id.canvas_view);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(mPathLayoutManager = new PathLayoutManager(null, 50));
        recyclerView.setAdapter(mAdapter = new MyAdapter(this, null));

        SeekBar seekBar = findViewById(R.id.seek_bar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mPathLayoutManager.setItemOffset(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Switch itemDirectionFixed = findViewById(R.id.direction_fixed);
        Switch allowOverflow = findViewById(R.id.allow_overflow);
        Switch unlimitedScroll = findViewById(R.id.unlimited_scroll);

        CompoundButton.OnCheckedChangeListener listener = (buttonView, isChecked) -> {
            switch (buttonView.getId()) {
                case R.id.direction_fixed:
                    mPathLayoutManager.setItemDirectionFixed(isChecked);
                    break;
                case R.id.unlimited_scroll:
                    mPathLayoutManager.setUnlimitedScroll(isChecked);
                    if (isChecked) {
                        allowOverflow.setChecked(false);
                    }
                    break;
                case R.id.allow_overflow:
                    mPathLayoutManager.setAllowOverflow(isChecked);
                    if (isChecked) {
                        unlimitedScroll.setChecked(false);
                    }
                    break;
                default:
                    break;
            }
        };

        itemDirectionFixed.setOnCheckedChangeListener(listener);
        unlimitedScroll.setOnCheckedChangeListener(listener);
        allowOverflow.setOnCheckedChangeListener(listener);

    }

    public void horizontalScroll(View view) {
        mPathLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mPathLayoutManager.smoothScrollToPosition(2);
    }

    public void verticalScroll(View view) {
        mPathLayoutManager.setOrientation(RecyclerView.VERTICAL);
    }

    public void startDraw(View view) {
        mCanvasView.setVisibility(View.VISIBLE);
        mCanvasView.clear();
        mView.setVisibility(View.INVISIBLE);
    }

    public void stopDraw(View view) {
        Path path = mCanvasView.getPath();
        if (path != null && !path.isEmpty()) {
            mCanvasView.setVisibility(View.INVISIBLE);
            mView.setPath(mCanvasView.getPath());
            mView.setVisibility(View.VISIBLE);
            mPathLayoutManager.updatePath(mCanvasView.getPath());
        }
    }

    public void addItem(View view) {
        List<String> data = new ArrayList<>();
        for (int i = mAdapter.getItemCount(); i < mAdapter.getItemCount() + 10; i++) {
            data.add("" + i);
        }
        mAdapter.addData(data);
//        mAdapter.notifyDataSetChanged();
    }

    public void removeItem(View view) {
        for (int i = 0; i < 10; i++) {
            mAdapter.removeData(mAdapter.getItemCount() - 1);
        }
    }
}
