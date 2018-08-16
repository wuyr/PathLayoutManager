package com.wuyr.pathlayoutmanagertest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Created by wuyr on 18-5-20 上午4:09.
 */
public class PathAdapter extends BaseAdapter<String, PathAdapter.ViewHolder> {

    public static final int TYPE_CARD = 0, TYPE_J20 = 1, TYPE_DRAGON = 2;
    private Toast mToast;
    private int mCurrentType;
    private Random mRandom = new Random();
    private List<SoftReference<Bitmap>> mBitmapList;

    public PathAdapter(Context context, List<String> data) {
        super(context, data, R.layout.adapter_item_view, ViewHolder.class);
        mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
    }

    private void initBitmaps() {
        mBitmapList = new ArrayList<>();
        mBitmapList.add(getBitmapById(R.drawable.ic_1));
        mBitmapList.add(getBitmapById(R.drawable.ic_2));
        mBitmapList.add(getBitmapById(R.drawable.ic_3));
        mBitmapList.add(getBitmapById(R.drawable.ic_j20));
        mBitmapList.add(getBitmapById(R.drawable.ic_dragon_head));
        mBitmapList.add(getBitmapById(R.drawable.ic_dragon_body_1));
        mBitmapList.add(getBitmapById(R.drawable.ic_dragon_body_2));
        mBitmapList.add(getBitmapById(R.drawable.ic_dragon_tail));
    }

    @NonNull
    private SoftReference<Bitmap> getBitmapById(int id) {
        return new SoftReference<>(BitmapFactory.decodeResource(mContext.getResources(), id));
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        recyclerBitmaps();
    }

    private void recyclerBitmaps() {
        for (SoftReference<Bitmap> softReference : mBitmapList) {
            Bitmap bitmap = softReference.get();
            if (bitmap != null) {
                bitmap.recycle();
            }
            softReference.clear();
        }
        mBitmapList.clear();
        mBitmapList = null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        switch (mCurrentType) {
            case TYPE_CARD:
                initCardHolder(holder);
                break;
            case TYPE_J20:
                initJ20Holder(holder);
                break;
            case TYPE_DRAGON:
                initDragonHolder(holder, position);
                break;
            default:
                break;
        }
        holder.itemView.setOnClickListener(v -> {
            mToast.setText(String.format(Locale.getDefault(), "item %s clicked", holder.getAdapterPosition()));
            mToast.show();
        });
    }

    private void initCardHolder(ViewHolder holder) {
        holder.imageView.getLayoutParams().width = 360;
        holder.itemView.requestLayout();
        holder.imageView.setImageBitmap(getBitmap(mRandom.nextInt(3)));
    }

    private void initJ20Holder(ViewHolder holder) {
        holder.imageView.getLayoutParams().width = 270;
        holder.itemView.requestLayout();
        holder.imageView.setImageBitmap(getBitmap(3));
    }

    private void initDragonHolder(ViewHolder holder, int position) {
        holder.imageView.getLayoutParams().width = 135;
        holder.itemView.requestLayout();
        if (position == 0) {
            holder.imageView.setImageBitmap(getBitmap(7));
        } else if (position == mData.size() - 1) {
            holder.imageView.setImageBitmap(getBitmap(4));
        } else {
            holder.imageView.setImageBitmap(getBitmap(mRandom.nextBoolean() ? 5 : 6));
        }
    }

    private Bitmap getBitmap(int index) {
        if (mBitmapList == null) {
            initBitmaps();
        }
        Bitmap bitmap = mBitmapList.get(index).get();
        return bitmap;
    }

    public void setType(int type) {
        mCurrentType = type;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
        }
    }
}
