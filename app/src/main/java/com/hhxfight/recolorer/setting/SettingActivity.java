
package com.hhxfight.recolorer.setting;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hhxfight.recolorer.R;
import com.hhxfight.recolorer.base.BaseActivity;

import java.io.File;

import fynn.app.PromptDialog;


public class SettingActivity extends BaseActivity {
	
	LinearLayout setting_info;
	LinearLayout clear_cache;
	TextView tv_clear_cache;
	static File fileclear = new File(Environment.getExternalStorageDirectory()
			.getAbsolutePath() + "/ColorFul/cache/");
	static File fileclear2 = new File(Environment.getExternalStorageDirectory()
			.getAbsolutePath() + "/ColorFul/cache/pic/");
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
        setting_info = (LinearLayout) findViewById(R.id.ll_setting_info);
        clear_cache = (LinearLayout) findViewById(R.id.ll_clear_cache);
        tv_clear_cache = (TextView) findViewById(R.id.btn_login_out);
	}
	
	private void initView() 
	{
		tv_clear_cache.setText("清理缓存" + " (" + getCacheSize(mContext) + ") ");
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initView();
	}

	public void toBack(View view)
	{
		this.finish();
	}

	
	
	//消息提醒对话框
	public void notifyCLearCache(View view)
	{
		new PromptDialog.Builder(this)
		.setMessage("是否打开提醒")
		.setViewStyle(PromptDialog.VIEW_STYLE_VERTICAL)
		.setMessageGravity(Gravity.CENTER)
		.setButton1("打开", new PromptDialog.OnClickListener() {
		            @Override
		            public void onClick(Dialog dialog, int which) {
		                dialog.dismiss();
		            }
		        })
		   .setButton2("取消", new PromptDialog.OnClickListener() {
		            @Override
		            public void onClick(Dialog dialog, int which) {
		                dialog.dismiss();
		            }
		        })     
		           .setButton3("关闭", new PromptDialog.OnClickListener() {
		            @Override
		            public void onClick(Dialog dialog, int which) {
		                dialog.dismiss();
		            }
		        })    
		.show();
	}
	
	//清空缓存对话框 
	public void cLearCache(View view)
	{
		new PromptDialog.Builder(this)
		.setMessage("清空缓存?")
		.setMessageGravity(Gravity.CENTER)
		.setButton1("清空", new PromptDialog.OnClickListener() {
		            @Override
		            public void onClick(Dialog dialog, int which) {
		            	deleteAllCache();
		            	tv_clear_cache.setText("清理缓存" + " (" + getCacheSize(mContext) + ") ");
		                dialog.dismiss();
		            }
		        })
		   .setButton2("取消", new PromptDialog.OnClickListener() {
		            @Override
		            public void onClick(Dialog dialog, int which) {
		                dialog.dismiss();
		            }
		        })     

		.show();
	}
	
	public void deleteAllCache()
	{
		cleanApplicationData(this, fileclear.getAbsolutePath(), fileclear2.getAbsolutePath());
	}

	
	/** * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache) * * @param context */
	public static void cleanInternalCache(Context context)
	{
		deleteFilesByDirectory(context.getCacheDir());
	}

	/**
	 * * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs) * * @param
	 * context
	 */
	public static void cleanSharedPreference(Context context)
	{
		deleteFilesByDirectory(new File("/data/data/"
				+ context.getPackageName() + "/shared_prefs"));
	}

	/** * 清除/data/data/com.xxx.xxx/files下的内容 * * @param context */
	public static void cleanFiles(Context context)
	{
		deleteFilesByDirectory(context.getFilesDir());
	}

	/**
	 * * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache) * * @param
	 * context
	 */
	public static void cleanExternalCache(Context context)
	{
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED))
		{
			deleteFilesByDirectory(context.getExternalCacheDir());
		}
	}

	/** * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除 * * @param filePath */
	public static void cleanCustomCache(String filePath)
	{
		deleteFilesByDirectory(new File(filePath));
	}

	/** * 清除本应用所有的数据 * * @param context * @param filepath */
	public static void cleanApplicationData(Context context, String... filepath)
	{
		cleanInternalCache(context);
		cleanExternalCache(context);
//	    password saved here
//		cleanSharedPreference(context);
		cleanFiles(context);
		for (String filePath : filepath)
		{
			cleanCustomCache(filePath);
		}
	}

	/** * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * * @param directory */
	private static void deleteFilesByDirectory(File directory)
	{
		if (directory != null && directory.exists() && directory.isDirectory())
		{
			for (File item : directory.listFiles())
			{
				item.delete();
			}
		}
	}
	/**
	 * 根据文件夹
	 * 
	 * @param directory
	 * @return
	 */
	private static long accountFilesSizeByDirectory(File directory)
	{
		long size = 0;
		if (directory != null && directory.exists() && directory.isDirectory())
		{
			for (File item : directory.listFiles())
			{
				size += item.length();
			}
		}
		return size;
	}

	/**
	 * 获取Cache大小
	 * 
	 * @return
	 */
	public static String getCacheSize(Context context)
	{
		long size = accountFilesSizeByDirectory(context.getCacheDir()) + accountFilesSizeByDirectory(fileclear) + 
				accountFilesSizeByDirectory(fileclear2);
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED))
		{
			size += accountFilesSizeByDirectory(context.getExternalCacheDir());
		}
		return String.format("%.2f", size / (1024.0 * 1024.0)) + "M";
	}
}