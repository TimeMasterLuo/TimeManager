package com.example.timemanager.util.dialog;

import android.content.Context;

public class ShowDialog {

    private FamilyDialog customDialog;

    public ShowDialog() {

    }
    public void show(final Context context, String message, final OnBottomClickListener onBottomClickListener) {
        customDialog = new FamilyDialog(context);
        customDialog.setMessage(message);
        customDialog.setYesOnClickListener("确定", () -> {
            if (onBottomClickListener != null) {
                onBottomClickListener.positive();
            }
            customDialog.dismiss();
        });

        customDialog.setNoOnClickListener("取消", () -> {
            if (onBottomClickListener != null) {
                onBottomClickListener.negative();
            }
            customDialog.dismiss();
        });
        customDialog.show();

    }
    public void show2(final Context context, String message,String confirm, final OnBottomClickListener onBottomClickListener) {
        customDialog = new FamilyDialog(context);
        customDialog.setMessage(message);
        customDialog.setYesOnClickListener(confirm, () -> {
            if (onBottomClickListener != null) {
                onBottomClickListener.positive();
            }
            customDialog.dismiss();
        });

        customDialog.setNoOnClickListener("取消", () -> {
            if (onBottomClickListener != null) {
                onBottomClickListener.negative();
            }
            customDialog.dismiss();
        });
        customDialog.show();

    }
    public interface OnBottomClickListener {
        void positive();

        void negative();

    }
}
