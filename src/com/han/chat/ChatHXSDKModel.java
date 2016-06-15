package com.han.chat;

import java.util.List;
import java.util.Map;

import android.content.Context;

import com.han.applib.model.DefaultHXSDKModel;
import com.han.chat.db.ChatDBManager;
import com.han.chat.db.UserDao;
import com.han.chat.domain.RobotUser;
import com.han.chat.domain.User;

public class ChatHXSDKModel extends DefaultHXSDKModel {


	public ChatHXSDKModel(Context context) {
		super(context);
	}

	public boolean isDebugMode() {
		return true;
	}

	public boolean saveContactList(List<User> contactList) {
		UserDao dao = new UserDao(context);
		dao.saveContactList(contactList);
		return true;
	}

	public Map<String, User> getContactList() {
		UserDao dao = new UserDao(context);
		return dao.getContactList();
	}

	public void saveContact(User user){
		UserDao dao = new UserDao(context);
		dao.saveContact(user);
	}
	
	public boolean saveRobotList(List<RobotUser> robotList){
		UserDao dao = new UserDao(context);
		dao.saveRobotUser(robotList);
		return true;
	}
	
	public Map<String, RobotUser> getRobotList(){
		UserDao dao = new UserDao(context);
		return dao.getRobotUser();
	}
	
	public void closeDB(){
		ChatDBManager.getInstance().closeDB();
	}
	
	@Override
	public String getAppProcessName() {
		return context.getPackageName();
	}

}
