package com.nightfarmer.luabridge.sample.recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

import com.nightfarmer.luabridge.sample.R;

import org.keplerproject.luajava.LuaBridge;
import org.keplerproject.luajava.LuaState;
import org.keplerproject.luajava.LuaStateFactory;

public class RecyclerViewActivity extends AppCompatActivity {

    private LuaState luaState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        luaState = LuaStateFactory.newLuaState();
        luaState.openLibs();
        luaState.LdoString(LuaBridge.loadAssets(this, "RecyclerViewActivity.lua"));

        luaState.getField(LuaState.LUA_GLOBALSINDEX, "onCreate");
        luaState.pushJavaObject(this);
        luaState.pushJavaObject(((ViewGroup) findViewById(android.R.id.content)).getChildAt(0));
        luaState.pushJavaObject(savedInstanceState);
        luaState.call(3, 0);


    }
}
