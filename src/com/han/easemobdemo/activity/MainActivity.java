package com.han.easemobdemo.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.easemob.chat.EMConversation;
import com.easemob.easeui.domain.EaseUser;
import com.easemob.easeui.ui.EaseContactListFragment;
import com.easemob.easeui.ui.EaseContactListFragment.EaseContactListItemClickListener;
import com.easemob.easeui.ui.EaseConversationListFragment;
import com.easemob.easeui.ui.EaseConversationListFragment.EaseConversationListItemClickListener;
import com.han.easemobdemo.R;
import com.han.easemobdemo.fragment.SettingFragment;

public class MainActivity extends FragmentActivity implements
		EaseConversationListItemClickListener, EaseContactListItemClickListener {

	private TextView unReadLabel;
	private Button[] mTabs;
	private EaseConversationListFragment conversationListFragment;
	private EaseContactListFragment contactListFragment;
	private SettingFragment settingFragment;

	// tab指针位置
	private int index;
	private int currentTabIndex;
	private Fragment[] mFragments;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {
		unReadLabel = (TextView) findViewById(R.id.unread_msg_number);
		mTabs = new Button[3];
		mTabs[0] = (Button) findViewById(R.id.btn_conversation);
		mTabs[1] = (Button) findViewById(R.id.btn_address_list);
		mTabs[2] = (Button) findViewById(R.id.btn_setting);
		mTabs[0].setSelected(true);

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
				.add(R.id.fragment_container, settingFragment)
				.show(conversationListFragment).commit();
	}

	public void onTabClicked(View v) {
		switch (v.getId()) {
		case R.id.btn_container_conversation:
			index = 0;
			break;
		case R.id.btn_container_address_list:
			index = 1;
			break;
		case R.id.btn_container_setting:
			index = 2;
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
}
