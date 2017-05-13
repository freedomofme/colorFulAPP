package com.hhxfight.recolorer.Activity.manifold.presenter;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.hhxfight.recolorer.Activity.manifold.view.IManifoldView;
import com.hhxfight.recolorer.config.Url;
import com.hhxfight.recolorer.requestfactory.DefaultErrorListener;
import com.hhxfight.recolorer.requestfactory.MultipartRequest;
import com.hhxfight.recolorer.volley.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by HHX on 2017/5/5.
 */

public class ManifoldPresenter implements IManifoldPresenter{
    Context mContext;
    IManifoldView iManifoldView;
    String gid;
    String sid;
    String mid;
    public  ManifoldPresenter(Context mContext, IManifoldView iManifoldView){
        this.mContext = mContext;
        this.iManifoldView = iManifoldView;
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
                    iManifoldView.onImagePosted(sid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new DefaultErrorListener(mContext));

        multipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingleton.getInstance(mContext.getApplicationContext()).getRequestQueue().add(multipartRequest);
    }

    @Override
    public void getManifold(final int choice) {
        String path = null;
        if (choice == 1)
            path = Url.m1Image;
        else if (choice == 2)
            path = Url.m2Image;
        MySingleton.getInstance(mContext.getApplicationContext()).getImageLoader().get(path+ "/" + sid, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                if (response.getBitmap() != null) {
                    iManifoldView.onManifoldGet(response, choice);
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }
}
