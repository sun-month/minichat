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
	 * ����������Ϣ
	 * @param message
	 * @return ����������Ϣ��db�е�id
	 */
	public Integer saveMessage(InviteMessage message){
		return ChatDBManager.getInstance().saveMessage(message);
	}
	
	/**
	 * ����������Ϣ
	 * @param msgId
	 * @param values
	 */
	public void updateMessage(int msgId, ContentValues values){
		ChatDBManager.getInstance().updateMessage(msgId, values);
	}
	
	/**
	 * ��ȡ��Ϣ�б�
	 * @return
	 */
	public List<InviteMessage> getMessageList(){
		return ChatDBManager.getInstance().getMessageList();
	}
	
	/**
	 * ��������˭���͵���Ϣɾ��������Ϣ
	 * @param from
	 */
	public void deleteMessage(String from){
		ChatDBManager.getInstance().deleteMessage(from);
	}
}
