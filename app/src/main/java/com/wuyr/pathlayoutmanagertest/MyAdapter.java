package com.wuyr.pathlayoutmanagertest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by wuyr on 18-5-20 上午4:09.
 */
public class MyAdapter extends BaseAdapter<String, MyAdapter.ViewHolder> {

    private Toast toast;

    public MyAdapter(Context context, List<String> data) {
        super(context, data, R.layout.adapter_item_view, ViewHolder.class);
        toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//
//        if (position == 0) {
//            holder.imageView.setImageResource(R.drawable.ic_dragon_tail);
//        } else if (position == mData.size() - 1) {
//            holder.imageView.setImageResource(R.drawable.ic_dragon_head);
//        } else {
//            holder.imageView.setImageResource(new Random().nextBoolean() ? R.drawable.ic_dragon_body_1 : R.drawable.ic_dragon_body_2);
//        }
        holder.imageView.setImageResource(R.drawable.ic_j20);
        holder.rootView.setOnClickListener(v -> {
            toast.setText("点击了： " + holder.textView.getText().toString());
            toast.show();
        });
        holder.textView.setText(position + "");
        holder.textView.setBackgroundColor(mContext.getResources().getColor(position % 5 == 0 ? R.color.colorPrimary : R.color.colorAccent));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        View rootView;
        TextView textView;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_view);
            rootView = itemView.findViewById(R.id.root_view);
            imageView = itemView.findViewById(R.id.image);
        }
    }
}
