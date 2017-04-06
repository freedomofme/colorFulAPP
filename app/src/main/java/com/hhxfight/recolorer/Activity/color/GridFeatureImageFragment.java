package com.hhxfight.recolorer.Activity.color;

import android.os.Bundle;
import android.widget.GridView;

import com.hhxfight.recolorer.R;
import com.shizhefei.fragment.LazyFragment;

import java.util.ArrayList;

public class GridFeatureImageFragment extends LazyFragment {
	public static final String INTENT_INT_INDEX = "HHX";

	private GridView gv_featureimage;
	private ArrayList<Integer> manifoldList = new ArrayList<>();

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
		}
		gv_featureimage = (GridView) findViewById(R.id.gv_featureimage);
		gv_featureimage.setAdapter(new FeatureImageAdapter(getActivity(), manifoldList));
	}

	@Override
	public void onDestroyViewLazy() {
		super.onDestroyViewLazy();
	}

}
