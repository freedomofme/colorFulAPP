package com.hhxfight.recolorer.requestfactory;

/**
 * Created by HHX on 15/8/20.
 */
public class RequestSingletonFactory {
    private volatile static RequestSingletonFactory requestFactory;

    protected static final String TYPE_UTF8_CHARSET = "charset=UTF-8";

    public static RequestSingletonFactory getInstance() {
        if (requestFactory == null) {
            synchronized (RequestSingletonFactory.class) {
                if (requestFactory == null)
                    requestFactory = new RequestSingletonFactory();
            }
        }
        return requestFactory;
    }

//    public StringRequest getGETStringRequest(Context context, final String url, Response.Listener responseListener) {
//        Log.i("RVA", "request add queue. link is :" + url);
//        return new StringRequest(Request.Method.GET, url, responseListener, new DefaultErrorListener(context)) {
//            @Override
//            protected Response<String> parseNetworkResponse(NetworkResponse response) {
//                addEncodeing2Request(response);
//                return super.parseNetworkResponse(response);
//            }
//
//            @Override
//            public int getDefaultTtl() {
//                return 24 * 3600 * 1000;
//            }
//
//            @Override
//            public int getDefaultSoftTtl() {
//                return 0 * 60 * 1000;
//            }
//
//            @Override
//            public boolean shouldLocalCacheControl() {
//                return true;
//            }
//        };
//    }

//    public StringRequest getPOSTStringRequest(Context context, String url, final Map<String, String> paramsMap, Response.Listener responseListener) {
//        return new StringRequest(Request.Method.POST, url, responseListener, new DefaultErrorListener(context)) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                return paramsMap;
//            }
//
//            @Override
//            protected Response<String> parseNetworkResponse(NetworkResponse response) {
//                String str = null;
//                addEncodeing2Request(response);
//                return super.parseNetworkResponse(response);
//            }
//        };
//    }






}
