package com.han.chat.domain;

public class InviteMessge {
	//˭����
	private String from;
	//��Ӻ��ѷ���ʱ��
	private long time;
	//�������
	private String reason;
	//����״̬����δͬ�⣬δ��֤����ͬ���
	private InviteMessageStatus status;
	//ȺID
	private String groupId;
	//Ⱥ����
	private String groupName;
	
	private int id;
	
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}


	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public InviteMessageStatus getStatus() {
		return status;
	}

	public void setStatus(InviteMessageStatus status) {
		this.status = status;
	}

	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	public enum InviteMessageStatus{
		/**������*/
		BRINVITEED,
		/**���ܾ�*/
		BEREFUSED,
		/**�Է�ͬ��*/
		BEAGREED,
		/**�Է�����*/
		BEAPPLYED,
		/**��ͬ���˶Է�������*/
		AGREED,
		/**�Ҿܾ��˶Է�������*/
		REFUSED
	}
}
