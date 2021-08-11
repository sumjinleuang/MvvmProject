package com.sumjin.peppymvvm.common.util;

import android.widget.Toast;

import com.sumjin.peppymvvm.BaseApplication;

public class ToastUtil {
    private static Toast sToast;

    public static void showToast(String tips) {
        if(sToast == null) {
            sToast = Toast.makeText(BaseApplication.getContext(),tips,Toast.LENGTH_SHORT);
        } else {
            sToast.setText(tips);
        }
        sToast.show();
    }
}
