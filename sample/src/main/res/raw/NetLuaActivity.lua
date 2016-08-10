
function onCreate(activity, rootView)
	activity:setTitle("由网络Lua脚本完成布局")
	context = activity

	contentView = luajava.newInstance("android.widget.LinearLayout",activity)
	rootView:addView(contentView)
	contentView:setOrientation(1)
	contentView:setPadding(50,50,50,50)

	local button1 =	luajava.newInstance("android.widget.Button",activity)
	button1:setText("根据网络Lua脚本动态生成的button.....一可赛艇")
	contentView:addView(button1)

	local button2 =	luajava.newInstance("android.widget.Button",activity)
	button2:setText("又追加了个button")
	contentView:addView(button2)

	local bt1ClickListener = luajava.createProxy("android.view.View$OnClickListener", button1_cb)
	button1:setOnClickListener(bt1ClickListener)

	return contentView
end



button1_cb={}
function button1_cb.onClick(view)
 	local button1 =	luajava.newInstance("android.widget.Button",view:getContext())
 	button1:setText("请求网络顺便生成一个button")
 	contentView:addView(button1)

  	local HttpCall = luajava.bindClass("com.nightfarmer.luabridge.sample.net.HttpCall")
 	local downLoadCallBack = luajava.createProxy("com.nightfarmer.luabridge.sample.net.NetLuaCallback", dl_cb)

	local path = view:getContext():getFilesDir()
	local file = luajava.newInstance("java.io.File", path, "hehehe")

	HttpCall:downLoad("https://github.com/NightFarmer/LuaBridge/raw/master/sample/src/main/res/raw/NetLuaActivity.lua", file, downLoadCallBack)

end

dl_cb={}
function dl_cb.onSuccess(file)
	Toast = luajava.bindClass("android.widget.Toast")
	Toast:makeText(context,"网络请求成功", 0):show()
end

function dl_cb.onFailure(msg, errcode)
	Toast = luajava.bindClass("android.widget.Toast")
	Toast:makeText(context,""..errcode..":"..msg,0):show()
end
