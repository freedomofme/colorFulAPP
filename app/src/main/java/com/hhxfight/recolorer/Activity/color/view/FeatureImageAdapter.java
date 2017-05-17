package com.hhxfight.recolorer.Activity.color.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.hhxfight.recolorer.R;
import com.hhxfight.recolorer.bean.LocalImageBean;

import java.util.ArrayList;

/**
 * 五官的图片，ViewPage下的多个fragment通用
 * 
 * @author HHX
 * @version 创建时间:2015年11月04日13:22:27
 * 
 */
public class FeatureImageAdapter extends BaseAdapter
{
	private Context mContext;
	private ArrayList<LocalImageBean> mFeatrueData;
	private ViewHolder mHolder;
	private int type = 0;

	public FeatureImageAdapter(Context context, ArrayList<LocalImageBean> data, int type)
	{
		mContext = context;
		mFeatrueData = data;
		this.type = type;
	}

	@Override
	public int getCount()
	{
		return mFeatrueData.size();
	}

	@Override
	public Object getItem(int position)
	{
		return mFeatrueData.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		if (null == convertView)
		{
			if (type == 0) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.gridview_feature_image_item, null);
			} else if(type == 1) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.gridview_feature_image_item2, null);
			}


			mHolder = new ViewHolder();
			mHolder.iv_feature = (ImageView) convertView.findViewById(R.id.iv_feature);
		
			convertView.setTag(mHolder);
		} else
		{
			mHolder = (ViewHolder) convertView.getTag();
		}

		mHolder.iv_feature.setImageBitmap(mFeatrueData.get(position).bitmap);
		
		return convertView;
	}

	static class ViewHolder
	{
		ImageView iv_feature;
	}
}
