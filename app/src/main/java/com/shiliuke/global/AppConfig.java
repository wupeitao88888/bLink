/*
 * Copyright (C) 2012 www.amsoft.cn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shiliuke.global;


// TODO: Auto-generated Javadoc

import com.shiliuke.BabyLink.Information;
import com.umeng.update.UpdateConfig;

/**
 * © 2012 amsoft.cn
 * 名称：AbAppConfig.java
 * 描述：初始设置类.
 *
 * @author 还如一梦中
 * @version v1.0
 * @date：2014-07-03 下午1:33:39
 */
public class AppConfig {
    public static Information loginInfo = new Information();

    /**
     * UI设计的基准宽度.
     */
    public static int UI_WIDTH = 720;

    /**
     * UI设计的基准高度.
     */
    public static int UI_HEIGHT = 1080;
    /**
     * Textview为空.
     */
    public static String TEXTVIEW_NULL = "TextView为空";
    /**
     * Text为空.
     */
    public static String TEXT_NULL = "Text为空";
    /**
     * 默认 SharePreferences文件名.
     */
    public static String SHARED_PATH = "app_share";
    //基础URL
    public static String BASE_URL = "http://app.chinababylink.com";
    // 获取验证码
    public static String GET_CODE = "/mobile.php/Mobile/Login/sendCode";
    // 注册
    public static String SEND_REGISTER = "/mobile.php/Mobile/Login/register";
    // 登陆
    public static String LOGIN = "/mobile.php/Mobile/Login";
    // 秀逗Link
    public static String XIUDOU_LINK = "/mobile.php/Mobile/Xiu/link";
    // 秀逗广场
    public static String XIUDOU_GUANGCHANG = "/mobile.php/Mobile/Xiu";
    // 秀逗_添加评论
    public static String XIUDOU_ADDCOMMEND = "/mobile.php/Mobile/Xiu/addCommend";
    // 秀逗_加关注
    public static String XIUDOU_ADDFRIEND = "/mobile.php/Mobile/Xiu/addFriend";
    public static String XIUDOU_ZAN = "/mobile.php/Mobile/Xiu/zan";
    //基础URL
    public static String BASE_URL_V1 = "/mobile.php/Mobile";
    //活动列表
    public static String ACTIVITY = BASE_URL_V1 + "/Activity";
    //活动详情
    public static String ACTIVITY_INFO = ACTIVITY + "/activity_info";
    //活动评论
    public static String ACTIVITY_COMMEND = ACTIVITY + "/commend";
    //活动报名
    public static String ACTIVITY_SIGN_UP = ACTIVITY + "/signUp";
    //话题列表
    public static String TALK = BASE_URL_V1 + "/Talk";
    //话题评论
    public static String TALK_COMMEND = TALK + "/commend";
    //点赞
    public static String TALK_ZAN = TALK + "/zan";
    // 忘记密码
    public static String FORGET_PASSWORD = "/mobile.php/Mobile/Login/forgetPassword";
    // 置换列表
    public static String HOME_ZHIHUAN_LIST = "/mobile.php/Mobile/Exchange";
    // 置换详情
    public static String HOME_ZHIHUAN_LIST_INFO = HOME_ZHIHUAN_LIST + "/exchangeInfo";
    // 完善资料
    public static String COMPLETE_DATA = "/mobile.php/Mobile/Login/perfect";
    // 获取个人信息
    public static String GET_INFORMATION = BASE_URL + "/mobile.php/Mobile/Member/member_info";
    // 第三方登录
    public static String QQ_OTHER_LOGIN = BASE_URL + "/mobile.php/Mobile/Login/otherLogin";
    // 发布秀
    public static String SENDSHOW = "/mobile.php/Mobile/Xiu/addXiu";
    // 我参与的可以置换列表
    public static String HOME_ZHIHUAN_LIST_ISSUEEXCHANGE = HOME_ZHIHUAN_LIST + "/issueExchange";
    // 我参与的可以置换列表
    public static String HOME_ZHIHUAN_LIST_EXCHANGE = HOME_ZHIHUAN_LIST + "/exchange";
    // 发布话题
    public static String SENDTOPIC = "/mobile.php/Mobile/Talk/addTalk";
    // 发布置换
    public static String SENDTEXCHANGE = "/mobile.php/Mobile/Exchange/addExchange";
    // 发现列表
    public static String FINDLIST = "/mobile.php/Goods";
    //我发起的活动列表
    public static String ME_ACTIVTY = "/mobile.php/Mobile/Activity/MyLaunch";
    //我参与的活动列表
    public static String ME_ACTIVTY_JION = "/mobile.php/Mobile/Activity/MyJoin";
    //我参与的活动列表
    public static String ME_TALK = "/mobile.php/Mobile/Talk";
    // 发步活动
    public static String SENDACTIVITY = "/mobile.php/Mobile/Activity/addActivity";
    // 发现详情
    public static String FINDINFO = "/mobile.php/Goods/goods_info";
    // 支付
    public static String PAY = "/mobile.php/Mobile/Order/pay";
    // 发现邀请好友列表
    public static String FIND_INVITE_LIST = "/mobile.php/Goods/friend_list";
    //删除话题
    public static String DELECT_TOPIC = "/mobile.php/Mobile/Talk/delTalk";
    //我发起的置换
    public static String MECHANGE = "/mobile.php/Mobile/Exchange/meExchange";

