package com.shiliuke.BabyLink.information;

import android.view.View;
import android.widget.TextView;
import com.shiliuke.bean.OrderNum;
import com.shiliuke.global.AppConfig;
import com.shiliuke.internet.TaskID;
import com.shiliuke.internet.VolleyListerner;
import com.shiliuke.model.BasicRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangzhi on 15/12/21.
 */
public class InformationUtils {

    public enum InformationKey {
        All_TAB,//全部,tab上的
        All_USER,//全部,我的主页上的
        CODE,//兑换码
        XIU,//我的秀逗
        ACTIVE,//我的活动
        EXCHANGE,//我的置换
        TALK,//我的话题
        FENSI,//我的粉丝
        WEIKUAN,//支付尾款
        TMP//临时使用
    }

    private static Map<InformationKey, TextView> viewMap = new HashMap<>();
    private static Map<InformationKey, Integer> numMap = new HashMap<>();

    public static void setInformationView(InformationKey key, TextView view) {
        viewMap.put(key, view);
    }

    public static void setInformationNum(InformationKey key, int num) {
        numMap.put(key, num);
    }
    public static void setInformationText(int num, TextView view) {
        setInformationView(InformationKey.TMP,view);
        setInformationNum(InformationKey.TMP, num);
        updateInformationView(InformationKey.TMP);
    }

    public static void updateInformationView(InformationKey key) {
        TextView view = viewMap.get(key);
        Integer num = numMap.get(key);
        if (view == null) {
            return;
        }
        if (num <= 0) {
            view.setVisibility(View.INVISIBLE);
        } else {
            view.setVisibility(View.VISIBLE);
            if (num > 99) {
                view.setText("99.");
            } else {
                view.setText(num + "");
            }
        }
    }

    public static void updataAllView() {
        for (InformationKey key : viewMap.keySet()) {
            updateInformationView(key);
        }
    }

    public static void requestAllNum(){
        Map<String, String> params = new HashMap();
        params.put("member_id", AppConfig.loginInfo.getMember_id());
        BasicRequest.getInstance().requestPost(
                new VolleyListerner() {
                    @Override
                    public void onResponse(String str, int taskid, Object obj) {
                        if (null != obj) {
                            OrderNum orderNum = (OrderNum) obj;
                            InformationUtils.setInformationNum(InformationUtils.InformationKey.All_TAB, orderNum.getDatas().getSum_num());
                            InformationUtils.setInformationNum(InformationUtils.InformationKey.All_USER, orderNum.getDatas().getSum_num());
                            InformationUtils.setInformationNum(InformationUtils.InformationKey.XIU, orderNum.getDatas().getXiu_num());
                            InformationUtils.setInformationNum(InformationUtils.InformationKey.ACTIVE, orderNum.getDatas().getActivity_num());
                            InformationUtils.setInformationNum(InformationUtils.InformationKey.EXCHANGE, orderNum.getDatas().getExchange_num());
                            InformationUtils.setInformationNum(InformationUtils.InformationKey.TALK, orderNum.getDatas().getTalk_num());
                            InformationUtils.setInformationNum(InformationUtils.InformationKey.CODE, orderNum.getDatas().getCode_num());
                            InformationUtils.setInformationNum(InformationUtils.InformationKey.WEIKUAN, orderNum.getDatas().getWei_num());
                            InformationUtils.setInformationNum(InformationKey.FENSI, orderNum.getDatas().getFans_num());
                            InformationUtils.updataAllView();
                        }
                    }

                    @Override
                    public void onResponseError(String error, int taskid) {

                    }
                }, TaskID.ACTION_ORDERNUM_MY,
                params, AppConfig.ORDERNUM_MY, OrderNum.class);
    }
}
