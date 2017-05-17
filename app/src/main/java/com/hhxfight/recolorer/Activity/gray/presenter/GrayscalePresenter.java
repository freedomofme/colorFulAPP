package com.hhxfight.recolorer.Activity.gray.presenter;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.hhxfight.recolorer.Activity.gray.view.IGaryView;
import com.hhxfight.recolorer.config.Url;
import com.hhxfight.recolorer.requestfactory.DefaultErrorListener;
import com.hhxfight.recolorer.requestfactory.MultipartRequest;
import com.hhxfight.recolorer.util.ImageIoUtil;
import com.hhxfight.recolorer.volley.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by HHX on 2017/5/3.
 */

public class GrayscalePresenter implements IGrayPresenter {
    Context mContext;
    IGaryView iGrayView;
    String gid;
    String sid;
    String mid;
    String holdPath;

    public GrayscalePresenter(Context mContext, IGaryView iGrayView) {
        this.mContext = mContext;
        this.iGrayView = iGrayView;
    }

    @Override
    public void postImage(String p, int kNearest) {
        if (p != null && p.length() > 0)
            this.holdPath = p;

        MultipartRequest multipartRequest = new MultipartRequest(Url.uploadImage, holdPath, new Response.Listener<String>() {
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
                    doGray(kNearest);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new DefaultErrorListener(mContext));

        MySingleton.getInstance(mContext.getApplicationContext()).getRequestQueue().add(multipartRequest);
    }

    private void doGray(int kNearest) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("image_sha1", sid);
        params.put("k", kNearest + "");

        JsonObjectRequest request_json = new JsonObjectRequest(Url.gray, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            gid = response.getString("sha1_gray");
                            iGrayView.onImageGrayed(gid);
                            getGray(gid);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
        }, new DefaultErrorListener(mContext));

        request_json.setRetryPolicy(new DefaultRetryPolicy((int) TimeUnit.SECONDS.toMillis(20),1,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingleton.getInstance(mContext.getApplicationContext()).getRequestQueue().add(request_json);
    }

    @Override
    public void getGray(final String gid) {
        MySingleton.getInstance(mContext.getApplicationContext()).getImageLoader().get(Url.grayImage + "/" + gid, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                if (response.getBitmap() != null) {
                    iGrayView.onGrayedImageGet(response);
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    @Override
    public void saveGray(View bg) {
        bg.buildDrawingCache();
        ImageIoUtil.saveBitmap(Url.APPDIR + Url.GRAY + Url.USERDEF, System.currentTimeMillis() +".png", bg.getDrawingCache());
        bg.destroyDrawingCache();
    }

}
