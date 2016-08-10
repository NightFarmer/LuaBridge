function onCreate(activity, rootView)
	activity:setTitle("使用Lua脚本构建RecyclerView")
	context = activity
        local recyclerView = luajava.newInstance("com.nightfarmer.luabridge.sample.recyclerview.LuaRecyclerView", activity)
	local linearManager = luajava.newInstance("android.support.v7.widget.LinearLayoutManager", activity)
	recyclerView:setLayoutManager(linearManager)
	rootView:addView(recyclerView)
	
	local adapter = luajava.createProxy("com.nightfarmer.luabridge.sample.recyclerview.LuaRecyclerAdapter",rec_adapter)
	recyclerView:setAdapter(adapter)
end

rec_adapter={}
function rec_adapter.onCreateViewHolder(parent, viewType)
	local button1 =	luajava.newInstance("android.widget.TextView",context)
	button1:setPadding(50,50,50,50)
	button1:setText("根据网络Lua脚本动态生成的button.....一可赛艇")
	parent:addView(button1)
	local holder = luajava.newInstance("com.nightfarmer.luabridge.sample.recyclerview.LuaViewHolder", button1)
	holder:putView("btn1", button1)
	return holder
end

function rec_adapter.onBindViewHolder(holder, position)
	holder:getView("btn1"):setText("yoo"..position)
end

function rec_adapter.getItemCount()
 	return 1000
end
