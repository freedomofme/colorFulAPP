package com.hhxfight.recolorer.Activity.color.view;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.hhxfight.recolorer.R;
import com.hhxfight.recolorer.bean.LocalImageBean;
import com.hhxfight.recolorer.config.Url;
import com.hhxfight.recolorer.util.ImageIoUtil;
import com.shizhefei.fragment.LazyFragment;

import java.io.File;
import java.util.ArrayList;

public class GridFeatureImageFragment extends LazyFragment {
	public static final String INTENT_INT_INDEX = "HHX";

	private GridView gv_featureimage;
	private ArrayList<LocalImageBean> manifoldList = new ArrayList<>();
	private ArrayList<LocalImageBean> userManifoldList = new ArrayList<>();

	@Override
	protected void onCreateViewLazy(Bundle savedInstanceState) {
		super.onCreateViewLazy(savedInstanceState);
		setContentView(R.layout.fragment_featureimage);
		init();
	}
	
	private void init() {
		if (0 == getArguments().getInt(INTENT_INT_INDEX)) {
			readImages(manifoldList, 0, 150);
			gv_featureimage = (GridView) findViewById(R.id.gv_featureimage);
			gv_featureimage.setAdapter(new FeatureImageAdapter(getActivity(), manifoldList, 0));
			gv_featureimage.setOnItemClickListener((parent, view, position, id) -> {
                view.setSelected(true);
                ((ColorTransformActivity)getActivity()).iColorPresenter.postTempleteImage(manifoldList.get(position).absPath);
            });
		}

		if (1 == getArguments().getInt(INTENT_INT_INDEX)) {
			userManifoldList.clear();
			readImages(userManifoldList, 1, 150);
			gv_featureimage = (GridView) findViewById(R.id.gv_featureimage);
			gv_featureimage.setAdapter(new FeatureImageAdapter(getActivity(), userManifoldList, 0));
			gv_featureimage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					view.setSelected(true);
					((ColorTransformActivity)getActivity()).iColorPresenter.postTempleteImage(userManifoldList.get(position).absPath);
				}
			});

		}
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
		}if (choice == 0) {
			String path = root + Url.APPDIR + Url.MANIFOLD + Url.PREDEF;
			File[] files = ImageIoUtil.getImageByPaths(path);
			if (files == null)
				return ;
			for (File file : files)
				list.add(new LocalImageBean(ImageIoUtil.decodeSampledBitmap(file.getAbsolutePath(), size, size),file.getAbsolutePath()));
		}
	}

	@Override
	public void onDestroyViewLazy() {
		super.onDestroyViewLazy();
	}

}
