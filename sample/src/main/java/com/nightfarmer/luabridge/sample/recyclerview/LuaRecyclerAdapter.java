package com.nightfarmer.luabridge.sample.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by zhangfan on 16-8-10.
 */
public interface LuaRecyclerAdapter {
    RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    void onBindViewHolder(RecyclerView.ViewHolder holder, int position);

    int getItemCount();
}
