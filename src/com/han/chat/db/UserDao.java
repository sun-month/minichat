package com.han.chat.db;

import java.util.List;
import java.util.Map;

import android.content.Context;

import com.han.chat.domain.RobotUser;
import com.han.chat.domain.User;

public class UserDao {

	public static final String TABLE_NAME = "users";
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
		ChatDBManager.getInstance().setDisabledGroups(groups);
	}

	public List<String> getDisabledGroups() {
		return ChatDBManager.getInstance().getDisabledGroups();
	}

	public void setDisabledIds(List<String> ids) {
		ChatDBManager.getInstance().setDisabledIds(ids);
	}

	public List<String> getDisabledIds() {
		return ChatDBManager.getInstance().getDisabledIds();
	}

	/**
	 * 保存好友list
	 * 
	 * @param contactList
	 */
	public void saveContactList(List<User> contactList) {
		ChatDBManager.getInstance().saveContactList(contactList);
	}

	/**
	 * 获取好友list
	 * 
	 * @return
	 */
	public Map<String, User> getContactList() {
		return ChatDBManager.getInstance().getContactList();
	}

	/**
	 * 保存一个联系人
	 * 
	 * @param user
	 */
	public void saveContact(User user) {
		ChatDBManager.getInstance().SaveContact(user);
	}

	/**
	 * 删除一个联系人
	 * 
	 * @param username
	 */
	public void deleteContact(String username) {
		ChatDBManager.getInstance().deleteContact(username);
	}
	
	public void setDisableGroups(List<String> groups){
		ChatDBManager.getInstance().setDisabledGroups(groups);
	}
	
	public List<String> getDisableGroups(){
		return ChatDBManager.getInstance().getDisabledGroups();
	}

	public void setDisableIds(List<String> ids){
		ChatDBManager.getInstance().setDisabledIds(ids);
	}
	
	public List<String> getDisableIds(){
		return ChatDBManager.getInstance().getDisabledIds();
	}
	
	public void saveRobotUser(List<RobotUser> robotList){
		ChatDBManager.getInstance().saveRobotList(robotList);
	}
	
	public Map<String,RobotUser> getRobotUser(){
		return ChatDBManager.getInstance().getRobotList();
	}
	
}
