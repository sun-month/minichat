package com.han.chat.db;

import com.han.applib.controller.HXSDKHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbOpenHelper extends SQLiteOpenHelper{

	private static final int DATABASE_VERSION = 1;
	private static DbOpenHelper instance;
	
	private static final String USERNAME_TALBE_CREATE = "CREATE TABLE "
			+ UserDao.TABLE_NAME + " ("
			+ UserDao.COLUMN_NAME_NICK + " TEXT, "
			+ UserDao.COLUMN_NAME_AVATAR + " TEXT, "
			+ UserDao.COLUMN_NAME_ID +"TEXT PRIMARY KEY";
	
	private static final String INIVTE_MESSAGE_TABLE_CREATE = "CREATE TABLE "
			+ InviteMessageDao.TABLE_NAME + " ("
			+ InviteMessageDao.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ InviteMessageDao.COLUMN_NAME_FROM + " TEXT, "
			+ InviteMessageDao.COLUMN_NAME_GROUP_ID + " TEXT, "
			+ InviteMessageDao.COLUMN_NAME_GROUP_Name + " TEXT, "
			+ InviteMessageDao.COLUMN_NAME_REASON + " TEXT, "
			+ InviteMessageDao.COLUMN_NAME_STATUS + " INTEGER, "
			+ InviteMessageDao.COLUMN_NAME_ISINVITEFROMME + " INTEGER, "
			+ InviteMessageDao.COLUMN_NAME_TIME + " TEXT); ";
			
	private static final String ROBOT_TABLE_CREATE = "CREATE TABLE "
			+ UserDao.ROBOT_TABLE_NAME + " ("
			+ UserDao.ROBOT_COLUMN_NAME_ID + " TEXT PRIMARY KEY, "
			+ UserDao.ROBOT_COLUMN_NAME_NICK + " TEXT, "
			+ UserDao.ROBOT_COLUMN_NAME_AVATAR + " TEXT);";
			
	private static final String CREATE_PREF_TABLE = "CREATE TABLE "
            + UserDao.PREF_TABLE_NAME + " ("
            + UserDao.COLUMN_NAME_DISABLED_GROUPS + " TEXT, "
            + UserDao.COLUMN_NAME_DISABLED_IDS + " TEXT);";

	private DbOpenHelper(Context context){
		super(context, getUserDatabaseName(), null, DATABASE_VERSION);
	}
	
	public static DbOpenHelper getInstance(Context context){
		if (instance == null) {
			instance = new DbOpenHelper(context.getApplicationContext());
		}
		return instance;
	}
	
	private static String getUserDatabaseName() {
		return HXSDKHelper.getInstance().getHXId()+"_minichat.db";
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

}
