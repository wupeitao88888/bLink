/*******************************************************************************
 * Copyright (c) 2014 by ehoo Corporation all right reserved.
 * 2014-7-15 
 * 
 *******************************************************************************/
package com.shiliuke.bean;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2014-7-15
 * 编写人员:	 吴培涛
 * 
 * 历史记录
 * 1、修改日期:
 *    修改人:
 *    修改内容:
 * </pre>
 */
public class ChatGrouplist {

	private String siteid;
	private String sellerid;
	private String mgroup;
	private String userid;
	private String msgtype;

	private String settingid;
	private String url;
	private int total;
	private String id;
	private String imageurl;
	private String currency;
	private String price;
	private int stock;
	private String category;
	private String name;
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getMgroup() {
		return mgroup;
	}

	public void setMgroup(String mgroup) {
		this.mgroup = mgroup;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	/**
	 * @param siteid
	 * @param sellerid
	 * @param userid
	 * @param msgtype
	 * @param settingid
	 * @param url
	 * @param mgroup
	 * @param total
	 */
	public ChatGrouplist(String siteid, String sellerid, String userid,
			String msgtype, String settingid, String url, String mgroup,
			int total) {
		super();
		this.siteid = siteid;
		this.sellerid = sellerid;
		this.userid = userid;
		this.msgtype = msgtype;
		this.settingid = settingid;
		this.url = url;
		this.mgroup = mgroup;
		this.total = total;
	}

	/**
	 * 
	 */
	public ChatGrouplist() {
		super();
	}

}
