package com.lzj.pass;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lzj.pass.dialog.PayPassDialog;
import com.lzj.pass.dialog.PayPassView;

/**
 *  参考Demo
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //点击测试
        Button bt=findViewById(R.id.bt_test);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payDialog();
            }
        });
        //点击测试
        Button bt2=findViewById(R.id.bt_test2);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payDialog2();
            }
        });
        //点击测试
        Button bt3=findViewById(R.id.bt_test3);
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payDialog3();
            }
        });
    }

    /**
     * 1 默认方式
     */
    private void payDialog() {
        final PayPassDialog dialog=new PayPassDialog(this);
                dialog.getPayViewPass()
                .setPayClickListener(new PayPassView.OnPayClickListener() {
                    @Override
                    public void onPassFinish(String password) {
                        //6位输入完成回调
                        showShort("输入内容:"+password);
                    }
                    @Override
                    public void onPayClose() {
                        dialog.dismiss();
                        //关闭回调
                    }
                    @Override
                    public void onPayForget() {
                        dialog.dismiss();
                        //点击忘记密码回调
                        showShort("忘记密码");
                    }
                });
    }

    /**
     * 2 随机数
     */
    private void payDialog2() {
        final PayPassDialog dialog=new PayPassDialog(this);
          dialog.getPayViewPass()
                .setRandomNumber(true)
                .setPayClickListener(new PayPassView.OnPayClickListener() {
                    @Override
                    public void onPassFinish(String password) {
                        //6位输入完成回调
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Log.i("test","--------------ssss");
                                showShort("输入内容:"+password);
                            }
                        }, 500);  //
                    }
                    @Override
                    public void onPayClose() {
                        dialog.dismiss();
                        //关闭回调
                    }
                    @Override
                    public void onPayForget() {
                        dialog.dismiss();
                        //点击忘记密码回调
                        showShort("忘记密码");
                    }
                });
    }
    /**
     *  3 自定义方式
     */
    private void payDialog3() {
        final PayPassDialog dialog=new PayPassDialog(this,R.style.dialog_pay_theme);
        //弹框自定义配置
        dialog.setAlertDialog(false)
                .setWindowSize(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,0.4f)
                .setOutColse(false) //点击外部是否消失
                .setGravity(R.style.dialogOpenAnimation, Gravity.BOTTOM);
        //组合控件自定义配置
         dialog.getPayViewPass()
        .setHintText("自定义样式文本")
        .setForgetText("密码忘记?")
        .setForgetColor(getResources().getColor(R.color.colorAccent))
        .setForgetSize(16)
        .setPayClickListener(new PayPassView.OnPayClickListener() {
            @Override
            public void onPassFinish(String password) {
                //6位输入完成回调
                showShort("输入内容:"+password);
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
                showShort("忘记密码");
            }
        });
    }




    public  void showShort(String text){
        Toast toast  = Toast.makeText(this,text, Toast.LENGTH_SHORT);
              toast.setText(text);
              toast.show();
    }

}
