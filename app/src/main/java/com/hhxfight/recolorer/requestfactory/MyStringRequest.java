package com.hhxfight.recolorer.requestfactory;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

/**
 * Created by HHX on 2017/3/23.
 */

public class MyStringRequest extends StringRequest {
    protected static final String TYPE_UTF8_CHARSET = "charset=UTF-8";

    public MyStringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    private Map<String, String> mParams;

    public void setPostParam(Map<String, String> map) {
        mParams = map;
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String str = null;
        addEncodeing2Request(response);
        return super.parseNetworkResponse(response);
    }

    @Override
    public int getDefaultTtl() {
        return 1 * 3600 * 1000;
    }

    @Override
    public int getDefaultSoftTtl() {
        return 0 * 60 * 1000;
    }

    @Override
    public String getCacheKey() {
        String temp = super.getCacheKey();
        if (mParams == null)
            return temp;
        for (Map.Entry<String, String> entry : mParams.entrySet())
            temp += entry.getKey() + "=" + entry.getValue();
        return temp;
    }

    private static void addEncodeing2Request(NetworkResponse response) {
        final String CONTENT_TYPE = "Content-Type";
        try {
            String type = response.headers.get(CONTENT_TYPE);
            if (type == null) {
                type = TYPE_UTF8_CHARSET;
                response.headers.put(CONTENT_TYPE, type);
            } else if (!type.contains("charset")) {
                type += ";" + TYPE_UTF8_CHARSET;
                response.headers.put(CONTENT_TYPE, type);
            } else {
                Log.d("RVA", "charset is " + type);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}