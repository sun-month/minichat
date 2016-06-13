package com.han.chat.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.han.chat.domain.User;

public class ChatDBManager {

	private static ChatDBManager dbMgr = new ChatDBManager();
	private DbOpenHelper dbHelper;

	public void onInit(Context context) {
		dbHelper = DbOpenHelper.getInstance(context);
	}

	public static synchronized ChatDBManager getInstance() {
		return dbMgr;
	}

	/**
	 * 保存好友list
	 * 
	 * @param contactList
	 */
	public synchronized void saveContactList(List<User> contactList) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		//保存好友数据库插入操作
	}

	/**
	 * 获取好友map
	 * 
	 * @return
	 */
	public synchronized Map<String, User> getContactList() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Map<String, User> users = new HashMap<String, User>();
		//获取好友map数据库查询操作
		
		return users;
	}
	
	/**
	 * 根据用户名删除联系人
	 * 
	 * @param username
	 */
	public synchronized void deleteContact(String username){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			db.delete(UserDao.TABLE_NAME, UserDao.COLUMN_NAME_ID + " = ?", new String[]{username});
		}
	}
	
	/**
	 * 保存一个联系人
	 * 
	 * @param user
	 */
	public synchronized void SaceContact(User user){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		//保存一个联系人数据库插入操作
	}
	
	

}
