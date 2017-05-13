package com.hhxfight.recolorer.Activity.color.view;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.hhxfight.recolorer.R;
import com.shizhefei.fragment.LazyFragment;

import java.util.ArrayList;

public class GridFeatureImageFragment extends LazyFragment {
	public static final String INTENT_INT_INDEX = "HHX";

	private GridView gv_featureimage;
	private ArrayList<Integer> manifoldList = new ArrayList<>();
	private ArrayList<Integer> userManifoldList = new ArrayList<>();

	@Override
	protected void onCreateViewLazy(Bundle savedInstanceState) {
		super.onCreateViewLazy(savedInstanceState);
		setContentView(R.layout.fragment_featureimage);
		init();
	}
	
	private void init() {
		if (0 == getArguments().getInt(INTENT_INT_INDEX)) {
			for (int i = 0; i <= 20; i++) {
				int drawable = getActivity().getResources().getIdentifier("sys" + i + "",
						"drawable", getActivity().getPackageName());
				manifoldList.add(drawable);
			}
			gv_featureimage = (GridView) findViewById(R.id.gv_featureimage);
			gv_featureimage.setAdapter(new FeatureImageAdapter(getActivity(), manifoldList, 0));
		}

		if (1 == getArguments().getInt(INTENT_INT_INDEX)) {
			userManifoldList.clear();
			for (int i = 1; i <= 3; i++) {
				int drawable = getActivity().getResources().getIdentifier("onedim" + i + "",
						"drawable", getActivity().getPackageName());
				userManifoldList.add(drawable);
			}
			gv_featureimage = (GridView) findViewById(R.id.gv_featureimage);
			gv_featureimage.setAdapter(new FeatureImageAdapter(getActivity(), userManifoldList, 0));
			gv_featureimage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					view.setSelected(true);
					System.out.println("!!!!!!");
				}
			});
		}


	}

	@Override
	public void onDestroyViewLazy() {
		super.onDestroyViewLazy();
	}

}
