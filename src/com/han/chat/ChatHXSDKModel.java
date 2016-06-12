package com.han.chat;

import java.util.List;
import java.util.Map;

import android.content.Context;

import com.han.applib.model.DefaultHXSDKModel;
import com.han.chat.db.UserDao;
import com.han.chat.domain.User;

public class ChatHXSDKModel extends DefaultHXSDKModel {

	private UserDao dao;

	public ChatHXSDKModel(Context context) {
		super(context);
		dao = new UserDao(context);
	}

	public boolean isDebugMode() {
		return true;
	}

	public boolean saveContactList(List<User> contactList) {
		dao.saveContactList(contactList);
		return true;
	}

	public Map<String, User> getContactList() {
		return dao.getContactList();
	}

	@Override
	public String getAppProcessName() {
		return context.getPackageName();
	}

}
