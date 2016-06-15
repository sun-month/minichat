package com.han.chat.db;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;

import com.han.chat.domain.InviteMessage;

public class InviteMessageDao {
	public static final String TABLE_NAME = "new_friends_msgs";
	public static final String COLUMN_NAME_ID = "id";
	public static final String COLUMN_NAME_FROM = "username";
	public static final String COLUMN_NAME_GROUP_ID = "groupid";
	public static final String COLUMN_NAME_GROUP_NAME = "groupname";
	
	public static final String COLUMN_NAME_TIME = "time";
	public static final String COLUMN_NAME_REASON = "reason";
	public static final String COLUMN_NAME_STATUS = "status";
	public static final String COLUMN_NAME_ISINVITEFROMME = "isInviteFromMe";

	public InviteMessageDao(Context context){
		ChatDBManager.getInstance().onInit(context);
	}
	
	/**
	 * 保存邀请消息
	 * @param message
	 * @return 返回这条消息再db中的id
	 */
	public Integer saveMessage(InviteMessage message){
		return ChatDBManager.getInstance().saveMessage(message);
	}
	
	/**
	 * 更新邀请消息
	 * @param msgId
	 * @param values
	 */
	public void updateMessage(int msgId, ContentValues values){
		ChatDBManager.getInstance().updateMessage(msgId, values);
	}
	
	/**
	 * 获取消息列表
	 * @return
	 */
	public List<InviteMessage> getMessageList(){
		return ChatDBManager.getInstance().getMessageList();
	}
	
	/**
	 * 根据来自谁发送的消息删除邀请消息
	 * @param from
	 */
	public void deleteMessage(String from){
		ChatDBManager.getInstance().deleteMessage(from);
	}
}
