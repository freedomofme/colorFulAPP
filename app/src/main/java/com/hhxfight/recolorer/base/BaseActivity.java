package com.hhxfight.recolorer.base;

import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

public class BaseActivity extends Activity
{
	public Context mContext;

	@Override
	public void setContentView(int layoutResID)
	{
		super.setContentView(layoutResID);
		mContext = this;

	}

	public void goBack(View v)
	{
		finish();
		enterAndExitAnimation();
	}

	public void enterAndExitAnimation()
	{
		overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);
	}

	@Override
	protected void onResume()
	{
		super.onResume();
//		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause()
	{
		super.onPause();
//		MobclickAgent.onPause(this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (KeyEvent.KEYCODE_BACK == keyCode)
		{
			finish();
			enterAndExitAnimation();
		}
		return super.onKeyDown(keyCode, event);
	}

	public void showToast(String msg)
	{
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}
}
