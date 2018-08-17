package com.wuyr.pathlayoutmanagertest;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
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
        initBitmaps();
    }

    private void initBitmaps() {
        if (mBitmapList != null) {
            for (int i = 0; i < mBitmapList.size(); i++) {
                SoftReference<Bitmap> softReference = mBitmapList.get(i);
                if (softReference.get() == null) {
                    mBitmapList.remove(i);
                    mBitmapList.add(i, getBitmapById(getIdByIndex(i)));
                }
            }
        } else {
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
    }

    private int getIdByIndex(int index) {
        int id = -1;
        switch (index) {
            case 0:
                id = R.drawable.ic_1;
                break;
            case 1:
                id = R.drawable.ic_2;
                break;
            case 2:
                id = R.drawable.ic_3;
                break;
            case 3:
                id = R.drawable.ic_j20;
                break;
            case 4:
                id = R.drawable.ic_dragon_head;
                break;
            case 5:
                id = R.drawable.ic_dragon_body_1;
                break;
            case 6:
                id = R.drawable.ic_dragon_body_2;
                break;
            case 7:
                id = R.drawable.ic_dragon_tail;
                break;
            default:
                break;
        }
        return id;
    }

    @NonNull
    private SoftReference<Bitmap> getBitmapById(int id) {
        return new SoftReference<>(decodeSampledBitmapFromResource(mContext.getResources(), id));
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
            mToast.setText(String.format(Locale.getDefault(),
                    "item %s clicked", holder.getAdapterPosition()));
            mToast.show();
        });
    }

    private void initCardHolder(ViewHolder holder) {
        holder.imageView.setVisibility(View.VISIBLE);
        holder.imageView2.setImageBitmap(null);
        holder.imageView2.setVisibility(View.GONE);

        holder.imageView.getLayoutParams().width = 360;
        holder.imageView.requestLayout();
        holder.imageView.setImageBitmap(getBitmap(mRandom.nextInt(3)));
    }

    private void initJ20Holder(ViewHolder holder) {
        holder.imageView2.setVisibility(View.VISIBLE);
        holder.imageView.setImageBitmap(null);
        holder.imageView.setVisibility(View.GONE);

        holder.imageView2.getLayoutParams().width = 180;
        holder.imageView2.requestLayout();
        holder.imageView2.setImageBitmap(getBitmap(3));
    }

    private void initDragonHolder(ViewHolder holder, int position) {
        holder.imageView2.setVisibility(View.VISIBLE);
        holder.imageView.setImageBitmap(null);
        holder.imageView.setVisibility(View.GONE);

        holder.imageView2.getLayoutParams().width = 135;
        holder.imageView2.requestLayout();
        if (position == 0) {
            holder.imageView2.setImageBitmap(getBitmap(7));
        } else if (position == mData.size() - 1) {
            holder.imageView2.setImageBitmap(getBitmap(4));
        } else {
            holder.imageView2.setImageBitmap(getBitmap(mRandom.nextBoolean() ? 5 : 6));
        }
    }

    private Bitmap getBitmap(int index) {
        Bitmap bitmap = mBitmapList.get(index).get();
        if (bitmap == null) {
            initBitmaps();
            return mBitmapList.get(index).get();
        }
        return bitmap;
    }

    public void setType(int type) {
        mCurrentType = type;
        notifyItemRangeChanged(0, getItemCount());
    }
    private int calculateInSampleSize(BitmapFactory.Options options) {
        int reqWidth = 0;
        int reqHeight = 0;
        switch (mCurrentType) {
            case TYPE_CARD:
                reqWidth = 180;
                reqHeight = 180;
                break;
            case TYPE_J20:
                reqWidth = 135;
                reqHeight = 208;
                break;
            case TYPE_DRAGON:
                reqWidth = 68;
                reqHeight = 116;
                break;
            default:
                break;
        }
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    private Bitmap decodeSampledBitmapFromResource(Resources res, int resId) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        try {
            return BitmapFactory.decodeResource(res, resId, options);
        } catch (Exception e) {
            return null;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView imageView;
        ImageView imageView2;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card);
            imageView = itemView.findViewById(R.id.image);
            imageView2 = itemView.findViewById(R.id.image2);
        }
    }
}
