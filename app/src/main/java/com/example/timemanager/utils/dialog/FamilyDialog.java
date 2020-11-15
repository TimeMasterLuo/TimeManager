package com.example.timemanager.utils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.timemanager.R;

public class FamilyDialog extends Dialog {
    private Button yes; //确定按钮
    private Button no; //取消按钮
    //    private TextView title; //消息标题文本
    private TextView message; //消息提示文本
    private String messageStr; //从外界设置的消息文本
    private String yesStr, noStr; //确定文本和取消文本的显示内容
    private onYesOnClickListener yesOnClickListener; //确定按钮被点击了的监听器
    private onNoClickListener noOnClickListener; //取消按钮被点击了的监听器


    public FamilyDialog(@NonNull Context context) {
        super(context, R.style.CustomDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dialog_family);
        //点击dialog以外的空白处是否隐藏
        setCanceledOnTouchOutside(false);
        //初始化界面控件
        initView();
        //初始化界面数据
        initData();
        //初始化界面控件的事件
        initEvent();
        //设置窗口显示
        windowDeploy();
    }

    /**
     * 初始化界面控件
     */
    private void initView() {
        yes = (Button) findViewById(R.id.yes);
        no = (Button) findViewById(R.id.no);
//        title = (TextView) findViewById(R.id.title);
        message = (TextView) findViewById(R.id.message);
    }

    /**
     * 初始化界面控件的显示数据
     */
    private void initData() {
//        if (TextUtils.isEmpty(titleStr)) {
//            title.setText(titleStr);
//        } else {
//            title.setText("应用提示");
//        }
        if (messageStr != null) {
            message.setText(messageStr);
        }
        if (yesStr != null) {
            yes.setText(yesStr);
        }
        if (noStr != null) {
            no.setText(noStr);
        }
    }

    /**
     * 初始化界面的确定和取消监听器
     */
    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        yes.setOnClickListener(view -> {
            if (yesOnClickListener != null) {
                yesOnClickListener.onYesClick();
            }
        });

        //设置取消按钮被点击后，向外界提供监听
        no.setOnClickListener(view -> {
            if (noOnClickListener != null) {
                noOnClickListener.onNoClick();
            }
        });
    }

    private void windowDeploy() {
        Window window = getWindow();
        assert window != null;
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.CENTER);
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        //window.setGravity(Gravity.CENTER); //设置窗口显示位置
//        window.setWindowAnimations(R.style.dialogWindowAnim); //设置窗口弹出动画
    }

    /**
     * 设置确定按钮的显示内容和监听
     *
     * @param str;
     * @param onYesOnClickListener;
     */
    public void setYesOnClickListener(String str, onYesOnClickListener onYesOnClickListener) {
        if (str != null) {
            yesStr = str;
        }
        this.yesOnClickListener = onYesOnClickListener;
    }

    /**
     * 设置取消按钮的显示内容和监听
     *
     * @param str;
     * @param onNoClickListener;
     */
    public void setNoOnClickListener(String str, onNoClickListener onNoClickListener) {
        if (str != null) {
            noStr = str;
        }
        this.noOnClickListener = onNoClickListener;
    }

    /**
     * 从外界Activity为Dialog设置标题
     *
     * @param title;
     */
    public void setTitle(String title) {
        //从外界设置的title文本
    }

    /**
     * 从外界Activity为Dialog设置dialog的message
     *
     * @param message;
     */
    public void setMessage(String message) {
        messageStr = message;
    }

    /**
     * 设置确定按钮和取消被点击的接口
     */
    public interface onYesOnClickListener {
        void onYesClick();
    }

    public interface onNoClickListener {
        void onNoClick();
    }
}

