package com.nightfarmer.luabridge.sample.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by zhangfan on 16-8-10.
 */
public class LuaRecyclerView extends RecyclerView {
    public LuaRecyclerView(Context context) {
        super(context);
    }

    public LuaRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LuaRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setAdapter(LuaRecyclerAdapter adapter) {
        super.setAdapter(new LuaAdapterWrapper(adapter));
    }
}
