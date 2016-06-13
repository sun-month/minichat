package com.han.chat.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.easemob.EMConnectionListener;
import com.easemob.EMEventListener;
import com.easemob.EMNotifierEvent;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMContactListener;
import com.easemob.chat.EMContactManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMGroupManager;
import com.easemob.chat.GroupChangeListener;
import com.easemob.easeui.domain.EaseUser;
import com.easemob.easeui.ui.EaseContactListFragment;
import com.easemob.easeui.ui.EaseContactListFragment.EaseContactListItemClickListener;
import com.easemob.easeui.ui.EaseConversationListFragment;
import com.easemob.easeui.ui.EaseConversationListFragment.EaseConversationListItemClickListener;
import com.han.applib.controller.HXSDKHelper;
import com.han.chat.ChatHXSDKHelper;
import com.han.chat.R;
import com.han.chat.db.UserDao;
import com.han.chat.domain.User;

public class MainActivity extends FragmentActivity implements
		EaseConversationListItemClickListener,
		EaseContactListItemClickListener, EMEventListener {

	private static final int CONCERSATION_PAGE_INDEX = 0;
	private static final int CONTACT_PAGE_INDEX = 0;
	private static final int SETTING_PAGE_INDEX = 0;
	public static final String MAIN = "main";
	private TextView unReadLabel;
	private Button[] mTabs;
	private EaseConversationListFragment conversationListFragment;
	private EaseContactListFragment contactListFragment;
	private SettingFragment settingFragment;

	// tab指针位置
	private int index;
	private int currentTabIndex;
	private Fragment[] mFragments;
	private UserDao userDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initView();

		userDao = new UserDao(this);

		conversationListFragment = new EaseConversationListFragment();
		contactListFragment = new EaseContactListFragment();
		settingFragment = new SettingFragment();
		conversationListFragment.setConversationListItemClickListener(this);
		contactListFragment.setContactListItemClickListener(this);
		mFragments = new Fragment[] { conversationListFragment,
				contactListFragment, settingFragment };
		getSupportFragmentManager().beginTransaction()
				.add(R.id.fragment_container, conversationListFragment)
				.add(R.id.fragment_container, contactListFragment)
				.hide(contactListFragment).show(conversationListFragment)
				.commit();

		// 添加各种监听事件
		init();

		// 获取当前用户头像和昵称,...
	}

	private void initView() {
		unReadLabel = (TextView) findViewById(R.id.unread_msg_number);

		mTabs = new Button[3];
		mTabs[0] = (Button) findViewById(R.id.btn_conversation);
		mTabs[1] = (Button) findViewById(R.id.btn_address_list);
		mTabs[2] = (Button) findViewById(R.id.btn_setting);
		mTabs[0].setSelected(true);

	}

	private void init() {

		// 监听好友变化
		EMContactManager.getInstance().setContactListener(
				new MyContactListener());

		// 注册一个监听连接状态的listener
		EMChatManager.getInstance().addConnectionListener(
				new MyConnectionListener());

		// 注册一个群聊的listener
		EMGroupManager.getInstance().addGroupChangeListener(
				new MyGroupChangeListener());
	}

	public void onTabClicked(View v) {
		switch (v.getId()) {
		case R.id.btn_conversation:
			index = CONCERSATION_PAGE_INDEX;
			break;
		case R.id.btn_address_list:
			index = CONTACT_PAGE_INDEX;
			break;
		case R.id.btn_setting:
			index = SETTING_PAGE_INDEX;
			break;
		}
		if (currentTabIndex != index) {
			FragmentTransaction trx = getSupportFragmentManager()
					.beginTransaction();
			trx.hide(mFragments[currentTabIndex]);
			if (!mFragments[index].isAdded()) {
				trx.add(R.id.fragment_container, mFragments[index]);
			}
			trx.show(mFragments[index]).commit();
		}

		mTabs[currentTabIndex].setSelected(false);
		mTabs[index].setSelected(true);
		currentTabIndex = index;
	}

	@Override
	public void onListItemClicked(EMConversation conversion) {
		// 跳到聊天界面
		ChatActivity.actionStart(this, ChatActivity.class,
				conversion.getUserName());
	}

	@Override
	public void onListItemClicked(EaseUser user) {
		// 跳到聊天界面
		ChatActivity.actionStart(this, ChatActivity.class, user.getUsername());
	}

	/**
	 * 监听消息事件
	 */
	@Override
	public void onEvent(EMNotifierEvent arg0) {

	}

	/**
	 * 好友变化listener
	 */
	public class MyContactListener implements EMContactListener {

		@Override
		public void onContactAdded(List<String> usernameList) {
			Map<String, User> localUsers = ((ChatHXSDKHelper) HXSDKHelper
					.getInstance()).getContactList();
			Map<String, User> newUsers = new HashMap<String, User>();
			for (String username : usernameList) {
				User user = new User(username);
				user.setHeader(username);
				if (!localUsers.containsKey(username)) {
					userDao.saveContact(user);
				}
				newUsers.put(username, user);
			}
			localUsers.putAll(newUsers);
			if (currentTabIndex == CONTACT_PAGE_INDEX) {
				contactListFragment.refresh();
			}
		}

		@Override
		public void onContactAgreed(String username) {

		}

		@Override
		public void onContactDeleted(List<String> usernameList) {
			Map<String, User> localUsers = ((ChatHXSDKHelper)HXSDKHelper.getInstance()).getContactList();
			for (String username : usernameList) {
				localUsers.remove(username);
				userDao.deleteContact(username);
			}
			
			//刷新UI
			contactListFragment.refresh();
		}

		@Override
		public void onContactInvited(String username, String reason) {

		}

		@Override
		public void onContactRefused(String username) {

		}
	}

	/**
	 * 连接listener
	 * 
	 * @author heng
	 * 
	 */
	public class MyConnectionListener implements EMConnectionListener {

		@Override
		public void onConnected() {

		}

		@Override
		public void onDisconnected(int arg0) {

		}

	}

	/**
	 * 群聊listener
	 * 
	 * @author heng
	 * 
	 */
	public class MyGroupChangeListener implements GroupChangeListener {

		@Override
		public void onApplicationAccept(String arg0, String arg1, String arg2) {

		}

		@Override
		public void onApplicationDeclined(String arg0, String arg1,
				String arg2, String arg3) {

		}

		@Override
		public void onApplicationReceived(String arg0, String arg1,
				String arg2, String arg3) {

		}

		@Override
		public void onGroupDestroy(String arg0, String arg1) {

		}

		@Override
		public void onInvitationAccpted(String arg0, String arg1, String arg2) {

		}

		@Override
		public void onInvitationDeclined(String arg0, String arg1, String arg2) {

		}

		@Override
		public void onInvitationReceived(String arg0, String arg1, String arg2,
				String arg3) {

		}

		@Override
		public void onUserRemoved(String arg0, String arg1) {

		}
	}
}
