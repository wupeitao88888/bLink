package com.shiliuke.bean;

public class ChatMsgEntity {

	private String name;

	private long date;

	private String text;

	private String isComMeg;
	private String siteid;// 平台ID
	private String sellerid;// 商户ID
	private String userid;// 客服id
	private String msgtype;// 信息类型
	private String settingid;// 咨询入口
	private String url;// 图片的地址
	private String sourceurl;// 下载图片的地址
	private String format;// 语音格式
	private String voiceurl;// 语音地址
	private String length;// 语音长度
	private String remark;// 访客评价
	private String msgid;// 消息id
	private String pcid;// 设备id
	private String sendstatus;// 消息的状态
	private String username;// 用户名
	private String group;// 组名
	private String localimage;// 本地图片的地址
	private String total;// 消息总数

	public String getLocalimage() {
		return localimage;
	}

	public void setLocalimage(String localimage) {
		this.localimage = localimage;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSendstatus() {
		return sendstatus;
	}

	public void setSendstatus(String sendstatus) {
		this.sendstatus = sendstatus;
	}

	public String getPcid() {
		return pcid;
	}

	public void setPcid(String pcid) {
		this.pcid = pcid;
	}

	public String getIsComMeg() {
		return isComMeg;
	}

	public void setIsComMeg(String isComMeg) {
		this.isComMeg = isComMeg;
	}

	public String getSiteid() {
		return siteid;
	}

	public void setSiteid(String siteid) {
		this.siteid = siteid;
	}

	public String getSellerid() {
		return sellerid;
	}

	public void setSellerid(String sellerid) {
		this.sellerid = sellerid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public String getSettingid() {
		return settingid;
	}

	public void setSettingid(String settingid) {
		this.settingid = settingid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSourceurl() {
		return sourceurl;
	}

	public void setSourceurl(String sourceurl) {
		this.sourceurl = sourceurl;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getVoiceurl() {
		return voiceurl;
	}

	public void setVoiceurl(String voiceurl) {
		this.voiceurl = voiceurl;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public ChatMsgEntity() {
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return super.equals(o);
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	/**
	 * @param name
	 * @param date
	 * @param text
	 * @param isComMeg
	 * @param siteid
	 * @param sellerid
	 * @param userid
	 * @param msgtype
	 * @param settingid
	 * @param url
	 * @param sourceurl
	 * @param format
	 * @param voiceurl
	 * @param length
	 * @param remark
	 * @param msgid
	 * @param pcid
	 * @param sendstatus
	 * @param username
	 * @param group
	 * @param total
	 */
	public ChatMsgEntity(String name, long date, String text, String isComMeg,
			String siteid, String sellerid, String userid, String msgtype,
			String settingid, String url, String sourceurl, String format,
			String voiceurl, String length, String remark, String msgid,
			String pcid, String sendstatus, String username, String group,
			String total) {
		super();
		this.name = name;
		this.date = date;
		this.text = text;
		this.isComMeg = isComMeg;
		this.siteid = siteid;
		this.sellerid = sellerid;
		this.userid = userid;
		this.msgtype = msgtype;
		this.settingid = settingid;
		this.url = url;
		this.sourceurl = sourceurl;
		this.format = format;
		this.voiceurl = voiceurl;
		this.length = length;
		this.remark = remark;
		this.msgid = msgid;
		this.pcid = pcid;
		this.sendstatus = sendstatus;
		this.username = username;
		this.group = group;
		this.total = total;
	}

	/**
	 * @param date
	 * @param text
	 * @param isComMeg
	 * @param siteid
	 * @param sellerid
	 * @param userid
	 * @param msgtype
	 * @param settingid
	 * @param url
	 * @param sourceurl
	 * @param format
	 * @param voiceurl
	 * @param length
	 * @param remark
	 * @param msgid
	 * @param sendstatus
	 * @param username
	 * @param group
	 * @param total
	 */
	public ChatMsgEntity(long date, String text, String isComMeg,
			String siteid, String sellerid, String userid, String msgtype,
			String settingid, String url, String sourceurl, String format,
			String voiceurl, String length, String remark, String msgid,
			String sendstatus, String username, String group, String total) {
		super();
		this.date = date;
		this.text = text;
		this.isComMeg = isComMeg;
		this.siteid = siteid;
		this.sellerid = sellerid;
		this.userid = userid;
		this.msgtype = msgtype;
		this.settingid = settingid;
		this.url = url;
		this.sourceurl = sourceurl;
		this.format = format;
		this.voiceurl = voiceurl;
		this.length = length;
		this.remark = remark;
		this.msgid = msgid;
		this.sendstatus = sendstatus;
		this.username = username;
		this.group = group;
		this.total = total;
	}

}
