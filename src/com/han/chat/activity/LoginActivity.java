package com.han.chat.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroupManager;
import com.han.applib.controller.HXSDKHelper;
import com.han.chat.ChatHXSDKHelper;
import com.han.chat.Constant;
import com.han.chat.MiniChatApplication;
import com.han.chat.R;
import com.han.chat.db.UserDao;
import com.han.chat.domain.User;
import com.han.chat.utils.CommonUtils;

public class LoginActivity extends Activity {
	private EditText usernameEditText;
	private EditText passwordEditText;
	private boolean progressShow = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		usernameEditText = (EditText) findViewById(R.id.username);
		passwordEditText = (EditText) findViewById(R.id.password);

		// 改变用户名清空密码
		usernameEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				passwordEditText.setText(null);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
	}

	public void login(View view) {
		if (!CommonUtils.isNetWorkConnected(this)) {
			Toast.makeText(this, R.string.network_isnot_available,
					Toast.LENGTH_SHORT).show();
			return;
		}

		final String currentUsername = usernameEditText.getText().toString()
				.trim();
		final String currentPassword = passwordEditText.getText().toString()
				.trim();

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

		// 调用环信SDK的登录方法，登录环信服务器
		EMChatManager.getInstance().login(currentUsername, currentPassword,
				new EMCallBack() {
					@Override
					public void onSuccess() {

						if (!progressShow) {
							return;
						}

						// 登陆成功，保存用户名密码
						MiniChatApplication.getInstance().setUsername(
								currentUsername);
						MiniChatApplication.getInstance().setPassword(
								currentPassword);

						try {
							// 登录进入主界面前，加载所有本地群和会话
							EMGroupManager.getInstance().loadAllGroups();
							EMChatManager.getInstance().loadAllConversations();
							// 进入主页面前，处理好友和群组
							initializeContacts();
						} catch (Exception e) {
							Log.d("error", e.getMessage());
							// 获取好有和群组失败，不让进入主页面
							runOnUiThread(new Runnable() {

								@Override
								public void run() {
									pd.dismiss();
									MiniChatApplication.getInstance().logout(true, null);
									Toast.makeText(getApplicationContext(), R.string.login_failure_failed,
											Toast.LENGTH_LONG).show();
								}
							});
							return;
						}

						// Log.d(MainActivity.MAIN, "登录聊天服务器成功！");
						boolean updatenick = EMChatManager.getInstance()
								.updateCurrentUserNick(
										MiniChatApplication.currentUserNick
												.trim());

						if (!updatenick) {
							Log.e("LogActivity",
									"update current user nick fail");
						}

						if (!LoginActivity.this.isFinishing() && pd.isShowing()) {
							pd.dismiss();
						}
						Intent intent = new Intent(LoginActivity.this,
								MainActivity.class);
						startActivity(intent);
						finish();

					}

					@Override
					public void onProgress(int progress, String status) {

					}

					@Override
					public void onError(final int code, final String message) {
						Log.d(MainActivity.MAIN, "登录聊天服务器失败！");
						if (!progressShow) {
							return;
						}
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								pd.dismiss();
								Toast.makeText(
										getApplicationContext(),
										getString(R.string.Login_failed)
												+ message, Toast.LENGTH_SHORT)
										.show();
							}
						});
					}
				});

	}

	private void initializeContacts() {
		Map<String, User> userlist = new HashMap<String, User>();

		// 添加user"申请与通知"
		User newFriends = new User();
		newFriends.setUsername(Constant.NEW_FRIENDS_USERNAME);
		String strChat = getResources().getString(
				R.string.Application_and_notify);
		newFriends.setNick(strChat);
		userlist.put(Constant.NEW_FRIENDS_USERNAME, newFriends);

		// 添加"群聊"
		User groupUser = new User();
		groupUser.setUsername(Constant.GROUP_USERNAME);
		String strGroup = getResources().getString(R.string.group_chat);
		groupUser.setNick(strGroup);
		groupUser.setHeader("");
		userlist.put(Constant.GROUP_USERNAME, groupUser);

		// 添加“Robot”,do something
		User robotUser = new User();
		robotUser.setUsername(Constant.CHAT_ROBOT);
		String strRobot = getResources().getString(R.string.robot_chat);
		robotUser.setNick(strRobot );

		// 存入内存
		((ChatHXSDKHelper) HXSDKHelper.getInstance()).setContactList(userlist);

		// 存入db,调用sqlite
		UserDao userDao = new UserDao(LoginActivity.this);
		List<User> users = new ArrayList<User>(userlist.values());
		userDao.saveContactList(users);
	}

	public void register(View view) {
		Intent intent = new Intent(this, RegisterActivity.class);
		startActivity(intent);
	}

}
