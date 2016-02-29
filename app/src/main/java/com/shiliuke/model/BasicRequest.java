/*
 * Copyright (C) 2015 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 */
package com.shiliuke.model;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.shiliuke.bean.BaseModel;
import com.shiliuke.global.AppConfig;
import com.shiliuke.global.MApplication;
import com.shiliuke.internet.NormalPostRequest;
import com.shiliuke.internet.VolleyListerner;
import com.shiliuke.internet.VolleyTask;
import com.shiliuke.utils.L;
import org.json.JSONObject;

import java.util.Map;

/**
 * @author yuemx 创建时间：2015年4月17日 下午12:08:45
 *         <p>
 *         类 描述
 */
public class BasicRequest<T extends BaseModel> {

    private static BasicRequest basic;
    private static Gson gson = new Gson();

    /**
     * 获取唯一实例对象
     */
    public static BasicRequest getInstance() {
        if (basic == null) {
            synchronized (BasicRequest.class) {
                if (basic == null) {
                    basic = new BasicRequest();
                }
            }
        }


        return basic;
    }

    public void Login(final VolleyListerner listerner, final int action, final Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        sb.append(AppConfig.BASE_URL);
        sb.append(AppConfig.LOGIN);
        String requesturl = sb.toString();
        L.e("URL=" + requesturl);
        VolleyTask.getInstance(MApplication.getApp()).addRequest(new NormalPostRequest(requesturl, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (null != response) {
                    L.d(response.toString());
                    listerner.onResponse(response.toString(), action, null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (!TextUtils.isEmpty(error.getMessage())) {
                    listerner.onResponseError(error.getMessage(),
                            action);
                }
            }
        }, params));
    }

    /**
     * 通过 “ID” 请求列表的时候调用的方法
     */
    public void requestPost(final VolleyListerner listerner, final int action, final Map<String, String> params, String strs) {
        StringBuilder sb = new StringBuilder();
        if (!strs.equalsIgnoreCase("http://wxpay.weixin.qq.com/pub_v2/app/app_pay.php?plat=android")) {
            sb.append(AppConfig.BASE_URL);
        }
        sb.append(strs);
        String requesturl = sb.toString();

        L.e("发起URL=" + requesturl + addParams(params));
        VolleyTask.getInstance(MApplication.getApp()).addRequest(new NormalPostRequest(requesturl, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (null != response) {
                    L.e("action=" + action + " 网络成功：" + response.toString());
                    try {
                        Map map = JSON.parseObject(response.toString(), Map.class);
                        if (!"0".equalsIgnoreCase(map.get("code").toString())) {
                            listerner.onResponseError(map.get("datas").toString(),
                                    action);
                            return;
                        }
                    } catch (Exception e) {
                        listerner.onResponseError("服务器异常",
                                action);
                        return;
                    }
                    listerner.onResponse(response.toString(), action, null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (!TextUtils.isEmpty(error.getMessage())) {
                    L.e("action=" + action + " 网络错误");
                    listerner.onResponseError(error.getMessage(),
                            action);
                }
            }
        }, params));
    }

