package com.nightfarmer.luabridge.sample;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.keplerproject.luajava.LuaBridge;
import org.keplerproject.luajava.LuaState;
import org.keplerproject.luajava.LuaStateFactory;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class TestActivity extends AppCompatActivity {

    private LuaState mLuaState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mLuaState = LuaStateFactory.newLuaState();
        mLuaState.openLibs();
        mLuaState.LdoString(LuaBridge.loadRaw(this, R.raw.test_activity));

        init(savedInstanceState);
    }

    public void init(Bundle savedInstanceState) {
        mLuaState.getField(LuaState.LUA_GLOBALSINDEX, "onCreate");
        mLuaState.pushJavaObject(this);
        mLuaState.pushJavaObject(savedInstanceState);
        mLuaState.call(2, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLuaState.getField(LuaState.LUA_GLOBALSINDEX, "onResume");
        mLuaState.pushJavaObject(this);
        mLuaState.call(1, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mLuaState.getField(LuaState.LUA_GLOBALSINDEX, "onActivityResult");
        mLuaState.pushJavaObject(this);
        mLuaState.pushNumber(requestCode);
        mLuaState.pushNumber(resultCode);
        mLuaState.pushJavaObject(data);
        mLuaState.call(4, 0);
    }

}
