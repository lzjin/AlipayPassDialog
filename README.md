# ViewPagerGallery
不懂看博客、不懂看博客、不懂看博客
<p>[博客讲解地址](https://blog.csdn.net/lin857/article/details/84111930)
<h3>特点功能:</h3>
<h6>支持自定义文本、颜色、大小</h6>
<h6>支持自定义关闭图标</h6>
<h6>支持弹框样式</h6>
<h6>支持回调函数处理</h6>
<h3>API方法介绍:</h3>
<h6>setCloseImgView(int resId) //设置关闭图标 </h6>
<h6>setHintText(String text)//设置提醒标题的文本 </h6>
<h6>setForgetText(String text) //设置忘记密码文字</h6>
<h6>PayPassDialog(Context context, int themeResId)//自定义弹框样式,有默认样式</h6>
<h6>setPayClickListener(OnPayClickListener listener) //设置回调</h6>
<h6>onPassFinish(String passContent)//6位输入完成回调</h6>
<h6>onPayClose()//关闭回调</h6>
<h6>onPayForget()//忘记密码回调</h6>

<h2>使用步骤</h2>
1、gradle引入
<h3> implementation 'com.github.lzjin:AlipayPassDialog:1.1' </h3>
<h4>2、使用参考:</h4>
<p>方式一：默认系统配置
<p><img src="https://raw.githubusercontent.com/lzjin/AlipayPassDialog/master/imgfolder/ic_code1.png">
<p>方式二：自定义配置:
<p><img src="https://raw.githubusercontent.com/lzjin/AlipayPassDialog/master/imgfolder/ic_code2.png">
<h2>效果图:</h2>
<p><img src="https://raw.githubusercontent.com/lzjin/AlipayPassDialog/master/imgfolder/ic_preview.png">