    /**
     * 通过 “ID” 请求列表的时候调用的方法，
     * return Model
     */
    public void requestPost(final VolleyListerner listerner, final int action, final Map<String, String> params, String strs, final Class<T> modelClass) {
        StringBuilder sb = new StringBuilder();
        sb.append(AppConfig.BASE_URL);
        sb.append(strs);
        String requesturl = sb.toString();

        L.e("发起URL=" + requesturl + addParams(params));
        VolleyTask.getInstance(MApplication.getApp()).addRequest(new NormalPostRequest(requesturl, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (!TextUtils.isEmpty(response.toString())) {
                    L.e("action=" + action + " 网络成功：" + response.toString());
                    try {
                        Map map = JSON.parseObject(response.toString(), Map.class);
                        if (!"0".equalsIgnoreCase(map.get("code").toString())) {
                            listerner.onResponseError(map.get("datas").toString(),
                                    action);
                            return;
                        }
                    } catch (Exception e) {
                        listerner.onResponseError("服务器异常",
                                action);
                        return;
                    }
                    if (modelClass == null) {
                        listerner.onResponse(response.toString(), action, null);
                    } else {
                        try {
                            listerner.onResponse(response.toString(), action, gson.fromJson(response.toString(), modelClass));
                        }catch (Exception e) {
                            listerner.onResponseError("数据异常",
                                    action);
                        }
                    }
                }else{
                    listerner.onResponseError("网络异常",
                            action);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (!TextUtils.isEmpty(error.getMessage())) {
                    L.e("action=" + action + " 网络错误：" + error.getMessage());
                    listerner.onResponseError(error.getMessage(),
                            action);
                }else{
                    listerner.onResponseError("网络异常",
                            action);
                }
            }
        }, params));
    }

    public String addParams(Map<String, String> params) {
        StringBuilder result = new StringBuilder();
        for (String key : params.keySet()) {
            if (params.size() > 0)
                result.append("&");
            result.append(key);
            result.append("=");
            result.append(params.get(key));
        }
        try {
            if (result.toString().substring(1, result.toString().length()).length() > 0) {
                return "?" + result.toString().substring(1, result.toString().length());
            } else {
                return result.toString().substring(1, result.toString().length()) + "";
            }
        } catch (Exception e) {
            return "";
        }
    }


    /**
     * 通过 “ID” 请求列表的时候调用的方法
     * Class<?>  tClass传入实体  返回object
     */
    public void requestPOST(final VolleyListerner listerner, final int action, final Map<String, String> params, String strs, final Class<?> tClass) {
        StringBuilder sb = new StringBuilder();
        sb.append(AppConfig.BASE_URL);
        sb.append(strs);
        String requesturl = sb.toString();

        L.e("发起URL=" + requesturl + addParams(params));
        VolleyTask.getInstance(MApplication.getApp()).addRequest(new NormalPostRequest(requesturl, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (!TextUtils.isEmpty(response.toString())) {
                    L.e("action=" + action + " 网络成功：" + response.toString());
                    Object obj = gson.fromJson(response.toString(), tClass);
                    listerner.onResponse(response.toString(), action, obj);
                }else{
                    listerner.onResponseError("网络异常",
                            action);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (!TextUtils.isEmpty(error.getMessage())) {
                    L.e("action=" + action + " 网络错误：" + error.getMessage());
                    listerner.onResponseError(error.getMessage(),
                            action);
                }else{
                    listerner.onResponseError("网络异常",
                            action);
                }
            }
        }, params));
    }


    /**
     * @param listerner
     * @param action
     * @param params    参数
     *                  获取验证码 lvfl
     */
    public void getCodePost(final VolleyListerner listerner, final int action, final Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        sb.append(AppConfig.BASE_URL);
        sb.append(AppConfig.GET_CODE);
        String requesturl = sb.toString();

        L.e("URL=" + requesturl);
        VolleyTask.getInstance(MApplication.getApp()).addRequest(new NormalPostRequest(requesturl, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (null != response) {
                    listerner.onResponse(response.toString(), action, null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (!TextUtils.isEmpty(error.getMessage())) {
                    listerner.onResponseError(error.getMessage(),
                            action);
                }
            }
        }, params));
    }

    /**
     * @param listerner
     * @param action
     * @param params    注册
     */
    public void sendRegisterPost(final VolleyListerner listerner, final int action, final Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        sb.append(AppConfig.BASE_URL);
        sb.append(AppConfig.SEND_REGISTER);
        String requesturl = sb.toString();

        L.e("URL=" + requesturl);
        VolleyTask.getInstance(MApplication.getApp()).addRequest(new NormalPostRequest(requesturl, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (null != response) {
                    listerner.onResponse(response.toString(), action, null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (!TextUtils.isEmpty(error.getMessage())) {
                    listerner.onResponseError(error.getMessage(),
                            action);
                }
            }
        }, params));
    }

    /**
     * @param listerner
     * @param action
     * @param params    参数
     *                  忘记密码 lvfl
     */
    public void getPswPost(final VolleyListerner listerner, final int action, final Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        sb.append(AppConfig.BASE_URL);
        sb.append(AppConfig.FORGET_PASSWORD);
        String requesturl = sb.toString();

        L.e("URL=" + requesturl);
        VolleyTask.getInstance(MApplication.getApp()).addRequest(new NormalPostRequest(requesturl, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (null != response) {
                    listerner.onResponse(response.toString(), action, null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (!TextUtils.isEmpty(error.getMessage())) {
                    listerner.onResponseError(error.getMessage(),
                            action);
                }
            }
        }, params));
    }

    /**
     * @param listerner
     * @param action
     * @param params    参数
     *                  完善资料 lvfl
     */
    public void getCompletePost(final VolleyListerner listerner, final int action, final Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        sb.append(AppConfig.BASE_URL);
        sb.append(AppConfig.COMPLETE_DATA);
        String requesturl = sb.toString();

        L.e("URL=" + requesturl);
        VolleyTask.getInstance(MApplication.getApp()).addRequest(new NormalPostRequest(requesturl, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (null != response) {
                    listerner.onResponse(response.toString(), action, null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (!TextUtils.isEmpty(error.getMessage())) {
                    listerner.onResponseError(error.getMessage(),
                            action);
                }
            }
        }, params));
    }

    /**
     * @param listerner
     * @param action
     * @param params    参数
     *                  获取个人信息 lvfl
     */
    public void getInformationPost(final VolleyListerner listerner, final int action, final Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        sb.append(AppConfig.GET_INFORMATION);
        String requesturl = sb.toString();

        L.e("URL=" + requesturl);
        VolleyTask.getInstance(MApplication.getApp()).addRequest(new NormalPostRequest(requesturl, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (null != response) {
                    listerner.onResponse(response.toString(), action, null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (!TextUtils.isEmpty(error.getMessage())) {
                    listerner.onResponseError(error.getMessage(),
                            action);
                }
            }
        }, params));
    }

    /**
     * @param listerner
     * @param action
     * @param params    参数
     *                  第三方登录 lvfl
     */
    public void getOtherLoginPost(final VolleyListerner listerner, final int action, final Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        sb.append(AppConfig.QQ_OTHER_LOGIN);
        String requesturl = sb.toString();

        L.e("URL=" + requesturl);
        VolleyTask.getInstance(MApplication.getApp()).addRequest(new NormalPostRequest(requesturl, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (null != response) {
                    listerner.onResponse(response.toString(), action, null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (!TextUtils.isEmpty(error.getMessage())) {
                    listerner.onResponseError(error.getMessage(),
                            action);
                }
            }
        }, params));
    }

}
