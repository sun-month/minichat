package com.han.chat.domain;

import com.easemob.chat.EMContact;

public class User extends EMContact {
	private int unReadMsgCount;
	private String header;
	private String avatar;

	public User() {
	}

	public User(String username) {
		this.username = username;
	}

	public int getUnReadMsgCount() {
		return unReadMsgCount;
	}

	public void setUnReadMsgCount(int unReadMsgCount) {
		this.unReadMsgCount = unReadMsgCount;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	@Override
	public int hashCode() {
		return 17 * getUsername().hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof User)) {
			return false;
		}
		return getUsername().equals(((User) o).getUsername());
	}

	@Override
	public String toString() {
		return nick == null ? username : nick;
	}
}
