package com.han.chat;

import java.util.Map;

import com.han.applib.controller.HXSDKHelper;
import com.han.applib.model.HXSDKModel;
import com.han.chat.domain.User;

public class ChatHXSDKHelper extends HXSDKHelper {

	private static final String TAG = "ChatHXSDKHelper";
	private Map<String, User> contactList;

	@Override
	protected HXSDKModel createModel() {
		return new ChatHXSDKModel(appContext);
	}

	public void setContactList(Map<String, User> contactList) {
		this.contactList = contactList;
		
	}

	public Map<String, User> getContactList(){
		if (contactList==null) {
			contactList = ((ChatHXSDKModel)getModel()).getContactList();
		}
		return contactList;
	}
	
}
