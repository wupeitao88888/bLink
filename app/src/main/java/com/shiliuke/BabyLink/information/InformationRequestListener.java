package com.shiliuke.BabyLink.information;

import com.shiliuke.internet.VolleyListerner;

/**
 * Created by wangzhi on 15/12/21.
 */
public class InformationRequestListener implements VolleyListerner {
    @Override
    public void onResponse(String str, int taskid, Object obj) {
        InformationUtils.requestAllNum();
    }

    @Override
    public void onResponseError(String error, int taskid) {

    }

}
