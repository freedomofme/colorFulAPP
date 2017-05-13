package com.hhxfight.recolorer.Activity.gray.presenter;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.hhxfight.recolorer.Activity.gray.view.IGaryView;
import com.hhxfight.recolorer.config.Url;
import com.hhxfight.recolorer.requestfactory.DefaultErrorListener;
import com.hhxfight.recolorer.requestfactory.MultipartRequest;
import com.hhxfight.recolorer.volley.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by HHX on 2017/5/3.
 */

public class GrayscalePresenter implements IGrayPresenter {
    Context mContext;
    IGaryView iGrayView;
    String gid;
    String sid;
    String mid;

    public GrayscalePresenter(Context mContext, IGaryView iGrayView) {
        this.mContext = mContext;
        this.iGrayView = iGrayView;
    }

    @Override
    public void postImage(String path) {
        MultipartRequest multipartRequest = new MultipartRequest(Url.uploadImage, path, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Tag", response.toString());
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length();i++) {
                        JSONObject obj = jsonArray.optJSONObject(i);
                        JSONArray subArray = obj.optJSONArray("links");

                        sid = subArray.optString(0);
                        mid = subArray.optString(1);
                    }
                    iGrayView.onImgaePosted();
                    doGray();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new DefaultErrorListener(mContext));

        MySingleton.getInstance(mContext.getApplicationContext()).getRequestQueue().add(multipartRequest);
    }


    @Override
    public void doGray() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("image_sha1", sid);

        JsonObjectRequest request_json = new JsonObjectRequest(Url.gray, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        iGrayView.onImageGrayed(sid);
                    }
                }, new DefaultErrorListener(mContext));
        MySingleton.getInstance(mContext.getApplicationContext()).getRequestQueue().add(request_json);
    }

}
