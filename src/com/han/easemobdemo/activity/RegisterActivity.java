package com.han.easemobdemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.EMError;
import com.easemob.chat.EMChatManager;
import com.easemob.exceptions.EaseMobException;
import com.han.easemobdemo.R;

public class RegisterActivity extends Activity {
	private TextView userNameEditText;
	private TextView passwordEditText;
	private TextView confirmPwdEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		userNameEditText = (TextView) findViewById(R.id.username);
		passwordEditText = (TextView) findViewById(R.id.password);
		confirmPwdEditText = (TextView) findViewById(R.id.confirm_password);

	}

	public void register(View view) {
		final String username = userNameEditText.getText().toString().trim();
		final String pwd = passwordEditText.getText().toString().trim();
		String confirmPwd = confirmPwdEditText.getText().toString().trim();

		// 验证输入可行性
		if (TextUtils.isEmpty(username)) {
			Toast.makeText(
					this,
					getResources()
							.getString(R.string.user_name_cannot_be_empty),
					Toast.LENGTH_SHORT).show();
			userNameEditText.requestFocus();
			return;
		}
		if (TextUtils.isEmpty(pwd)) {
			Toast.makeText(
					this,
					getResources().getString(R.string.password_cannot_be_empty),
					Toast.LENGTH_SHORT).show();
			passwordEditText.requestFocus();
			return;
		}
		if (TextUtils.isEmpty(confirmPwd)) {
			Toast.makeText(
					this,
					getResources().getString(
							R.string.confirm_password_cannot_be_empty),
					Toast.LENGTH_SHORT).show();
			confirmPwdEditText.requestFocus();
			return;
		}
		if (!pwd.equals(confirmPwd)) {
			Toast.makeText(this,
					getResources().getString(R.string.two_input_password),
					Toast.LENGTH_SHORT).show();
			return;
		}
		Looper.prepare();
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					EMChatManager.getInstance().createAccountOnServer(username,
							pwd);
					Intent intent = new Intent(RegisterActivity.this,
							LoginActivity.class);
					getApplicationContext().startActivity(intent);
				} catch (final EaseMobException e) {
					runOnUiThread(new Runnable() {
						public void run() {
							int errorCode = e.getErrorCode();
							if (errorCode == EMError.NONETWORK_ERROR) {
								Toast.makeText(getApplicationContext(),
										"网络异常，请检查网络！", Toast.LENGTH_SHORT)
										.show();
							} else if (errorCode == EMError.USER_ALREADY_EXISTS) {
								Toast.makeText(getApplicationContext(),
										"用户已存在！", Toast.LENGTH_SHORT).show();
							} else if (errorCode == EMError.UNAUTHORIZED) {
								Toast.makeText(getApplicationContext(),
										"注册失败，无权限！", Toast.LENGTH_SHORT).show();
							} else {
								Toast.makeText(getApplicationContext(),
										"注册失败: " + e.getMessage(),
										Toast.LENGTH_SHORT).show();
							}
						}
					});
				}
			}
		}).start();
	}
}
