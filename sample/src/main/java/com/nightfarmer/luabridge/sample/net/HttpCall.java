package com.nightfarmer.luabridge.sample.net;

import java.io.File;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by zhangfan on 16-8-10.
 */
public class HttpCall {
    private static final OkHttpClient okClick = new OkHttpClient();

    public static void downLoad(String url, File file, NetLuaCallback callback) {
        Request request = new Request.Builder().url(url).build();
        FileDownLoadHandler responseCallback = new FileDownLoadHandler(file);
        responseCallback.setNetLuaCallback(callback);
        okClick.newCall(request).enqueue(responseCallback);
    }
}


//private val okclient = OkHttpClient()
//
//    /**
//     * 文件下载
//     */
//    fun downLoad(url: String, callback: Callback) {
//        val request = Request.Builder()
//                .url(url)
//                .build();
//
//        okclient.newCall(request).enqueue(callback);
//    }