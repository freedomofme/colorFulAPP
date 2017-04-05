package com.hhxfight.recolorer.requestfactory;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.hhxfight.recolorer.config.ErrorCode;

/**
 * Created by HHX on 2017/3/23.
 */

public class DefaultErrorListener implements Response.ErrorListener {
    private Context contextHold;

    public DefaultErrorListener(Context context) {
        contextHold = context;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        error.printStackTrace();
        Log.d("RVA", "error:" + error);

        int errorCode = 0;
        if (error instanceof TimeoutError) {
            errorCode = -7;
        } else if (error instanceof NoConnectionError) {
            errorCode = -1;
        } else if (error instanceof AuthFailureError) {
            errorCode = -6;
        } else if (error instanceof ServerError) {
            errorCode = 0;
        } else if (error instanceof NetworkError) {
            errorCode = -1;
        } else if (error instanceof ParseError) {
            errorCode = -8;
        }
        Toast.makeText(contextHold, ErrorCode.errorCodeMap.get(errorCode), Toast.LENGTH_SHORT).show();
    }
}
