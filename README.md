# Housekeeping

###2016/4/15  by Ariel

####将初始工程提交到了GitHub

####部分页面布局文件over，主体框架完成

###2016/4/16  by Ariel

####在`RegisterActivity`中添加了短信验证功能，初步验证成功

###2016/4/17  by Ariel

####新建了`RequestService.java`，实现使用post方法向服务器传数据

####在`RegisterActivity`中使用post方法将注册的账号密码传递给服务器，
并从服务器中返回注册是否成功的结果

####添加基本验证，以及校验

####待解决：result是上一次的返回结果

###2016/4/18  by Ariel

####解决了result是上一次的返回结果，原因是网络延迟，
因此新建了子线程，在子线程中请求网络数据，并返回给handler

####解决了线程之间的不协调，并且调用了验证，解决了短信验证和请求服务器之间的线程顺序

###2016/4/19  by Ariel

####添加了`progressDialog`进度框，以及返回键取消进度框
