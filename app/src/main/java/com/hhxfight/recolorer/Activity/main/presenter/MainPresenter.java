package com.hhxfight.recolorer.Activity.main.presenter;

import android.content.Context;

import com.hhxfight.recolorer.Activity.main.model.PreManifoldSaveModel;
import com.hhxfight.recolorer.Activity.main.view.IMainView;

/**
 * Created by HHX on 2017/3/23.
 */

public class MainPresenter implements IMainPresenter {
    private IMainView iMainView;
    private Context mContext;
    private PreManifoldSaveModel preManifoldSaveModel;
    public MainPresenter(IMainView iMainView, Context mContext) {
        this.iMainView = iMainView;
        this.mContext = mContext;
        preManifoldSaveModel = new PreManifoldSaveModel(mContext);
    }

    @Override
    public void getPrefinedMainFold() {

    }

    @Override
    public void savePreDefinedMainFold() {
        preManifoldSaveModel.saveManifoldIntoFile();
    }
}
