package com.han.easemobdemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.han.easemobdemo.R;
import com.han.easemobdemo.activity.LoginActivity;

public class SettingFragment extends Fragment implements OnClickListener {

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Button logoutButton = (Button) getView().findViewById(R.id.btn_logout);
		logoutButton.setOnClickListener(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_setting, container, false);
	}

	@Override
	public void onClick(View v) {
		EMChatManager.getInstance().logout(new EMCallBack() {

			@Override
			public void onSuccess() {
				getActivity().finish();
				startActivity(new Intent(getActivity(), LoginActivity.class));
			}

			@Override
			public void onProgress(int arg0, String arg1) {

			}

			@Override
			public void onError(int arg0, String arg1) {

			}
		});
	}
}
