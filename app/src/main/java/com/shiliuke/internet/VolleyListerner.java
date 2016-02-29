package com.shiliuke.internet;


/**
 * @author lvfl
 * 创建时间：2015年11月5日 下午12:08:45
 *
 * 轻量级网络监听接口
 */
public interface VolleyListerner {

	/**
	 * 当访问网络成功的时候
	 * 
	 * @param taskid
	 *            访问网络当前的任务id
	 * @param str
	 *            后台返回的json串
	 *@param obj
	 *            解析实体
	 *
	 */
	public void onResponse(String str, int taskid,Object obj);

	/**
	 * 
	 * @param taskid
	 *            访问网络当前的任务id
	 * @param error
	 *            错误信息
	 */
	public void onResponseError(String error, int taskid);

}
