package com.shiliuke.BabyLink.chooseimage.upload;

import com.shiliuke.utils.L;
import com.shiliuke.view.stickerview.StickerExecutor;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * 上传工具类
 *
 * @author spring sky<br>
 *         Email :vipa1888@163.com<br>
 *         QQ: 840950105<br>
 *         支持上传文件和参数
 */
public class UploadUtil {
    private static UploadUtil uploadUtil;

    private UploadUtil() {

    }

    /**
     * 单例模式获取上传工具类
     *
     * @return
     */
    public static UploadUtil getInstance() {
        if (null == uploadUtil) {
            uploadUtil = new UploadUtil();
        }
        return uploadUtil;
    }

    private static final String TAG = "UploadUtil";
    private int readTimeOut = 10 * 1000; // 读取超时
    private int connectTimeout = 10 * 1000; // 超时时间
    /**
     * 请求使用多长时间
     */
    private static int requestTime = 0;

    private static final String CHARSET = "utf-8"; // 设置编码

    /**
     * 上传成功
     */
    public static final int UPLOAD_SUCCESS_CODE = 1;
    /**
     * 文件不存在
     */
    public static final int UPLOAD_FILE_NOT_EXISTS_CODE = 2;
    /**
     * 服务器出错
     */
    public static final int UPLOAD_SERVER_ERROR_CODE = 3;
    protected static final int WHAT_TO_UPLOAD = 1;
    protected static final int WHAT_UPLOAD_DONE = 2;
    final String BOUNDARY = "---------------------------7da2137580612"; //数据分隔线
    final String endline = "--" + BOUNDARY + "--\r\n";//数据结束标志

    /**
     * android上传文件到服务器
     *
     * @param file       需要上传的文件
     * @param RequestURL 请求的URL
     */
    public void uploadFile(final ArrayList<File> file,
                           final String RequestURL) {
//        if (file == null || (!file.exists())) {
//            sendMessage(UPLOAD_FILE_NOT_EXISTS_CODE, "文件不存在");
//            return;
//        }
        StickerExecutor.getSingleExecutor().execute(new Runnable() {
            @Override
            public void run() {
                toUploadFile(file, RequestURL);
            }
        });
    }

    private void toUploadFile(ArrayList<File> files, String RequestURL) {
        String result = null;
        requestTime = 0;

        long requestTime = System.currentTimeMillis();
        long responseTime = 0;
        try {
            L.d("final geturl ==" + RequestURL);
            URL url = new URL(RequestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type", "multipart/form-data");//"application/x-www-form-urlencoded");
            // connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");//"application/x-www-form-urlencoded");
            connection.connect();
            // POST请求
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            //readFileContent("D:\\11.jpg");
            for (File file : files) {
                out.write(file2BetyArray(file));
                out.write("\r\n".getBytes());
            }
            out.write(endline.getBytes());
            out.flush();
            out.close();
            /**
             * 获取响应码 200=成功 当响应成功，获取响应的流
             */
            int res = connection.getResponseCode();
            responseTime = System.currentTimeMillis();
            this.requestTime = (int) ((responseTime - requestTime) / 1000);
            if (res == 200) {
                InputStream input = connection.getInputStream();
                byte[] data = new byte[1024];
                int a = 0;
                int b = 0;
                while ((b = input.read()) != -1) {
                    data[a] = (byte) b;
                    a++;
                }
                result = new String(data, 0, a, "UTF-8");
//				result = conn.getResponseMessage();
                sendMessage(UPLOAD_SUCCESS_CODE, result);
                return;
            } else {
                sendMessage(UPLOAD_SERVER_ERROR_CODE, "上传失败：code=" + res);
                return;
            }
        } catch (MalformedURLException e) {
            sendMessage(UPLOAD_SERVER_ERROR_CODE, "上传失败：error=" + e.getMessage());
            e.printStackTrace();
            return;
        } catch (IOException e) {
            sendMessage(UPLOAD_SERVER_ERROR_CODE, "上传失败：error=" + e.getMessage());
            e.printStackTrace();
            return;
        }
    }

    public static byte[] file2BetyArray(File file) {
        FileInputStream stream = null;
        ByteArrayOutputStream out = null;
        try {
            stream = new FileInputStream(file);
            out = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = stream.read(b)) != -1) {
                L.d("write");
                out.write(b, 0, n);
            }
            return out.toByteArray();// 此方法大文件OutOfMemory
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                stream.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    /**
     * 发送上传结果
     *
     * @param responseCode
     * @param responseMessage
     */
    private void sendMessage(int responseCode, String responseMessage) {
        onUploadProcessListener.onUploadDone(responseCode, responseMessage);
    }

    /**
     * 下面是一个自定义的回调函数，用到回调上传文件是否完成
     *
     * @author shimingzheng
     */
    public static interface OnUploadProcessListener {
        /**
         * 上传响应
         *
         * @param responseCode
         * @param message
         */
        void onUploadDone(int responseCode, String message);

        /**
         * 上传中
         *
         * @param uploadSize
         */
        void onUploadProcess(int uploadSize);

        /**
         * 准备上传
         *
         * @param fileSize
         */
        void initUpload(int fileSize);
    }

    private OnUploadProcessListener onUploadProcessListener;


    public void setOnUploadProcessListener(
            OnUploadProcessListener onUploadProcessListener) {
        this.onUploadProcessListener = onUploadProcessListener;
    }

    public int getReadTimeOut() {
        return readTimeOut;
    }

    public void setReadTimeOut(int readTimeOut) {
        this.readTimeOut = readTimeOut;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    /**
     * 获取上传使用的时间
     *
     * @return
     */
    public static int getRequestTime() {
        return requestTime;
    }

    public static interface uploadProcessListener {

    }


}
