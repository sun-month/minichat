package com.han.easemobdemo.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroupManager;
import com.han.easemobdemo.R;
import com.han.easemobdemo.utils.CommonUtils;

public class LoginActivity extends Activity {
	private EditText usernameEditText;
	private EditText passwordEditText;
	private boolean progressShow = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		usernameEditText = (EditText) findViewById(R.id.username);
		passwordEditText = (EditText) findViewById(R.id.password);
	}

	public void login(View view) {
		if (!CommonUtils.isNetWorkConnected(this)) {
			Toast.makeText(this, R.string.network_isnot_available,
					Toast.LENGTH_SHORT).show();
			return;
		}

		String currentUsername = usernameEditText.getText().toString().trim();
		String currentPassword = passwordEditText.getText().toString().trim();

		if (TextUtils.isEmpty(currentUsername)) {
			Toast.makeText(this, R.string.user_name_cannot_be_empty,
					Toast.LENGTH_SHORT).show();
			return;
		}
		if (TextUtils.isEmpty(currentPassword)) {
			Toast.makeText(this, R.string.password_cannot_be_empty,
					Toast.LENGTH_SHORT).show();
			return;
		}

		final ProgressDialog pd = new ProgressDialog(this);
		pd.setCanceledOnTouchOutside(false);
		pd.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				progressShow = false;
			}
		});
		pd.setMessage(getString(R.string.is_landing));
		pd.show();

		EMChatManager.getInstance().login(currentUsername, currentPassword,
				new EMCallBack() {
					@Override
					public void onSuccess() {

						if (!progressShow) {
							return;
						}

						runOnUiThread(new Runnable() {
							public void run() {
								EMGroupManager.getInstance().loadAllGroups();
								EMChatManager.getInstance()
										.loadAllConversations();
								Log.d("main", "登录聊天服务器成功！");
								if (!LoginActivity.this.isFinishing()
										&& pd.isShowing()) {
									pd.dismiss();
								}
								Intent intent = new Intent(LoginActivity.this,
										MainActivity.class);
								startActivity(intent);
								finish();
							}
						});
					}

					@Override
					public void onProgress(int progress, String status) {

					}

					@Override
					public void onError(int code, String message) {
						Log.d("main", "登录聊天服务器失败！");
					}
				});

	}

	public void register(View view) {
		Intent intent = new Intent(this, RegisterActivity.class);
		startActivity(intent);
	}

}
