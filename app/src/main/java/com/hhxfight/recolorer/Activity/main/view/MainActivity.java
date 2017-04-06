package com.hhxfight.recolorer.Activity.main.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.hhxfight.recolorer.Activity.color.ColorTransformActivity;
import com.hhxfight.recolorer.Activity.gray.GrayscaleActivity;
import com.hhxfight.recolorer.Activity.manifold.ManifoldActivity;
import com.hhxfight.recolorer.Activity.mywork.MyWorkActivity;
import com.hhxfight.recolorer.R;
import com.hhxfight.recolorer.Activity.setting.SettingActivity;
import com.yanzhenjie.album.Album;

import java.util.List;

public class MainActivity extends AppCompatActivity implements IMainView{
    MyHandler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        // Example of a call to a native method
//        TextView tv = (TextView) findViewById(R.id.sample_text);
//        tv.setText(stringFromJNI());
//        mHandler = new MyHandler(this);
//
//        HashMap<String, String> map = new HashMap<>();
//        map.put("imagelink", "asdf,asf,af3,feq");
//
//        NetworkImageView test = (NetworkImageView) findViewById(R.id.iv_test);
//        test.setImageUrl("/storage/emulated/0/DCIM/Philm/2017_03_24_01_37_20_300_pic.png", MySingleton.getInstance(this.getApplicationContext()).getLocalImageLoader());



//        PostRequest postRequest = new PostRequest(Request.Method.POST, Url.test, new Response.Listener<String>(){
//            @Override
//            public void onResponse(String response) {
//                Log.i("Tag",response.toString());
//            }
//        }, new DefaultErrorListener(this));
//
//        postRequest.setPostParam(map);
//        MySingleton.getInstance(this.getApplicationContext()).getRequestQueue().add(postRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) { // 判断是否成功。
                // 拿到用户选择的图片路径List：
                List<String> pathList = Album.parseResult(data);
                Log.d("TAG", pathList.toString());
            } else if (resultCode == RESULT_CANCELED) { // 用户取消选择。
                // 根据需要提示用户取消了选择。
            }
        }
    }
    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    public void onGetPreefinedMainFold() {
        Toast.makeText(this, "流形已更新", Toast.LENGTH_SHORT).show();
    }

    class MyHandler extends Handler {
        Context context;
        public MyHandler(Context context) {
            this.context = context.getApplicationContext();
        }

        public void handleMessage(Message msg) {

        }

    }

    public void onGray(View view) {

        Intent i = new Intent(this, GrayscaleActivity.class);
        startActivity(i);
    }
    public void onSetting(View view) {
        Intent i = new Intent(this, SettingActivity.class);
        startActivity(i);
    }
    public void onMyWork(View view) {
        Intent i = new Intent(this, MyWorkActivity.class);
        startActivity(i);
    }

    public void onManiflod(View view) {
        Intent i = new Intent(this, ManifoldActivity.class);
        startActivity(i);
    }

    public void onTransform(View view) {
        Intent i = new Intent(this, ColorTransformActivity.class);
        startActivity(i);
    }
}
