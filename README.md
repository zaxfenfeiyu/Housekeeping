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

###2016/4/23 by Ariel

在LoginActivity中添加了progressDialog，在MyFragment中添加了显示用户名，并且当用户登录时登录按钮取消。添加了DBManager.java。在LoginActivity中，登录成功后将数据添加到本地数据库中

###2016/4/23 by Disagree

修改了PlaceOrderActivity.java和activity_order_yuesao.xml，添加了rightarrow.png图片

###2016/4/25 by Disagree

完善了PlaceOrderActivity.java和activity_order_muying.xml，将Servicecatalog中price改为double类型（因为Object没有getFloat方法）