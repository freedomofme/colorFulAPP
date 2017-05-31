package com.hhxfight.recolorer.Activity.mywork;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.hhxfight.recolorer.Activity.color.view.FeatureImageAdapter;
import com.hhxfight.recolorer.R;
import com.hhxfight.recolorer.bean.LocalImageBean;
import com.hhxfight.recolorer.config.Url;
import com.hhxfight.recolorer.util.ImageIoUtil;
import com.shizhefei.fragment.LazyFragment;

import java.io.File;
import java.util.ArrayList;

public class WorkFragment extends LazyFragment {
	public static final String INTENT_INT_INDEX = "HHX";

	public GridView gv_featureimage;
	private ArrayList<LocalImageBean> manifoldList = new ArrayList<>();
	private ArrayList<LocalImageBean> userManifoldList = new ArrayList<>();
	private ArrayList<LocalImageBean> grayList = new ArrayList<>();
	private ArrayList<LocalImageBean> colorList = new ArrayList<>();

	@Override
	protected void onCreateViewLazy(Bundle savedInstanceState) {
		super.onCreateViewLazy(savedInstanceState);
		setContentView(R.layout.fragment_featureimage);
		init();
	}
	
	private void init() {
		gv_featureimage = (GridView) findViewById(R.id.gv_featureimage);

		if (0 == getArguments().getInt(INTENT_INT_INDEX)) {
			manifoldList.clear();
			readImages(manifoldList, 0, 150);

			gv_featureimage.setAdapter(new FeatureImageAdapter(getActivity(), manifoldList, 0));
		}

		if (1 == getArguments().getInt(INTENT_INT_INDEX)) {
			userManifoldList.clear();
			readImages(userManifoldList, 1, 150);

			gv_featureimage.setAdapter(new FeatureImageAdapter(getActivity(), userManifoldList, 0));
		}

		if (2 == getArguments().getInt(INTENT_INT_INDEX)) {
			grayList.clear();
			readImages(grayList, 2, 300);
			gv_featureimage.setAdapter(new FeatureImageAdapter(getActivity(), grayList, 1));
		}

		if (3 == getArguments().getInt(INTENT_INT_INDEX)) {
			colorList.clear();

			readImages(colorList, 3, 300);
			gv_featureimage.setAdapter(new FeatureImageAdapter(getActivity(), colorList, 1));
		}

		gv_featureimage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				((FeatureImageAdapter)(gv_featureimage.getAdapter())).setSelectedPosition(position);
			}
		});
	}

	private void readImages(ArrayList<LocalImageBean> list, int choice, int size) {
		String root = Environment.getExternalStorageDirectory().toString();
		if (choice == 1) {
			String path = root + Url.APPDIR + Url.MANIFOLD + Url.USERDEF;
			File[] files = ImageIoUtil.getImageByPaths(path);
			if (files == null)
				return ;
			for (File file : files)
				list.add(new LocalImageBean(ImageIoUtil.decodeSampledBitmap(file.getAbsolutePath(), size, size), file.getAbsolutePath()));
		} else if (choice == 2) {
			String path = root + Url.APPDIR + Url.GRAY + Url.USERDEF;
			File[] files = ImageIoUtil.getImageByPaths(path);
			if (files == null)
				return ;
			for (File file : files)
				list.add(new LocalImageBean(ImageIoUtil.decodeSampledBitmap(file.getAbsolutePath(), size, size), file.getAbsolutePath()));
		} else if (choice == 3) {
			String path = root + Url.APPDIR + Url.COLOR + Url.USERDEF;
			File[] files = ImageIoUtil.getImageByPaths(path);
			if (files == null)
				return ;
			for (File file : files)
				list.add(new LocalImageBean(ImageIoUtil.decodeSampledBitmap(file.getAbsolutePath(), size, size), file.getAbsolutePath()));
		} if (choice == 0) {
			String path = root + Url.APPDIR + Url.MANIFOLD + Url.PREDEF;
			File[] files = ImageIoUtil.getImageByPaths(path);
			if (files == null)
				return ;
			for (File file : files)
				list.add(new LocalImageBean(ImageIoUtil.decodeSampledBitmap(file.getAbsolutePath(), size, size), file.getAbsolutePath()));
		}
	}

	@Override
	public void onDestroyViewLazy() {
		super.onDestroyViewLazy();
	}

}
