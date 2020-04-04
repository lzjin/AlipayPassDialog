# ViewPagerGallery

#### 博客讲解地址,欢迎前往查看  
[博客讲解地址](https://blog.csdn.net/lin857/article/details/84111930)  

### 欢迎大家Star,老铁给鼓励呗  

### 效果图如下:  
<img  width="300" height="500"  src="https://raw.githubusercontent.com/lzjin/AlipayPassDialog/master/imgfolder/ic_preview.png">  

### 主要功能  
* 支持自定义文本、颜色、大小 
* 支持自定义关闭图标 
* 支持弹框样式 
* 支持回调函数处理 

### API方法介绍  
* setCloseImgView(int resId) //设置关闭图标 
* setHintText(String text)//设置提醒标题的文本  
* setForgetText(String text) //设置忘记密码文字 
* PayPassDialog(Context context, int themeResId)//自定义弹框样式,有默认样式 
* setPayClickListener(OnPayClickListener listener) //设置回调 
* onPassFinish(String passContent)//6位输入完成回调 
* onPayClose()//关闭回调 
* onPayForget()//忘记密码回调 

### Usage Jitpack 
---
Step 1. Add it in your root build.gradle at the end of repositories:
```
allprojects {
    repositories {
	 ...
	 maven { url 'https://jitpack.io' }
    }
}
```
#### Gradle:
Step 2. Add the dependency
```
dependencies {
    //androidX版本
    implementation 'com.github.lzjin:AlipayPassDialog:2.1' 

    //Support  版本
    implementation 'com.github.lzjin:AlipayPassDialog:2.0'
}
```

## 历史版本最底部
Demo
--
```
    //1 默认方式(推荐)
    private void payDialog() {
        final PayPassDialog dialog=new PayPassDialog(this);
          dialog.getPayViewPass()
                .setPayClickListener(new PayPassView.OnPayClickListener() {
                    @Override
                    public void onPassFinish(String passContent) {
                        //6位输入完成回调
                    }
                    @Override
                    public void onPayClose() {
                        dialog.dismiss();
                        //关闭弹框
                    }
                    @Override
                    public void onPayForget() {
                        dialog.dismiss();
                        //点击忘记密码回调
                    }
                });
    }
```  

```
    //2 自定义方式
    private void payDialog2() {
        final PayPassDialog dialog=new PayPassDialog(this,R.style.dialog_pay_theme);
        //弹框自定义配置
        dialog.setAlertDialog(false)
                .setWindowSize(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,0.4f)
                .setOutColse(false)
                .setGravity(R.style.dialogOpenAnimation, Gravity.BOTTOM);
        //组合控件自定义配置
        PayPassView payView=dialog.getPayViewPass();
        payView.setForgetText("忘记支付密码?");
        payView.setForgetColor(getResources().getColor(R.color.colorAccent));
        payView.setForgetSize(16);
        payView.setPayClickListener(new PayPassView.OnPayClickListener() {
            @Override
            public void onPassFinish(String passContent) {
                //6位输入完成回调
                showShort("输入完成回调");
            }
            @Override
            public void onPayClose() {
                dialog.dismiss();
                //关闭回调
            }
            @Override
            public void onPayForget() {
                dialog.dismiss();
                //忘记密码回调
                showShort("忘记密码回调");
            }
        });
    }
```
 #### v2.1
* AndroidX 版本

 #### v2.0
* 优化界面适配问题
* 增加Demo演示代码
  
#### v1.1
* 优化已知
* 修复部分手机兼容问题
* 增加功能
 
#### v1.0
* 基础使用
