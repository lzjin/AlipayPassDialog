package com.lzj.pass;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lzj.pass.dialog.PayPassDialog;
import com.lzj.pass.dialog.PayPassView;

/**
 * 参考Demo
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //默认
        findViewById(R.id.bt_test1).setOnClickListener(v -> payDialog1());
        //随机密码
        findViewById(R.id.bt_test2).setOnClickListener(v -> payDialog2());
        //自定义标题，字体颜色
        findViewById(R.id.bt_test3).setOnClickListener(v -> payDialog3());
        //4位数密码
        findViewById(R.id.bt_test4).setOnClickListener(v -> payDialog4());
        //悬浮窗
        findViewById(R.id.bt_test5).setOnClickListener(v -> payDialog5());
    }

    /**
     * 1 默认方式
     */
    private void payDialog1() {
        final PayPassDialog dialog = new PayPassDialog(this);
        dialog.getPayViewPass()
                .setPayClickListener(new PayPassView.OnPayClickListener() {
                    @Override
                    public void onPassFinish(String password) {
                        //6位输入完成回调
                        show("输入内容:" + password);
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
                        show("忘记密码");
                    }
                });
    }

    /**
     * 2 随机数
     */
    private void payDialog2() {
        final PayPassDialog dialog = new PayPassDialog(this);
        dialog.getPayViewPass()
                .setRandomNumber(true)
                .setPayClickListener(new PayPassView.OnPayClickListener() {
                    @Override
                    public void onPassFinish(String password) {
                        //6位输入完成回调
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Log.i("test", "--------------ssss");
                                show("输入内容:" + password);
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
                        show("忘记密码");
                    }
                });
    }

    /**
     * 3 自定义方式
     */
    private void payDialog3() {
        final PayPassDialog dialog = new PayPassDialog(this, R.style.dialog_pay_theme);
        //弹框自定义配置
        dialog.setAlertDialog(false)
                .setWindowSize(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.4f)
                .setOutClose(false) //点击外部是否消失
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
                        show("输入内容:" + password);
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
                        show("忘记密码");
                    }
                });
    }

    private void payDialog4() {
        final PayPassDialog dialog = new PayPassDialog(this);
        dialog.getPayViewPass()
                .setPassWordLength(4)
                .setPayClickListener(new PayPassView.OnPayClickListener() {
                    @Override
                    public void onPassFinish(String password) {
                        //6位输入完成回调
                        show("输入内容:" + password);
                        dialog.dismiss();
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
                        show("忘记密码");
                    }
                });
    }

    private void payDialog5() {
        final PayPassDialog dialog = new PayPassDialog(this, R.style.dialog_pay_theme);
        //弹框自定义配置
        dialog.setAlertDialog(false)
                .setWindowSize((int)(getScreenWidth(this)*0.8F), LinearLayout.LayoutParams.WRAP_CONTENT, 0.4f)
                .setOutClose(false) //点击外部是否消失
                .setGravity(R.style.dialogOpenAnimation, Gravity.BOTTOM);
        //组合控件自定义配置
        dialog.getPayViewPass()
                .setHintText("自定义样式文本")
                .setForgetText("密码忘记?")
                .setPassWordLength(4)
                .setForgetColor(getResources().getColor(R.color.colorAccent))
                .setForgetSize(16)
                .setPayClickListener(new PayPassView.OnPayClickListener() {
                    @Override
                    public void onPassFinish(String password) {
                        //6位输入完成回调
                        show("输入内容:" + password);
                        dialog.dismiss();
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
                        show("忘记密码");
                    }
                });
    }

    public void show(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public static int getScreenWidth(Context context) {
        return getDisplayMetrics(context).widthPixels;
    }
    public static int getScreenHeight(Context context) {
        return getDisplayMetrics(context).heightPixels;
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        if (manager != null)
            manager.getDefaultDisplay().getRealMetrics(metrics);//manager.getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }
}
