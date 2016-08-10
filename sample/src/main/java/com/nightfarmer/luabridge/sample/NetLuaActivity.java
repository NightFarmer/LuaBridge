package com.nightfarmer.luabridge.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nightfarmer.luabridge.sample.net.FileDownLoadHandler;
import com.nightfarmer.luabridge.sample.net.HttpCall;
import com.nightfarmer.luabridge.sample.net.SampleNetLuaCallback;

import org.keplerproject.luajava.LuaBridge;
import org.keplerproject.luajava.LuaException;
import org.keplerproject.luajava.LuaState;
import org.keplerproject.luajava.LuaStateFactory;

import java.io.File;

public class NetLuaActivity extends AppCompatActivity {

    private LuaState luaState;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_lua);

        File sdPath = getFilesDir();
        File file = new File(sdPath, "NetLuaActivity.lua");
        file.delete();
        if (!file.exists()) {
            HttpCall.downLoad("https://github.com/NightFarmer/LuaBridge/raw/master/sample/src/main/assets/NetLuaActivity.lua", file, new SampleNetLuaCallback() {
//            HttpCall.downLoad("http://www.97gyl.com/download/NetLuaActivity.lua", file, new SampleNetLuaCallback() {
                @Override
                public void onSuccess(File file) {
                    super.onSuccess(file);
                    Toast.makeText(NetLuaActivity.this, "lua文件下载完成", Toast.LENGTH_SHORT).show();
                    onCreateForLua(file, savedInstanceState);
                }

                @Override
                public void onFailure(String msg, int errorCode) {
                    super.onFailure(msg, errorCode);
                }
            });
        } else {
            Toast.makeText(NetLuaActivity.this, "lua文件已存在", Toast.LENGTH_SHORT).show();
            onCreateForLua(file, savedInstanceState);
        }
    }

    private void onCreateForLua(File file, Bundle savedInstanceState) {
        String fileStr = LuaBridge.loadFile(this, file);
        System.err.print(fileStr);
        luaState = LuaStateFactory.newLuaState();
        luaState.openLibs();
        luaState.LdoString(fileStr);

        luaState.getField(LuaState.LUA_GLOBALSINDEX, "onCreate");
        luaState.pushJavaObject(this);
        luaState.pushJavaObject(((ViewGroup) findViewById(android.R.id.content)).getChildAt(0));
        luaState.pushJavaObject(savedInstanceState);
        luaState.call(3, 0);

    }


}
