# Housekeeping

###2016/4/15  by Ariel

将初始工程提交到了GitHub

部分页面布局文件over，主体框架完成

###2016/4/16  by Ariel

在`RegisterActivity`中添加了短信验证功能，初步验证成功

###2016/4/17  by Ariel

新建了`RequestService.java`，实现使用post方法向服务器传数据

在`RegisterActivity`中使用post方法将注册的账号密码传递给服务器，
并从服务器中返回注册是否成功的结果

添加基本验证，以及校验

待解决：result是上一次的返回结果

###2016/4/18  by Ariel

解决了result是上一次的返回结果，原因是网络延迟，
因此新建了子线程，在子线程中请求网络数据，并返回给handler

解决了线程之间的不协调，并且调用了验证，解决了短信验证和请求服务器之间的线程顺序

###2016/4/19  by Ariel

添加了`progressDialog`进度框，以及返回键取消进度框

###2016/4/22 by Ariel

添加了常用地址界面，全部订单界面，完善个人信息页面，订单详情页面，月嫂下单页面，更多设置页面

添加了上述页面相应的activity

添加了个人中心页面各个按钮的跳转，以及跳转后返回键的监听

添加了订单中心——全部订单页面的跳转和返回

###2016/4/22 by Disagree

在LoginActivity中添加了progressDialog，在MyFragment中添加了显示用户名，并且当用户登录时登录按钮取消。添加了DBManager.java。在LoginActivity中，登录成功后将数据添加到本地数据库中

###2016/4/22 by Ariel

完成了下单功能

###2016/4/23 by Disagree

修改了PlaceOrderActivity.java和activity_order_yuesao.xml，添加了rightarrow.png图片

###2016/4/25 by Disagree

完善了PlaceOrderActivity.java和activity_order_muying.xml，将Servicecatalog中price改为double类型（因为Object没有getFloat方法）

###2016/4/26 by Ariel

完成了下单显示供应商列表，并且点击查看供应商详情，并且点击下单的功能

###2016/4/29 by Ariel

增加了主页返回键的监听，即按两次返回键才能退出程序

实现了订单的评价功能

###2016/4/30 by Ariel

修复了登录后等页面按返回键会跳转到未登录的页面的bug，即在每次跳转到主页的intent中添加 intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);语句即可

增加了主页8个功能按键的监听，完成了其跳转到下单页面

增加了订单页面的5个监听，并且按状态显示待评价和待确认两个按钮

完善了订单的评价功能，并且自定义了ratingBar的样式

实现了订单的确认功能


###2016/5/1 by Disagree

增加了全局变量的Id属性

修改了LoginActivity.java中handler判断登录成功后增加全局变量Data的Id属性。

修改了启动图片和logo

增加了下单、获取订单、获取个人信息时先判断用户是否登录

增加了登录后下单时服务地址自动获取的功能

修改了首页的瑕疵 增加了滚动条