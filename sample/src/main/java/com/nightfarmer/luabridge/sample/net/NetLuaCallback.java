package com.nightfarmer.luabridge.sample.net;

import java.io.File;

/**
 * Created by zhangfan on 16-8-10.
 */
public interface NetLuaCallback {

    void onSuccess(File file);

    void onFinish();

    void onFailure(String msg, int errorCode);

    void onProcess(long process, long length);
}
