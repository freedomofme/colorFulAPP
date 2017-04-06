package com.hhxfight.recolorer.Activity.color;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.hhxfight.recolorer.R;

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
	private ArrayList<Integer> mFeatrueData;
	private ViewHolder mHolder;

	public FeatureImageAdapter(Context context, ArrayList<Integer> data)
	{
		mContext = context;
		mFeatrueData = data;
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
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.gridview_feature_image_item, null);
			mHolder = new ViewHolder();
			mHolder.iv_feature = (ImageView) convertView.findViewById(R.id.iv_feature);
		
			convertView.setTag(mHolder);
		} else
		{
			mHolder = (ViewHolder) convertView.getTag();
		}

		mHolder.iv_feature.setImageResource(mFeatrueData.get(position));
		
		return convertView;
	}

	static class ViewHolder
	{
		ImageView iv_feature;
	}
}
