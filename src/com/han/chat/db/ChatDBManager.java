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
	 * �������list
	 * 
	 * @param contactList
	 */
	public synchronized void saveContactList(List<User> contactList) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		//����������ݿ�������
	}

	/**
	 * ��ȡ����map
	 * 
	 * @return
	 */
	public synchronized Map<String, User> getContactList() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Map<String, User> users = new HashMap<String, User>();
		//��ȡ����map���ݿ��ѯ����
		
		return users;
	}
	
	/**
	 * �����û���ɾ����ϵ��
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
	 * ����һ����ϵ��
	 * 
	 * @param user
	 */
	public synchronized void SaceContact(User user){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		//����һ����ϵ�����ݿ�������
	}
	
	

}
