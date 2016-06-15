package com.han.chat.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.easemob.util.HanziToPinyin;
import com.han.chat.Constant;
import com.han.chat.domain.InviteMessage;
import com.han.chat.domain.InviteMessage.InviteMessageStatus;
import com.han.chat.domain.RobotUser;
import com.han.chat.domain.User;

@SuppressLint("DefaultLocale")
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
		// 保存好友数据库插入操作
		if (!db.isOpen())
			return;
		// 先清空列表
		db.delete(UserDao.TABLE_NAME, null, null);

		for (User user : contactList) {
			ContentValues values = new ContentValues();
			values.put(UserDao.COLUMN_NAME_ID, user.getUsername());
			if (user.getNick() != null)
				values.put(UserDao.COLUMN_NAME_NICK, user.getNick());
			if (user.getAvatar() != null)
				values.put(UserDao.COLUMN_NAME_AVATAR, user.getAvatar());

			db.replace(UserDao.TABLE_NAME, null, values);
		}
	}

	/**
	 * 获取好友map
	 * 
	 * @return
	 */
	@SuppressLint("DefaultLocale")
	public synchronized Map<String, User> getContactList() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Map<String, User> users = new HashMap<String, User>();
		// 获取好友map数据库查询操作
		if (!db.isOpen())
			return null;
		Cursor cursor = db.rawQuery("select " + UserDao.COLUMN_NAME_ID + ","
				+ UserDao.COLUMN_NAME_NICK + "," + UserDao.COLUMN_NAME_AVATAR
				+ " from" + UserDao.TABLE_NAME, null);

		while (cursor.moveToNext()) {
			String username = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_ID));
			String nick = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_NICK));
			String avatar = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_AVATAR));

			User user = new User();
			user.setUsername(username);
			user.setNick(nick);
			user.setAvatar(avatar);
			String headerName = null;
			if (!TextUtils.isEmpty(nick)) {
				headerName = nick;
			} else {
				headerName = username;
			}

			if (Constant.NEW_FRIENDS_USERNAME.equals(username) || Constant.GROUP_USERNAME.equals(username)
					|| Constant.CHAT_ROOM.equals(username) || Constant.CHAT_ROBOT.equals(username)) {
				user.setHeader("");
			} else if (Character.isDigit(headerName.charAt(0))){
				user.setHeader("#");
			} else {
				user.setHeader(HanziToPinyin.getInstance().get(headerName.substring(0, 1))
						.get(0).target.substring(0, 1).toUpperCase());
				
				char header = user.getHeader().toLowerCase(Locale.ENGLISH).charAt(0);
				if(header < 'a' || header >'z'){
					user.setHeader("#");
				}
			}
			users.put(username, user);
		}
		cursor.close();

		return users;
	}

	/**
	 * 根据用户名删除联系人
	 * 
	 * @param username
	 */
	public synchronized void deleteContact(String username) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			db.delete(UserDao.TABLE_NAME, UserDao.COLUMN_NAME_ID + " = ?",
					new String[] { username });
		}
	}

	/**
	 * 保存一个联系人
	 * 
	 * @param user
	 */
	public synchronized void SaveContact(User user) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		// 保存一个联系人数据库插入操作
		ContentValues values = new ContentValues();
		values.put(UserDao.COLUMN_NAME_ID, user.getUsername());
		if(user.getNick() != null)
			values.put(UserDao.COLUMN_NAME_NICK, user.getNick());
		if(user.getAvatar() != null)
			values.put(UserDao.COLUMN_NAME_AVATAR, user.getAvatar());
		if (db.isOpen()) {
			db.replace(UserDao.TABLE_NAME, null, values);
		}
	}

	public void setDisabledGroups(List<String> groups) {
		setList(UserDao.COLUMN_NAME_DISABLED_GROUPS, groups);
	}

	public List<String> getDisabledGroups() {
		return getList(UserDao.COLUMN_NAME_DISABLED_GROUPS);
	}

	public void setDisabledIds(List<String> ids) {
		setList(UserDao.COLUMN_NAME_DISABLED_IDS, ids);
	}

	public List<String> getDisabledIds() {
		return getList(UserDao.COLUMN_NAME_DISABLED_IDS);
	}

	private synchronized void setList(String column, List<String> strList) {
		StringBuilder strBuilder = new StringBuilder();
		for (String hxid : strList) {
			strBuilder.append(hxid).append("$");
		}
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			ContentValues values = new ContentValues();
			values.put(column, strBuilder.toString());

			db.update(UserDao.PREF_TABLE_NAME, values, null, null);
		}
	}

	private synchronized List<String> getList(String column) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select " + column + " from "
				+ UserDao.PREF_TABLE_NAME, null);
		if (!cursor.moveToFirst()) {
			cursor.close();
			return null;
		}

		String value = cursor.getString(0);
		if (TextUtils.isEmpty(value)) {
			return null;
		}

		cursor.close();

		String[] array = value.split("$");
		if (array == null || array.length == 0) {
			return null;
		}

		return Arrays.asList(array);
	}
	
	/**
	 * 保存message
	 * @param message
	 * @return 返回这条message在db中的id(是rowid不是msgId)
	 */
	public synchronized Integer saveMessage(InviteMessage message){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Integer id = -1;
		
		if (!db.isOpen()) 
			return id;
		
		ContentValues values = new ContentValues();
		values.put(InviteMessageDao.COLUMN_NAME_FROM, message.getFrom());
		values.put(InviteMessageDao.COLUMN_NAME_GROUP_ID, message.getGroupId());
		values.put(InviteMessageDao.COLUMN_NAME_GROUP_NAME, message.getGroupName());
		values.put(InviteMessageDao.COLUMN_NAME_REASON, message.getReason());
		values.put(InviteMessageDao.COLUMN_NAME_STATUS, message.getStatus().ordinal());
		db.insert(InviteMessageDao.TABLE_NAME, null, values);
		
		Cursor cursor = db.rawQuery("select last_insert_rowid() from " + InviteMessageDao.TABLE_NAME, null);
		if (cursor.moveToFirst()) {
			id = cursor.getInt(0);
		}
		
		return id;
	}
	
	/**
	 * 更新message
	 * @param msgId
	 * @param values
	 */
	public synchronized void updateMessage(int msgId, ContentValues values){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			db.update(InviteMessageDao.TABLE_NAME, values, InviteMessageDao.COLUMN_NAME_ID + " = ?", new String[]{String.valueOf(msgId)});
		}
	}
	
	/**
	 * @return db中所有邀请信息
	 */
	public synchronized List<InviteMessage> getMessageList(){
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		List<InviteMessage> msgs = new ArrayList<InviteMessage>();
		
		if (!db.isOpen()) 
			return null;
		
		Cursor cursor = db.rawQuery("select " + InviteMessageDao.COLUMN_NAME_ID + ","
					+ InviteMessageDao.COLUMN_NAME_FROM + ","
					+ InviteMessageDao.COLUMN_NAME_GROUP_ID + ","
					+ InviteMessageDao.COLUMN_NAME_GROUP_NAME + ","
					+ InviteMessageDao.COLUMN_NAME_REASON + ","
					+ InviteMessageDao.COLUMN_NAME_STATUS + ","
					+ InviteMessageDao.COLUMN_NAME_TIME + " from "
					+ InviteMessageDao.TABLE_NAME + " desc", null);
		while(cursor.moveToNext()){
			InviteMessage msg = new InviteMessage();
			int id = cursor.getInt(cursor.getColumnIndex(InviteMessageDao.COLUMN_NAME_ID));
			String from = cursor.getString(cursor.getColumnIndex(InviteMessageDao.COLUMN_NAME_FROM));
			String groupId = cursor.getString(cursor.getColumnIndex(InviteMessageDao.COLUMN_NAME_GROUP_ID));
			String groupName = cursor.getString(cursor.getColumnIndex(InviteMessageDao.COLUMN_NAME_GROUP_NAME));
			String reason = cursor.getString(cursor.getColumnIndex(InviteMessageDao.COLUMN_NAME_REASON));
			long time = cursor.getLong(cursor.getColumnIndex(InviteMessageDao.COLUMN_NAME_TIME));
			int status = cursor.getInt(cursor.getColumnIndex(InviteMessageDao.COLUMN_NAME_STATUS));
			
			msg.setFrom(from);
			msg.setGroupId(groupId);
			msg.setGroupName(groupName);
			msg.setId(id);
			msg.setReason(reason);
			msg.setTime(time);
			if (status == InviteMessageStatus.AGREED.ordinal()) {
				msg.setStatus(InviteMessageStatus.AGREED);
			}else if (status == InviteMessageStatus.BEAGREED.ordinal()){
				msg.setStatus(InviteMessageStatus.BEAGREED);
			}else if(status == InviteMessageStatus.BEAPPLYED.ordinal()){
				msg.setStatus(InviteMessageStatus.BEAPPLYED);
			}else if (status == InviteMessageStatus.BEREFUSED.ordinal()) {
				msg.setStatus(InviteMessageStatus.BEREFUSED);
			}else if(status == InviteMessageStatus.BRINVITEED.ordinal()){
				msg.setStatus(InviteMessageStatus.BRINVITEED);
			}else if(status == InviteMessageStatus.REFUSED.ordinal()){
				msg.setStatus(InviteMessageStatus.REFUSED);
			}
			msgs.add(msg);
		}
		cursor.close();
		
		return msgs;
	}
	
	public synchronized void deleteMessage(String from){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			db.delete(InviteMessageDao.TABLE_NAME, InviteMessageDao.COLUMN_NAME_FROM + " = ?", new String[]{from});
		}
	}

	/**
	 * save RobotList
	 * @param robotList
	 */
	public synchronized void saveRobotList(List<RobotUser> robotList){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (!db.isOpen()) 
			return ;
		db.delete(UserDao.ROBOT_TABLE_NAME, null, null);
		for (RobotUser robotUser : robotList) {
			ContentValues values = new ContentValues();
			values.put(UserDao.ROBOT_COLUMN_NAME_ID, robotUser.getUsername());
			if(robotUser.getNick() != null)
				values.put(UserDao.ROBOT_COLUMN_NAME_NICK, robotUser.getNick());
			if(robotUser.getAvatar() != null)
				values.put(UserDao.ROBOT_COLUMN_NAME_AVATAR, robotUser.getAvatar());
			db.replace(UserDao.ROBOT_TABLE_NAME, null, values);
		}
	}
	
	/**
	 * load robot list
	 * @return Map
	 */
	public synchronized Map<String,RobotUser> getRobotList(){
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		if (!db.isOpen()) 
			return null;
		Cursor cursor = db.rawQuery("select "+UserDao.ROBOT_COLUMN_NAME_ID + ","
				+ UserDao.ROBOT_COLUMN_NAME_NICK + ","
				+ UserDao.ROBOT_COLUMN_NAME_AVATAR + " from "
				+ UserDao.ROBOT_TABLE_NAME, null);
		if(cursor.getCount() < 0)
			return null;
		Map<String, RobotUser> users = new HashMap<String, RobotUser>();
		while(cursor.moveToNext()){
			RobotUser user = new RobotUser();
			String username = cursor.getString(cursor.getColumnIndex(UserDao.ROBOT_COLUMN_NAME_ID));
			String nick = cursor.getString(cursor.getColumnIndex(UserDao.ROBOT_COLUMN_NAME_NICK));
			String avatar = cursor.getString(cursor.getColumnIndex(UserDao.ROBOT_COLUMN_NAME_AVATAR));
			user.setUsername(username);
			user.setNick(nick);
			user.setAvatar(avatar);
			
			String headerName = null;
			if (!TextUtils.isEmpty(nick)) {
				headerName = nick;
			}else{
				headerName = username;
			}
			
			if(Character.isDigit(headerName.charAt(0))){
				user.setHeader("#");
			}else {
				user.setHeader(HanziToPinyin.getInstance().get(headerName.substring(0, 1)).get(0).target.substring(0, 1).toUpperCase());
				
				char header = user.getHeader().toLowerCase().charAt(0);
				if (header <'a' || header > 'z') {
					user.setHeader("#");
				}
			}
			users.put(username, user);
		}
		cursor.close();
		
		return users;
	}
	
	public synchronized void closeDB(){
		if (dbHelper!=null) {
			dbHelper.closeDB();
		}
	}
	
	
}
