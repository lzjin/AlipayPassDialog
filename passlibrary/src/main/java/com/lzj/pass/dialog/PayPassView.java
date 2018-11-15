package com.lzj.pass.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/11/15.
 *  自定义支付密码组件
 */

public class PayPassView extends RelativeLayout {
    private Activity mContext;//上下文
    private GridView mGridView; //支付键盘
    private String      strPass="";//保存密码
    private TextView[]  mTvPass;//密码数字控件
    private ImageView mImageViewClose;//关闭
    private TextView    mTvForget;//忘记密码
    private TextView    mTvHint;//提示 (提示:密码错误,重新输入)
    private List<Integer> listNumber;//1,2,3---0
    private View mPassLayout;//布局

    /**
     * 按钮对外接口
     */
    public static interface OnPayClickListener {
        void onPassFinish(String passContent);
        void onPayClose();
        void onPayForget();
    }
    private OnPayClickListener mPayClickListener;
    public void setPayClickListener(OnPayClickListener listener) {
        mPayClickListener = listener;
    }
    //在代码new使用
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
        mPassLayout = LayoutInflater.from(mContext).inflate( R.layout.view_paypass_layout, null);

        mImageViewClose  = (ImageView) mPassLayout.findViewById(R.id.iv_close);//关闭
        mTvForget   = (TextView) mPassLayout.findViewById(R.id.tv_forget);//忘记密码
        mTvHint     = (TextView) mPassLayout.findViewById(R.id.tv_passText);//提示文字
        mTvPass     = new TextView[6];                                  //密码控件
        mTvPass[0]  = (TextView) mPassLayout.findViewById(R.id.tv_pass1);
        mTvPass[1]  = (TextView) mPassLayout.findViewById(R.id.tv_pass2);
        mTvPass[2]  = (TextView) mPassLayout.findViewById(R.id.tv_pass3);
        mTvPass[3]  = (TextView) mPassLayout.findViewById(R.id.tv_pass4);
        mTvPass[4]  = (TextView) mPassLayout.findViewById(R.id.tv_pass5);
        mTvPass[5]  = (TextView) mPassLayout.findViewById(R.id.tv_pass6);
        mGridView   = (GridView) mPassLayout.findViewById(R.id.gv_pass);

        mImageViewClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cleanAllTv();
                mPayClickListener.onPayClose();
            }
        });
        mTvForget.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mPayClickListener.onPayForget();
            }
        });

        //初始化数据
        listNumber = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            listNumber.add(i);
        }
        listNumber.add(10);
        listNumber.add(0);
        listNumber.add(R.mipmap.ic_pay_del0);

        mGridView.setAdapter(adapter);
    }


    /**
     *   GridView的适配器
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
                holder.btnNumber = (TextView) convertView.findViewById(R.id.btNumber);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            //-------------设置数据----------------
            holder.btnNumber.setText(listNumber.get(position)+"");
            if (position == 9) {
                holder.btnNumber.setText("");
                holder.btnNumber.setBackgroundColor(mContext.getResources().getColor(R.color.graye3));
            }
            if (position == 11) {
                holder.btnNumber.setText("");
                holder.btnNumber.setBackgroundResource(listNumber.get(position));
            }
            //监听事件----------------------------
            if(position==11) {
                holder.btnNumber.setOnTouchListener(new OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
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
                    }
                });
            }
            holder.btnNumber.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position < 11 &&position!=9) {//0-9按钮
                        if(strPass.length()==6){
                            return;
                        }
                        else {
                            strPass=strPass+listNumber.get(position);//得到当前数字并累加
                            mTvPass[strPass.length()-1].setText("*"); //设置界面*
                            //输入完成
                            if(strPass.length()==6){
                                mPayClickListener.onPassFinish(strPass);//请求服务器验证密码
                            }
                        }
                    }
                    else if(position == 11) {//删除
                        if(strPass.length()>0){
                            mTvPass[strPass.length()-1].setText("");//去掉界面*
                            strPass=strPass.substring(0,strPass.length()-1);//删除一位
                        }
                    }
                    if(position==9){//空按钮
                    }
                }
            });

            return convertView;
        }
    };
    static class ViewHolder {
        public TextView btnNumber;
    }



    /**
     * 关闭图片
     * 资源方式
     */
    public void setCloseImgView(int resId) {
        mImageViewClose.setImageResource(resId);
    }
    /**
     * 关闭图片
     * Bitmap方式
     */
    public void setCloseImgView(Bitmap bitmap) {
        mImageViewClose.setImageBitmap(bitmap);
    }
    /**
     * 关闭图片
     * drawable方式
     */
    public void setCloseImgView(Drawable drawable) {
        mImageViewClose.setImageDrawable(drawable);
    }


    /**
     * 设置忘记密码文字
     */
    public void setForgetText(String text) {
        mTvForget.setText(text);
    }
    /**
     * 设置忘记密码文字大小
     */
    public void setForgetSize(float textSize) {
        mTvForget.setTextSize(textSize);
    }
    /**
     * 设置忘记密码文字颜色
     */
    public void setForgetColor(int textColor) {
        mTvForget.setTextColor(textColor);
    }

    /**
     * 设置提醒的文字
     */
    public void setHintText(String text) {
        mTvHint.setText(text );
    }
    /**
     * 设置提醒的文字大小
     */
    public void setTvHintSize(float textSize) {
        mTvHint.setTextSize(textSize);
    }
    /**
     * 设置提醒的文字颜色
     */
    public void setTvHintColor(int textColor) {
        mTvHint.setTextColor(textColor);
    }
    /**
     * 清楚所有密码TextView
     */
    public void cleanAllTv() {
        strPass="";
        for(int i=0;i<6;i++){
            mTvPass[i].setText("");
        }
    }
}
