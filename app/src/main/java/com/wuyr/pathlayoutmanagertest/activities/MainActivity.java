package com.wuyr.pathlayoutmanagertest.activities;

import android.annotation.SuppressLint;
import android.graphics.Path;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.wuyr.pathlayoutmanager.PathLayoutManager;
import com.wuyr.pathlayoutmanagertest.R;
import com.wuyr.pathlayoutmanagertest.adapters.PathAdapter;
import com.wuyr.pathlayoutmanagertest.views.CanvasView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by wuyr on 18-5-21 下午11:25.
 */
public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener {

    private PathLayoutManager mPathLayoutManager;
    private CanvasView mTrackView, mCanvasView;
    private PathAdapter mAdapter;
    private Toast mToast;
    private boolean isPathInitialized;
    private boolean isShowPath;

    @SuppressLint("ShowToast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_main_view);
        initView();
    }

    private void initView() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, findViewById(R.id.drawer), findViewById(R.id.toolbar),
                R.string.app_name, R.string.app_name);
        ((DrawerLayout) findViewById(R.id.drawer)).addDrawerListener(toggle);
        toggle.syncState();

        mTrackView = findViewById(R.id.track_panel);
        mCanvasView = findViewById(R.id.canvas_view);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(mPathLayoutManager = new PathLayoutManager(null, 150));
        recyclerView.setAdapter(mAdapter = new PathAdapter(this, null));

        mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);

        findViewById(R.id.card).setEnabled(false);
        findViewById(R.id.normal).setEnabled(false);

        ((Switch) findViewById(R.id.orientation)).setOnCheckedChangeListener(this);
        ((Switch) findViewById(R.id.direction_fixed)).setOnCheckedChangeListener(this);
        ((Switch) findViewById(R.id.auto_select)).setOnCheckedChangeListener(this);
        ((Switch) findViewById(R.id.disable_fling)).setOnCheckedChangeListener(this);
        ((Switch) findViewById(R.id.show_path)).setOnCheckedChangeListener(this);

        ((SeekBar) findViewById(R.id.item_offset)).setOnSeekBarChangeListener(this);
        ((SeekBar) findViewById(R.id.auto_select_fraction)).setOnSeekBarChangeListener(this);
        ((SeekBar) findViewById(R.id.fixing_animation_duration)).setOnSeekBarChangeListener(this);

        mPathLayoutManager.setOnItemSelectedListener(position -> {
            mToast.setText(String.format(Locale.getDefault(), "Item %d selected", position));
            mToast.show();
        });

    }

    public void handleOnClick(View view) {
        switch (view.getId()) {
            case R.id.start_draw:
                mAdapter.clearData();
                mCanvasView.setVisibility(View.VISIBLE);
                mCanvasView.clear();
                mTrackView.setVisibility(View.INVISIBLE);
                break;
            case R.id.end_draw:
                Path path = mCanvasView.getPath();
                if (path != null && !path.isEmpty()) {
                    mCanvasView.setVisibility(View.INVISIBLE);
                    mTrackView.setPath(mCanvasView.getPath());
                    mTrackView.setVisibility(isShowPath ? View.VISIBLE : View.INVISIBLE);
                    mPathLayoutManager.updatePath(mCanvasView.getPath());
                    isPathInitialized = true;
                }
                break;
            case R.id.add:
                if (checkIsPathInitialized()) {
                    List<Object> data = new ArrayList<>();
                    for (int i = 0; i < 10; i++) {
                        data.add(null);
                    }
                    mAdapter.addData(data);
                }
                break;
            case R.id.remove:
                if (checkIsPathInitialized()) {
                    for (int i = 0; i < 10; i++) {
                        mAdapter.removeData(mAdapter.getItemCount() - 1);
                    }
                }
                break;
            case R.id.card:
                if (checkIsPathInitialized()) {
                    view.setEnabled(false);
                    findViewById(R.id.j20).setEnabled(true);
                    findViewById(R.id.dragon).setEnabled(true);
                    mAdapter.setType(PathAdapter.TYPE_CARD);
                }
                break;
            case R.id.j20:
                if (checkIsPathInitialized()) {
                    view.setEnabled(false);
                    findViewById(R.id.card).setEnabled(true);
                    findViewById(R.id.dragon).setEnabled(true);
                    mAdapter.setType(PathAdapter.TYPE_J20);
                }
                break;
            case R.id.dragon:
                if (checkIsPathInitialized()) {
                    view.setEnabled(false);
                    findViewById(R.id.card).setEnabled(true);
                    findViewById(R.id.j20).setEnabled(true);
                    mAdapter.setType(PathAdapter.TYPE_DRAGON);
                }
                break;
            case R.id.normal:
                if (checkIsPathInitialized()) {
                    view.setEnabled(false);
                    findViewById(R.id.overflow).setEnabled(true);
                    findViewById(R.id.loop).setEnabled(true);
                    mPathLayoutManager.setScrollMode(PathLayoutManager.SCROLL_MODE_NORMAL);
                }
                break;
            case R.id.overflow:
                if (checkIsPathInitialized()) {
                    view.setEnabled(false);
                    findViewById(R.id.normal).setEnabled(true);
                    findViewById(R.id.loop).setEnabled(true);
                    mPathLayoutManager.setScrollMode(PathLayoutManager.SCROLL_MODE_OVERFLOW);
                }
                break;
            case R.id.loop:
                if (checkIsPathInitialized()) {
                    view.setEnabled(false);
                    findViewById(R.id.overflow).setEnabled(true);
                    findViewById(R.id.normal).setEnabled(true);
                    mPathLayoutManager.setScrollMode(PathLayoutManager.SCROLL_MODE_LOOP);
                }
                break;
            case R.id.apply_scale_ratio:
                if (checkIsPathInitialized()) {
                    String content = ((TextView) findViewById(R.id.scale_ratio_text)).getText().toString();
                    if (TextUtils.isEmpty(content)) {
                        mPathLayoutManager.setItemScaleRatio();
                    } else {
                        String[] ratiosString = content.split(",");
                        float[] ratios = new float[ratiosString.length];
                        try {
                            for (int i = 0; i < ratiosString.length; i++) {
                                ratios[i] = Float.parseFloat(ratiosString[i]);
                            }
                            mPathLayoutManager.setItemScaleRatio(ratios);
                            mToast.setText(R.string.success);
                        } catch (Exception e) {
                            mToast.setText(e.toString());
                        }
                        mToast.show();
                    }
                }
                break;
            default:
                break;
        }
    }

    private boolean checkIsPathInitialized() {
        if (!isPathInitialized) {
            mToast.setText(R.string.path_not_set);
            mToast.show();
        }
        return isPathInitialized;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (checkIsPathInitialized()) {
            switch (buttonView.getId()) {
                case R.id.orientation:
                    mPathLayoutManager.setOrientation(isChecked ? RecyclerView.HORIZONTAL : RecyclerView.VERTICAL);
                    break;
                case R.id.direction_fixed:
                    mPathLayoutManager.setItemDirectionFixed(isChecked);
                    break;
                case R.id.auto_select:
                    mPathLayoutManager.setAutoSelect(isChecked);
                    break;
                case R.id.disable_fling:
                    mPathLayoutManager.setFlingEnable(!isChecked);
                    break;
                case R.id.show_path:
                    isShowPath = isChecked;
                    if (isChecked) {
                        if (mCanvasView.getVisibility() == View.INVISIBLE) {
                            mTrackView.setVisibility(View.VISIBLE);
                        }
                    } else {
                        mTrackView.setVisibility(View.INVISIBLE);
                    }
                    break;
                default:
                    break;
            }
        } else {
            buttonView.setChecked(!isChecked);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.item_offset:
                mPathLayoutManager.setItemOffset(progress);
                mToast.setText(String.valueOf(progress));
                break;
            case R.id.auto_select_fraction:
                float fraction = progress / 100F;
                mPathLayoutManager.setAutoSelectFraction(fraction);
                mToast.setText(String.valueOf(fraction));
                break;
            case R.id.fixing_animation_duration:
                mPathLayoutManager.setFixingAnimationDuration(progress);
                mToast.setText(String.valueOf(progress));
                break;
            default:
                break;
        }
        mToast.show();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
