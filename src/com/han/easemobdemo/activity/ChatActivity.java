package com.han.easemobdemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.easemob.easeui.EaseConstant;
import com.easemob.easeui.ui.EaseChatFragment;
import com.han.easemobdemo.R;

public class ChatActivity extends FragmentActivity {

	public static ChatActivity chatInstance;

	private EaseChatFragment chatFragment;

	private String toChatUsername;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_chat);
		chatInstance = this;
		toChatUsername = getIntent().getExtras().getString(
				EaseConstant.EXTRA_USER_ID);
		chatFragment = new EaseChatFragment();
		chatFragment.setArguments(getIntent().getExtras());
		getSupportFragmentManager().beginTransaction()
				.add(R.id.container, chatFragment).commit();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		String username = intent.getStringExtra("userId");
		if (toChatUsername.equals(username)) {
			super.onNewIntent(intent);
		} else {
			finish();
			startActivity(intent);
		}
	}

	@Override
	public void onBackPressed() {
		chatFragment.onBackPressed();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		chatInstance = null;
	}

	public static void actionStart(Activity activity, Class<?> cls,
			String userId) {
		Intent intent = new Intent(activity, cls);
		intent.putExtra(EaseConstant.EXTRA_USER_ID, userId);
		activity.startActivity(intent);
	}
}