    // 发现邀请好友
    public static String FIND_INVITE_FRIENDS = "/mobile.php/Goods/issue_friend";
    // 更改手机号
    public static String UPDATE_MOBILE = "/mobile.php/Mobile/Member/updateMobile";
    // 修改密码
    public static String UPDATE_PASSWORD = "/mobile.php/Mobile/Member/updatePassword";
    // 我的秀逗
    public static String MY_BEANSHOW = "/mobile.php/Mobile/Xiu/myXiu";
    //邻居
    public static String NEIGHBOR = "/mobile.php/Mobile/Xiu/neighborList";
    //好友
    public static String FRIEND = "/mobile.php/Mobile/Xiu/friendList";
    //删除好友
    public static String DEL_FRIEND = "/mobile.php/Mobile/Xiu/deleteFriend";
    //我的粉丝
    public static String ME_FANS = "/mobile.php/Mobile/Xiu/fansList";
    // 获得小区
    public static String GET_AREA_LIST = "/mobile.php/Mobile/Index/autoPosition";
    // 获得联想小区
    public static String GET_AREA_LIST_AUTO = "/mobile.php/Mobile/Index/search";
    // 订单列表
    public static String ORDER_LIST = "/mobile.php/Mobile/Order";
    // 完成置换
    public static String FINISH_EXCHANGE = "/mobile.php/Mobile/Exchange/finishExchange";
    //取消置换
    public static String CANCEL_EXCHANGE = "/mobile.php/Mobile/Exchange/cancelExchange";
    //取消报名
    public static String CANCEL_ISSUE = "/mobile.php/Mobile/Activity/cancel_activity";
    //退款
    public static String ORDER_REFUND = "/mobile.php/Mobile/Order/refund";
    //取消订单
    public static String ORDER_CANCEL = "/mobile.php/Mobile/Order/cancel";
    // 兑换码列表
    public static String DUIHUANMA_LIST = "/mobile.php/Mobile/Order";
    // 获取城市列表
    public static String CHOICE_GET_CITY = "/mobile.php/Mobile/Index/getCityList";
    // 修改小区/附属小区
    public static String CHANGE_AEAR = "/mobile.php/Mobile/Member/updateMember";
    // 订单个数
    public static String ORDERNUM_MY = "/mobile.php/Member/getOrderNum";
    // 发现分类
    public static String FIND_CATEGORY = "/mobile.php/Goods/get_class";
    // 修改未读数量
    public static String UPDATE_INFORMATION = "/mobile.php/Mobile/Member/update_can";
    //微信appID
    public static String WX_APP_ID = "wx0fbabc189e400940";
    public static String WX_APP_SECRET = "d4624c36b6795d1d99dcf0547af5443d";
    public static final String ACTION_ZHIFUBAO_SUCCESS = "com.shiliuke.BabLink.ACTION_ZFBSHARESHARE";
    public static final String ACTION_ZHIFUBAO_FAILD = "com.shiliuke.BabLink.ACTION_ZFBSHAREFAILD";
    public static final String ACTION_ZHIFUBAO_CHECKING = "com.shiliuke.BabLink.ACTION_ZFBSHARECHECKING";
    public static final String ACTION_WXSHARE_SUCCESS = "com.shiliuke.BabLink.ACTION_WXSHARESHARE";
    public static final String ACTION_WXSHARE_FAILD = "com.shiliuke.BabLink.ACTION_WXSHAREFAILD";
    public static final String ACTION_WXPAY_SUCCESS = "com.shiliuke.BabLink.ACTION_WXPAYSUCCESS";
    public static final String ACTION_WXPAY_CANCLE = "com.shiliuke.BabLink.ACTION_WXPAYCANCLE";
    public static final String ACTION_WXPAY_FAILD = "com.shiliuke.BabLink.ACTION_WXPAYFALID";
    public static final String ACTION_WXPAY_UNSUPPORT = "com.shiliuke.BabLink.ACTION_WXPAYUNSUPPORT";

    public static final int ACTION_REFRESH = 1000;//eventbar
    public static final String SHARE_URL="www.baidu.com";
//来打开日志输出，发布应用时请去掉。


}
