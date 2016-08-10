package com.nightfarmer.luabridge.sample;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.keplerproject.luajava.LuaException;
import org.keplerproject.luajava.LuaState;
import org.keplerproject.luajava.LuaStateFactory;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
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


//        addListener();
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

        addListener();
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
//        mLuaState.pushInteger(Color.RED);
        mLuaState.call(3, 1);

        // 将结果保存到resultKey中
        mLuaState.setField(LuaState.LUA_GLOBALSINDEX, "resultTV");
        // 获取
        mLuaState.getGlobal("resultTV");
        // 输出
//        mDisplay.setText(mLuaState.toString(-1));
        try {
            TextView tv = (TextView) mLuaState.toJavaObject(-1);
            this.tv = tv;
            tv.setTextColor(Color.RED);
        } catch (LuaException e) {
            e.printStackTrace();
        }
    }

    TextView tv;

    public void textString(View v) {
        if (tv == null) return;
        String str = readStream(getResources().openRawResource(R.raw.test));
        mLuaState.LdoString(str);
        // 找到functionInLuaFile函数
        mLuaState.getField(LuaState.LUA_GLOBALSINDEX, "textString");
//        mLuaState.pushString("设置到TextView的数据");
//        mLuaState.pushNumber(Color.BLUE);
        mLuaState.pushNumber(-16776961);
        mLuaState.pushJavaObject(tv);
        mLuaState.pushJavaObject(this);
//        mLuaState.pushInteger(2);
//        mLuaState.pushInteger(Color.RED);
        mLuaState.call(3, 1);

        // 将结果保存到resultKey中
        mLuaState.setField(LuaState.LUA_GLOBALSINDEX, "resultStr");
        // 获取
        mLuaState.getGlobal("resultStr");
        // 输出
        try {
            Object o = mLuaState.toJavaObject(-1);
            Log.i("xxx", "" + o);
        } catch (LuaException e) {
            e.printStackTrace();
        }
//        Log.i("xx", "" + mLuaState.toNumber(-1));
    }

    public void startTestActivity(View v) {
        String str = readStream(getResources().openRawResource(R.raw.test));
        mLuaState.LdoString(str);
        // 找到functionInLuaFile函数
        mLuaState.getField(LuaState.LUA_GLOBALSINDEX, "startTestActivity");
        mLuaState.pushJavaObject(this);
        mLuaState.call(1, 0);

    }

    public void addListener() {
        String str = readStream(getResources().openRawResource(R.raw.test));
        mLuaState.LdoString(str);
        // 找到functionInLuaFile函数
        mLuaState.getField(LuaState.LUA_GLOBALSINDEX, "addListener");
        mLuaState.pushJavaObject(this);
        mLuaState.call(1, 0);


    }

    public void getLuaByNet(View v) {
        startActivity(new Intent(this, NetLuaActivity.class));
    }


    private String readStream(InputStream is) {
        try {
            int len;
            byte[] b = new byte[1024];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((len = is.read(b)) != -1) {
                baos.write(b, 0, len);
            }
            return baos.toString("UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ReadStream", "读取文件流失败");
            return "";
        }
//        try {
//            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
//            ByteArrayOutputStream bo = new ByteArrayOutputStream();
//            int i = isr.read();
//            while (i != -1) {
//                bo.write(i);
//                i = isr.read();
//            }
//            String FileContent = ""; // 文件很长的话建议使用StringBuffer
//            StringBuilder stringBuffer = new StringBuilder();
//            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
//            BufferedReader br = new BufferedReader(isr);
//            String line;
//            while ((line = br.readLine()) != null) {
//                stringBuffer.append(line);
//                stringBuffer.append("\r\n");
//            }
//            return stringBuffer.toString();
//            return bo.toString("utf-8");
//            byte[] lens = bo.toByteArray();
//            return new String(lens);//result结果显示正常：含中文无乱码
//        } catch (IOException e) {
//            Log.e("ReadStream", "读取文件流失败");
//            return "";
//        }

    }

}