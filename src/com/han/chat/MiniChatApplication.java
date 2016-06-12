package com.han.chat;

import com.easemob.EMCallBack;

import android.app.Application;
import android.content.Context;

public class MiniChatApplication extends Application {

	public static Context applicationContext;

	private static MiniChatApplication instance;

	public static String currentUserNick = "";
	public static ChatHXSDKHelper hxSDKHelper = new ChatHXSDKHelper();

	@Override
	public void onCreate() {
		applicationContext = this;
		instance = this;
		hxSDKHelper.onInit(applicationContext);
	}

	public void setUsername(String username) {
		hxSDKHelper.setHXId(username);
	}

	public String getUsername() {
		return hxSDKHelper.getHXId();
	}

	public void setPassword(String password) {
		hxSDKHelper.setPassword(password);
	}

	public String getPassword() {
		return hxSDKHelper.getPassword();
	}

	public static MiniChatApplication getInstance() {
		return instance;
	}

	//退出登录，并清理数据
	public void logout(final boolean isGCM, final EMCallBack emCallBack) {
		hxSDKHelper.logout(isGCM, emCallBack);
	}

}
