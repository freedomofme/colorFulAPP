
package com.hhxfight.recolorer.Activity.mywork;


import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.AnyRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hhxfight.recolorer.Activity.color.view.FeatureImageAdapter;
import com.hhxfight.recolorer.Activity.color.view.GridFeatureImageFragment;
import com.hhxfight.recolorer.R;
import com.shizhefei.view.indicator.FragmentListPageAdapter;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

import java.io.File;


public class MyWorkActivity extends FragmentActivity {
    private String[] names = {"系统模板", "我的模板", "灰色的图片", "换色的图片"};
    private int size = names.length;
    private LayoutInflater inflate;
    private MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mywork);

        inflate = LayoutInflater.from(getBaseContext());
        ScrollIndicatorView indicator = (ScrollIndicatorView) findViewById(R.id.siv_features);
        indicator.setScrollBar(new ColorBar(this, Color.RED, 5));
        int selectColorId = R.color.tab_top_text_2;
        int unSelectColorId = R.color.tab_top_text_1;
        indicator.setOnTransitionListener(new OnTransitionTextListener().setColorId(this, selectColorId, unSelectColorId));

        ViewPager viewPager = (ViewPager) findViewById(R.id.vp_features);

        viewPager.setOffscreenPageLimit(2);
        IndicatorViewPager indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        inflate = LayoutInflater.from(getApplicationContext());
        myAdapter = new MyAdapter(getSupportFragmentManager());
        indicatorViewPager.setAdapter(myAdapter);

    }

    public void toShare(View view) {
//        Uri imageUri = getUriToDrawable(view.getContext(), R.drawable.sunrise);
        WorkFragment workFragment = (WorkFragment) myAdapter.getCurrentFragment();
        Uri imageUri = Uri.fromFile(new File(((FeatureImageAdapter)(workFragment.gv_featureimage.getAdapter())).getSelectedPath()));

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "我在焕彩制作的图片");
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType("image/jpeg");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(shareIntent, "send"));
    }

    public static final Uri getUriToDrawable(@NonNull Context context,
                                             @AnyRes int drawableId) {
        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + context.getResources().getResourcePackageName(drawableId)
                + '/' + context.getResources().getResourceTypeName(drawableId)
                + '/' + context.getResources().getResourceEntryName(drawableId) );
        return imageUri;
    }

    private class MyAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {
        public MyAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return size;
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = inflate.inflate(R.layout.tab_top, container, false);
            }
            TextView textView = (TextView) convertView;
            textView.setText(names[position % names.length]);
            textView.setPadding(20, 0, 20, 0);
            return convertView;
        }

        @Override
        public Fragment getFragmentForPage(int position) {
            WorkFragment workFragment = new WorkFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(GridFeatureImageFragment.INTENT_INT_INDEX, position);
            workFragment.setArguments(bundle);
            return workFragment;
        }

        @Override
        public int getItemPosition(Object object) {
            return FragmentListPageAdapter.POSITION_NONE;
        }

    }


}