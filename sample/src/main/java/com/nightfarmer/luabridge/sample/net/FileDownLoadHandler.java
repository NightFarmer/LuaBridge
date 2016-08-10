package com.nightfarmer.luabridge.sample.net;

import android.os.Handler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by zhangfan on 16-8-10.
 */
public class FileDownLoadHandler implements Callback {
    NetLuaCallback netLuaCallback;

    public void setNetLuaCallback(NetLuaCallback netLuaCallback) {
        this.netLuaCallback = netLuaCallback;
    }

    private File file;

    public FileDownLoadHandler(File file) {
        this.file = file;
    }

    private static final HashMap<Integer, String> errorMap = new HashMap<>();

    static {
        errorMap.put(0, "连接失败，请重试");
        errorMap.put(1, "连接失败，请重试");
        errorMap.put(2, "下载失败，手机空间不足");
        errorMap.put(3, "下载失败，请重试");
    }

    private Handler handler = new Handler();


    @Override
    public void onFailure(Call call, IOException e) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                callFailure(1, "");
            }
        });
    }

    @Override
    public void onResponse(Call call, Response response) {
        if (!response.isSuccessful()) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    callFailure(0, "");
                }
            });
            return;
        }
        InputStream byteStream = response.body().byteStream();
        final long length = response.body().contentLength();
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    callFailure(2, "");
                }
            });
            return;
        }
        int bytesRead = -1;
        byte[] buffer = new byte[1024];
        long process = 0;
        try {
            do {
                bytesRead = byteStream.read(buffer);
                if (bytesRead == -1) break;
                process += bytesRead;
                outputStream.write(buffer, 0, bytesRead);
                final long finalProcess = process;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        netLuaCallback.onProcess(finalProcess, length);
                    }
                });
            } while (true);
        } catch (IOException e) {
            e.printStackTrace();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    callFailure(3, "");
                }
            });
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                netLuaCallback.onSuccess(file);
                netLuaCallback.onFinish();
            }
        });
    }

    private void callFailure(int errorCode, String msg) {
        netLuaCallback.onFailure(errorMap.get(errorCode), errorCode);
        netLuaCallback.onFinish();
    }

}
