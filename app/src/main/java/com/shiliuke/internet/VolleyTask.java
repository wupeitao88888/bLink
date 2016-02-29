/*
 * Copyright (C) 2015 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 */
package com.shiliuke.internet;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.Map;

/**
 * @author lvfl
 *         创建时间：2015年11月5日 上午10:31:13
 *         <p/>
 *         轻型网络请求框架
 */
public class VolleyTask {

    private Context context;
    private RequestQueue mQueue;
    private static VolleyTask instance;


    private VolleyTask(Context context) {
        this.context = context;
        mQueue = Volley.newRequestQueue(context);

    }

    public static VolleyTask getInstance(Context context) {
        if (instance == null) {
            synchronized (VolleyTask.class) {
                if (instance == null) {
                    instance = new VolleyTask(context);
                }
            }
        }
        return instance;
    }

    public void addRequest(Request request) {
        //设置超时时间
        /***
         * Volley provides an easy way to implement your RetryPolicy for your requests.
         Volley sets default Socket & ConnectionTImeout to 5 secs for all requests.
         RetryPolicy is an interface where you need to implement your logic of how you want to retry a particular request when a timeout happens.

         It deals with these three parameters

         Timeout - Specifies Socket Timeout in millis per every retry attempt.
         Number Of Retries - Number of times retry is attempted.
         Back Off Multiplier - A multiplier which is used to determine exponential time set to socket for every retry attempt.
         For ex. If RetryPolicy is created with these values

         Timeout - 3000 secs, Num of Attempt - 2, Back Off Multiplier - 2
         Attempt 1:

         time = time + (time * Back Off Multiplier );
         time = 3000 + 6000 = 9000
         Socket Timeout = time;
         Request dispatched with Socket Timeout of 9 Secs
         Attempt 2:

         time = time + (time * Back Off Multiplier );
         time = 9000 + 18000 = 27000
         Socket Timeout = time;
         Request dispatched with Socket Timeout of 27 Secs
         So at the end of Attempt 2 if still Socket Timeout happenes Volley would throw a TimeoutError in your UI Error response handler.
         */
        request.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mQueue.add(request);
        mQueue.start();
    }

    private void initParam(Map<String, String> params) {
        //参数初始化(预留方法)
    }

    public void cancleAll() {
        mQueue.cancelAll(context);
    }

}
