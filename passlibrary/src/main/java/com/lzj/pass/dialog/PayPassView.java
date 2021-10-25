package com.lzj.pass.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2018/11/15.
 * 自定义支付密码组件
 */
public class PayPassView extends RelativeLayout {
    private Activity mContext;//上下文
    private GridView mGridView; //支付键盘
    private String strPass = "";//保存密码
    private List<TextView> mTvPass = new ArrayList<>();//密码数字控件
    private List<View> lines = new ArrayList<>();   //竖线
    private ImageView mImageViewClose;//关闭
    private TextView mTvForget;//忘记密码
    private TextView mTvHint;//提示 (提示:密码错误,重新输入)
    private List<Integer> listNumber;//1,2,3---0
    private View mPassLayout;//布局
    private int passWordLength = 6;//密码长度 1-9 位
    private boolean isRandom;

    /**
     * 按钮对外接口
     */
    public static interface OnPayClickListener {
        void onPassFinish(String password);

        void onPayClose();

        void onPayForget();
    }

    private OnPayClickListener mPayClickListener;

    public void setPayClickListener(OnPayClickListener listener) {
        mPayClickListener = listener;
    }

    public PayPassView(Context context) {
        super(context);
    }

    //在布局文件中使用的时候调用,多个样式文件
    public PayPassView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //在布局文件中使用的时候调用
    public PayPassView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = (Activity) context;
        initView();//初始化
        this.addView(mPassLayout); //将子布局添加到父容器,才显示控件
    }

    /**
     * 初始化
     */
    private void initView() {
        mPassLayout = LayoutInflater.from(mContext).inflate(R.layout.view_paypass_layout, null);
        mImageViewClose = mPassLayout.findViewById(R.id.iv_close);//关闭
        mTvForget = mPassLayout.findViewById(R.id.tv_forget);//忘记密码
        mTvHint = mPassLayout.findViewById(R.id.tv_passText);//提示文字
        //密码控件
        mTvPass.add(mPassLayout.findViewById(R.id.tv_pass1));
        mTvPass.add(mPassLayout.findViewById(R.id.tv_pass2));
        mTvPass.add(mPassLayout.findViewById(R.id.tv_pass3));
        mTvPass.add(mPassLayout.findViewById(R.id.tv_pass4));
        mTvPass.add(mPassLayout.findViewById(R.id.tv_pass5));
        mTvPass.add(mPassLayout.findViewById(R.id.tv_pass6));
        mTvPass.add(mPassLayout.findViewById(R.id.tv_pass7));
        mTvPass.add(mPassLayout.findViewById(R.id.tv_pass8));
        mTvPass.add(mPassLayout.findViewById(R.id.tv_pass9));
        //竖线
        lines.add(mPassLayout.findViewById(R.id.line_1));
        lines.add(mPassLayout.findViewById(R.id.line_2));
        lines.add(mPassLayout.findViewById(R.id.line_3));
        lines.add(mPassLayout.findViewById(R.id.line_4));
        lines.add(mPassLayout.findViewById(R.id.line_5));
        lines.add(mPassLayout.findViewById(R.id.line_6));
        lines.add(mPassLayout.findViewById(R.id.line_7));
        lines.add(mPassLayout.findViewById(R.id.line_8));
        lines.add(mPassLayout.findViewById(R.id.line_9));
        //键盘
        mGridView = mPassLayout.findViewById(R.id.gv_pass);
        mImageViewClose.setOnClickListener(v -> {
            cleanAllTv();
            mPayClickListener.onPayClose();
        });
        mTvForget.setOnClickListener(v -> mPayClickListener.onPayForget());

        //根据密码位数显示密码长度
        displayPasswordLength();

        //初始化数据
        initData();
    }

    /**
     * 根据密码位数显示密码长度
     */
    private void displayPasswordLength() {
        //密码长度不能大于9或者小于等于0
        if (passWordLength > 9 || passWordLength <= 0) passWordLength = 9;
        //显示要输入密码的位数
        for (int i = 0; i < 9; i++) {
            if (i < passWordLength) {
                mTvPass.get(i).setVisibility(View.VISIBLE);
                lines.get(i).setVisibility(View.VISIBLE);
            } else {
                mTvPass.get(i).setVisibility(View.GONE);
                lines.get(i).setVisibility(View.GONE);
            }
        }
    }

    /**
     * isRandom是否开启随机数
     */
    private void initData() {
        if (isRandom) {
            listNumber = new ArrayList<>();
            listNumber.clear();
            for (int i = 0; i <= 10; i++) {
                listNumber.add(i);
            }
            //此方法是打乱顺序
            Collections.shuffle(listNumber);
            for (int i = 0; i <= 10; i++) {
                if (listNumber.get(i) == 10) {
                    listNumber.remove(i);
                    listNumber.add(9, 10);
                }
            }
            listNumber.add(R.mipmap.ic_pay_del0);
        } else {
            listNumber = new ArrayList<>();
            listNumber.clear();
            for (int i = 1; i <= 9; i++) {
                listNumber.add(i);
            }
            listNumber.add(10);
            listNumber.add(0);
            listNumber.add(R.mipmap.ic_pay_del0);
        }
        mGridView.setAdapter(adapter);
    }


    /**
     * GridView的适配器
     */

    BaseAdapter adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return listNumber.size();
        }

        @Override
        public Object getItem(int position) {
            return listNumber.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.view_paypass_gridview_item, null);
                holder = new ViewHolder();
                holder.btnNumber = convertView.findViewById(R.id.btNumber);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            //-------------设置数据----------------
            holder.btnNumber.setText(listNumber.get(position) + "");
            if (position == 9) {
                holder.btnNumber.setText("");
                holder.btnNumber.setBackgroundColor(mContext.getResources().getColor(R.color.graye3));
            }
            if (position == 11) {
                holder.btnNumber.setText("");
                holder.btnNumber.setBackgroundResource(listNumber.get(position));
            }
            //监听事件----------------------------
            if (position == 11) {
                holder.btnNumber.setOnTouchListener((v, event) -> {
                    if (position == 11) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                holder.btnNumber.setBackgroundResource(R.mipmap.ic_pay_del1);
                                break;
                            case MotionEvent.ACTION_MOVE:
                                holder.btnNumber.setBackgroundResource(R.mipmap.ic_pay_del1);
                                break;
                            case MotionEvent.ACTION_UP:
                                holder.btnNumber.setBackgroundResource(R.mipmap.ic_pay_del0);
                                break;
                        }
                    }
                    return false;
                });
            }
            holder.btnNumber.setOnClickListener(v -> {
                if (position < 11 && position != 9) {//0-9按钮
                    if (strPass.length() == passWordLength) {
                        return;
                    } else {
                        strPass = strPass + listNumber.get(position);//得到当前数字并累加
                        mTvPass.get(strPass.length() - 1).setText("*"); //设置界面*
                        //输入完成
                        if (strPass.length() == passWordLength) {
                            mPayClickListener.onPassFinish(strPass);
                        }
                    }
                } else if (position == 11) {//删除
                    if (strPass.length() > 0) {
                        mTvPass.get(strPass.length() - 1).setText("");//去掉界面*
                        strPass = strPass.substring(0, strPass.length() - 1);//删除一位
                    }
                }
                if (position == 9) {//空按钮
                }
            });

            return convertView;
        }
    };

    static class ViewHolder {
        public TextView btnNumber;
    }

    /***
     * 设置随机数
     * @param israndom
     */
    public PayPassView setRandomNumber(boolean israndom) {
        isRandom = israndom;
        initData();
        adapter.notifyDataSetChanged();
        return this;
    }

    /**
     * 关闭图片
     * 资源方式
     */
    public PayPassView setCloseImgView(int resId) {
        mImageViewClose.setImageResource(resId);
        return this;
    }

    /**
     * 关闭图片
     * Bitmap方式
     */
    public PayPassView setCloseImgView(Bitmap bitmap) {
        mImageViewClose.setImageBitmap(bitmap);
        return this;
    }

    /**
     * 关闭图片
     * drawable方式
     */
    public PayPassView setCloseImgView(Drawable drawable) {
        mImageViewClose.setImageDrawable(drawable);
        return this;
    }


    /**
     * 设置忘记密码文字
     */
    public PayPassView setForgetText(String text) {
        mTvForget.setText(text);
        return this;
    }

    /**
     * 设置忘记密码文字大小
     */
    public PayPassView setForgetSize(float textSize) {
        mTvForget.setTextSize(textSize);
        return this;
    }

    /**
     * 设置忘记密码文字颜色
     */
    public PayPassView setForgetColor(int textColor) {
        mTvForget.setTextColor(textColor);
        return this;
    }

    /**
     * 设置提醒的文字
     */
    public PayPassView setHintText(String text) {
        mTvHint.setText(text);
        return this;
    }

    /**
     * 设置提醒的文字大小
     */
    public PayPassView setTvHintSize(float textSize) {
        mTvHint.setTextSize(textSize);
        return this;
    }

    /**
     * 设置提醒的文字颜色
     */
    public PayPassView setTvHintColor(int textColor) {
        mTvHint.setTextColor(textColor);
        return this;
    }

    /**
     * 获取密码长度 1-9位
     */
    public int getPassWordLength() {
        return passWordLength;
    }

    /**
     * 设置密码长度 1-9位
     */
    public PayPassView setPassWordLength(int passWordLength) {
        this.passWordLength = passWordLength;
        displayPasswordLength();
        return this;
    }

    /**
     * 清楚所有密码TextView
     */
    public PayPassView cleanAllTv() {
        strPass = "";
        for (int i = 0; i < passWordLength; i++)
            mTvPass.get(i).setText("");
        return this;
    }
}
