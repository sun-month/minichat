package com.han.easemobdemo;

import com.easemob.chat.EMChat;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
	
	private static Context applicationContext;

	@Override
	public void onCreate() {
		applicationContext = this;
		init(applicationContext);
	}

	private void init(Context applicationContext) {
		EMChat.getInstance().init(applicationContext);
		EMChat.getInstance().setDebugMode(true);
	}
}
