package com.nightfarmer.luabridge.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import org.keplerproject.luajava.LuaState;
import org.keplerproject.luajava.LuaStateFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MainActivity extends AppCompatActivity {

    private LuaState mLuaState;

    private ViewGroup mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLayout = (ViewGroup) findViewById(R.id.layout);
        mLuaState = LuaStateFactory.newLuaState();
        mLuaState.openLibs();
    }

    public void runStatement(View v) {
        // 定义一个Lua变量
        mLuaState.LdoString(" varSay = 'This is string in lua script statement.'");
        // 获取
        mLuaState.getGlobal("varSay");
        // 输出
//        mDisplay.setText(mLuaState.toString(-1));
        String s = mLuaState.toString(-1);
        Log.i("xx", "" + s);
    }

    public void runFile(View v) {
        mLuaState.LdoString(readStream(getResources().openRawResource(R.raw.test)));
        // 找到functionInLuaFile函数
        mLuaState.getField(LuaState.LUA_GLOBALSINDEX, "functionInLuaFile");
        // 将参数压入栈
        mLuaState.pushString("从Java中传递的参数");
        // functionInLuaFile函数有一个参数，一个返回结果
        int paramCount = 1;
        int resultCount = 1;
        mLuaState.call(paramCount, resultCount);
        // 将结果保存到resultKey中
        mLuaState.setField(LuaState.LUA_GLOBALSINDEX, "resultKey");
        // 获取
        mLuaState.getGlobal("resultKey");
        // 输出
//        mDisplay.setText(mLuaState.toString(-1));
        Log.i("xxx", "" + mLuaState.toString(-1));
    }


    public void callAndroidAPI(View v) {
        mLuaState.LdoString(readStream(getResources().openRawResource(R.raw.test)));
        // 找到functionInLuaFile函数
        mLuaState.getField(LuaState.LUA_GLOBALSINDEX, "callAndroidApi");
        mLuaState.pushJavaObject(getApplicationContext());
        mLuaState.pushJavaObject(mLayout);
        mLuaState.pushString("设置到TextView的数据");
        mLuaState.call(3, 0);
    }


    private String readStream(InputStream is) {
        try {
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = isr.read();
            while (i != -1) {
                bo.write(i);
                i = isr.read();
            }
            return bo.toString("utf-8");
        } catch (IOException e) {
            Log.e("ReadStream", "读取文件流失败");
            return "";
        }
    }

}