package com.nightfarmer.luabridge.sample.net;

import java.io.File;

/**
 * Created by zhangfan on 16-8-10.
 */
public class SampleNetLuaCallback implements NetLuaCallback {

    public void onSuccess(File file){}

    public void onFinish() {
    }

    public void onFailure(String msg, int errorCode) {
    }

    public void onProcess(long process, long length) {
    }
}
