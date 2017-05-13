package com.hhxfight.recolorer.requestfactory;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import org.apache.commons.io.FileUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static com.hhxfight.recolorer.requestfactory.MyStringRequest.TYPE_UTF8_CHARSET;

public class MultipartRequest extends Request<String> {
    private final Response.Listener<String> mListener;
    private final Response.ErrorListener mErrorListener;
    private final String boundary = "apiclient-" + System.currentTimeMillis();
    private final String mMimeType = "multipart/form-data;boundary=" + boundary;
    private final String path;
    private final String twoHyphens = "--";
    private final String lineEnd = "\r\n";

    public MultipartRequest(String url, String path, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, url, errorListener);
        this.mListener = listener;
        this.mErrorListener = errorListener;
        this.path = path;
    }

    @Override
    public String getBodyContentType() {
        return mMimeType;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        byte[] mMultipartBody = null;
        try {
            byte bytes[] = FileUtils.readFileToByteArray(new File(path));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(bos);
            try {
                // the first file
                buildPart(dos, bytes, "anyname.png");
//                // the second file
//                buildPart(dos, fileData2, "ic_action_book.png");

                // send multipart form data necesssary after file data
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                // pass to multipart body
                mMultipartBody = bos.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mMultipartBody;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        addEncodeing2Request(response);
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }

        // So far the cacheEntry will be never used if the method of shouldCache return false
        // so quick return
        if (!shouldCache()) {
            return Response.success(parsed, null);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response, parsed, shouldLocalCacheControl(), getDefaultTtl(), getDefaultSoftTtl()));
    }

    @Override
    protected void deliverResponse(String response) {
        mListener.onResponse(response);
    }

    @Override
    public void deliverError(VolleyError error) {
        mErrorListener.onErrorResponse(error);
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

    private void buildPart(DataOutputStream dataOutputStream, byte[] fileData, String fileName) throws IOException {
        dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
        dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\"; filename=\""
                + fileName + "\"" + lineEnd);
        dataOutputStream.writeBytes(lineEnd);

        ByteArrayInputStream fileInputStream = new ByteArrayInputStream(fileData);
        int bytesAvailable = fileInputStream.available();

        int maxBufferSize = 1024 * 1024;
        int bufferSize = Math.min(bytesAvailable, maxBufferSize);
        byte[] buffer = new byte[bufferSize];

        // read file and write it into form...
        int bytesRead = fileInputStream.read(buffer, 0, bufferSize);

        while (bytesRead > 0) {
            dataOutputStream.write(buffer, 0, bufferSize);
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
        }

        dataOutputStream.writeBytes(lineEnd);
    }

}