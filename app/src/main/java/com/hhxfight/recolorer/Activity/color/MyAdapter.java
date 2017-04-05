package com.hhxfight.recolorer.Activity.color;

/**
 * Created by HHX on 2017/4/6.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hhxfight.recolorer.R;
import com.hhxfight.recolorer.util.AssetsUtil;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private String[] picUrls;
    private Context mContext;

    public MyAdapter(Context context, String[] picUrls) {
        this.picUrls = picUrls;
        mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

//        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_vertical, viewGroup, false);
//        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_horizontal, viewGroup, false);
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_gridlayout, viewGroup, false);
//        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_staggered_gridlayout, viewGroup, false);

        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int i) {
        // 数据绑定
        viewHolder.picImageView.setImageBitmap(AssetsUtil.getBitmap(mContext, "apple_0.png"));
        viewHolder.picUrl.setText(picUrls[i]);
    }

    @Override
    public int getItemCount() {
        // 返回数据有多少条
        if (null == picUrls) {
            return 0;
        }
        return picUrls.length;
    }

    // 可复用的VH
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView picImageView;
        public TextView picUrl;

        public MyViewHolder(View itemView) {
            super(itemView);
            picImageView = (ImageView) itemView.findViewById(R.id.imavPic);
            picUrl = (TextView) itemView.findViewById(R.id.tvUrl);
        }
    }
}