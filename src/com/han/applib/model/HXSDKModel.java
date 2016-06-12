/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.han.applib.model;

/**
 * HX SDK app model which will manage the user data and preferences
 * 
 * @author easemob
 * 
 */
public abstract class HXSDKModel {
	public abstract void setSettingMsgNotification(boolean paramBoolean);

	// �𶯺������ܿ��أ�����Ϣʱ���Ƿ�����˿��ش�
	// the vibrate and sound notification are allowed or not?
	public abstract boolean getSettingMsgNotification();

	public abstract void setSettingMsgSound(boolean paramBoolean);

	// �Ƿ������
	// sound notification is switched on or not?
	public abstract boolean getSettingMsgSound();

	public abstract void setSettingMsgVibrate(boolean paramBoolean);

	// �Ƿ����
	// vibrate notification is switched on or not?
	public abstract boolean getSettingMsgVibrate();

	public abstract void setSettingMsgSpeaker(boolean paramBoolean);

	// �Ƿ��������
	// the speaker is switched on or not?
	public abstract boolean getSettingMsgSpeaker();

	public abstract boolean saveHXId(String hxId);

	public abstract String getHXId();

	public abstract boolean savePassword(String pwd);

	public abstract String getPwd();

	/**
	 * ����application���ڵ�process name,Ĭ���ǰ���
	 * 
	 * @return
	 */
	public abstract String getAppProcessName();

	/**
	 * �Ƿ����ǽ��պ�������
	 * 
	 * @return
	 */
	public boolean getAcceptInvitationAlways() {
		return false;
	}

	/**
	 * �Ƿ���Ҫ���ź��ѹ�ϵ��Ĭ����false
	 * 
	 * @return
	 */
	public boolean getUseHXRoster() {
		return false;
	}

	/**
	 * �Ƿ���Ҫ�Ѷ���ִ
	 * 
	 * @return
	 */
	public boolean getRequireReadAck() {
		return true;
	}

	/**
	 * �Ƿ���Ҫ���ʹ��ִ
	 * 
	 * @return
	 */
	public boolean getRequireDeliveryAck() {
		return false;
	}

	/**
	 * �Ƿ�������sandbox���Ի���. Ĭ���ǹص��� ����sandbox ���Ի��� ���鿪���߿���ʱ���ô�ģʽ
	 */
	public boolean isSandboxMode() {
		return false;
	}

	/**
	 * �Ƿ�����debugģʽ
	 * 
	 * @return
	 */
	public boolean isDebugMode() {
		return true;
	}

	public void setGroupsSynced(boolean synced) {
	}

	public boolean isGroupsSynced() {
		return false;
	}

	public void setContactSynced(boolean synced) {
	}

	public boolean isContactSynced() {
		return false;
	}

	public void setBlacklistSynced(boolean synced) {
	}

	public boolean isBacklistSynced() {
		return false;
	}
}
