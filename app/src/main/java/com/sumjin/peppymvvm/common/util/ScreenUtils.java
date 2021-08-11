package com.sumjin.peppymvvm.common.util;

import android.content.Context;

public class ScreenUtils {
    /**
     * dp2px   dp和px 的转换
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
