package com.hhxfight.recolorer.Activity.color.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.hhxfight.recolorer.Activity.color.view.IColorTransformView;
import com.hhxfight.recolorer.config.Url;
import com.hhxfight.recolorer.database.DBHelper;
import com.hhxfight.recolorer.database.MainfoldDao;
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
 * Created by HHX on 2017/5/17.
 */

public class ColorTransformPresenter implements IColorPresenter {
    Context mContext;
    IColorTransformView iColorTransformView;
    String sid;
    String templeteSid;
    String rid;
    public  ColorTransformPresenter(Context mContext, IColorTransformView iColorTransformView){
        this.mContext = mContext;
        this.iColorTransformView = iColorTransformView;
    }


    @Override
    public void postImage(String path) {
        MultipartRequest multipartRequest = new MultipartRequest(Url.uploadImageNotCreateM, path, new Response.Listener<String>() {
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
                    }
                    iColorTransformView.onImagePosted(path);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new DefaultErrorListener(mContext));

        multipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingleton.getInstance(mContext.getApplicationContext()).getRequestQueue().add(multipartRequest);
    }

    @Override
    public void postTempleteImage(String path) {
        MainfoldDao mainfoldDao = new MainfoldDao(DBHelper.getInstance(mContext));
        String holdTempleteSid = mainfoldDao.getSid(path);
        if (holdTempleteSid != null) {
            templeteSid = holdTempleteSid;
            Log.i("Tag", "cache got, sid: " + templeteSid);
            doColoredTransform(0);
            return;
        }

        MultipartRequest multipartRequest = new MultipartRequest(Url.uploadImageNotCreateM, path, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Tag", response.toString());
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length();i++) {
                        JSONObject obj = jsonArray.optJSONObject(i);
                        JSONArray subArray = obj.optJSONArray("links");
                        templeteSid = subArray.optString(0);
                        mainfoldDao.insert(path,templeteSid);
                        doColoredTransform(0);
                    }

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
    public void doColoredTransform(int templateSource) {
        if (sid == null || templeteSid == null) {
            Toast.makeText(mContext, "请等待图片上传完成", Toast.LENGTH_SHORT).show();
            return;
        }

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("image_sha1", sid);
        params.put("template_sha1", templeteSid);

        JsonObjectRequest request_json = new JsonObjectRequest(Url.recolor, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            rid = response.getString("rid");
                            getColorTransformedImage(rid);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new DefaultErrorListener(mContext));

        request_json.setRetryPolicy(new DefaultRetryPolicy((int) TimeUnit.SECONDS.toMillis(20),1,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingleton.getInstance(mContext.getApplicationContext()).getRequestQueue().add(request_json);
    }

    @Override
    public void getColorTransformedImage(final String rid) {
        MySingleton.getInstance(mContext.getApplicationContext()).getImageLoader().get(Url.colorImage + "/" + rid, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                if (response.getBitmap() != null) {
                    iColorTransformView.onTransformedImageGet(response);
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    @Override
    public void saveColor(Bitmap bitmap) {
        ImageIoUtil.saveBitmap(Url.APPDIR + Url.COLOR + Url.USERDEF, System.currentTimeMillis() +".png", bitmap);
    }


}
