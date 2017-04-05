package com.hhxfight.recolorer.main.presenter;

import android.os.Handler;
import android.os.Looper;

import com.hhxfight.recolorer.main.view.IMainView;

/**
 * Created by HHX on 2017/3/23.
 */

public class MainPresenter implements IMainPresenter {
    IMainView iMainView;
    Handler handler;
    public MainPresenter(IMainView iMainView) {
        this.iMainView = iMainView;
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void getPreefinedMainFold() {

    }
}
