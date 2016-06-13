package com.han.chat.db;

import java.util.List;
import java.util.Map;

import com.han.chat.domain.User;

import android.content.Context;

public class UserDao {

	public static final String TABLE_NAME = "uers";
	public static final String COLUMN_NAME_ID = "username";
	public static final String COLUMN_NAME_NICK = "nick";
	public static final String COLUMN_NAME_AVATAR = "avatar";
	
	public static final String PREF_TABLE_NAME = "pref";
	public static final String COLUMN_NAME_DISABLED_GROUPS = "disabled_groups";
	public static final String COLUMN_NAME_DISABLED_IDS = "disabled_ids";

	public static final String ROBOT_TABLE_NAME = "robots";
	public static final String ROBOT_COLUMN_NAME_ID = "username";
	public static final String ROBOT_COLUMN_NAME_NICK = "nick";
	public static final String ROBOT_COLUMN_NAME_AVATAR = "avatar";
	
	public UserDao(Context context) {
		ChatDBManager.getInstance().onInit(context);
	}

	public void setDisabledGroups(List<String> groups) {

	}

	public Object getDisabledGroups() {
		return null;
	}

	public void setDisabledIds(List<String> ids) {

	}

	public Object getDisabledIds() {
		return null;
	}

	public void saveContactList(List<User> contactList) {

	}

	public Map<String, User> getContactList() {
		return null;
	}

	public void saveContact(User user) {
		
	}

	public void deleteContact(String username) {
		
	}

}
