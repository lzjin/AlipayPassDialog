package com.lzj.pass;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    }

    /**
     * 1 默认方式
     */
    private void payDialog() {
        final PayPassDialog dialog=new PayPassDialog(this);
          dialog.getPayViewPass()
                .setPayClickListener(new PayPassView.OnPayClickListener() {
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
                        //点击忘记密码回调
                        showShort("忘记密码回调");
                    }
                });
    }
    /**
     *  2 自定义方式
     */
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




    public  void showShort(String text){
        Toast toast  = Toast.makeText(this,text, Toast.LENGTH_SHORT);
              toast.setText(text);
              toast.show();
    }

}
