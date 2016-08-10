package com.nightfarmer.luabridge.sample.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.HashMap;

/**
 * Created by zhangfan on 16-8-10.
 */
public class LuaViewHolder extends RecyclerView.ViewHolder {

    private HashMap<String, View> viewMap = new HashMap<>();

    public void putView(String name, View view){
        viewMap.put(name, view);
    }

    public View getView(String name){
        return viewMap.get(name);
    }

    public LuaViewHolder(View itemView) {
        super(itemView);
    }
}
