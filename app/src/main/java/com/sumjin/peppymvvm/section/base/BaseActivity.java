package com.sumjin.peppymvvm.section.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.load.engine.Resource;
import com.hyphenate.easeui.utils.StatusBarCompat;
import com.sumjin.peppymvvm.BaseApplication;
import com.sumjin.peppymvvm.R;
import com.sumjin.peppymvvm.common.interfaceOrImplement.UserActivityLifecycleCallbacks;

import java.util.List;

public class BaseActivity extends AppCompatActivity {
    public BaseActivity mContext;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        clearFragmentsBeforeCreate();
        //registerAccountObservable();
    }

    /**
     * 通用页面设置
     */
    public void setFitSystemForTheme() {
        setFitSystemForTheme(true, R.color.white);
        setStatusBarTextColor(true);
    }

    /**
     * 通用页面，需要设置沉浸式
     * @param fitSystemForTheme
     */
    public void setFitSystemForTheme(boolean fitSystemForTheme) {
        setFitSystemForTheme(fitSystemForTheme, R.color.white);
        setStatusBarTextColor(false);
    }

    /**
     * 通用页面，需要设置沉浸式
     * @param fitSystemForTheme
     */
    public void setFitSystemForTheme2(boolean fitSystemForTheme) {
        setFitSystemForTheme(fitSystemForTheme, "#ffffffff");
        setStatusBarTextColor(true);
    }

    /**
     * 设置是否是沉浸式，并可设置状态栏颜色
     * @param fitSystemForTheme
     * @param colorId 颜色资源路径
     */
    public void setFitSystemForTheme(boolean fitSystemForTheme, @ColorRes int colorId) {
        setFitSystem(fitSystemForTheme);
        //初始设置
        StatusBarCompat.compat(this, ContextCompat.getColor(mContext, colorId));
    }

    /**
     * 修改状态栏文字颜色
     * @param isLight 是否是浅色字体
     */
    public void setStatusBarTextColor(boolean isLight) {
        StatusBarCompat.setLightStatusBar(mContext, !isLight);
    }


    /**
     * 设置是否是沉浸式，并可设置状态栏颜色
     * @param fitSystemForTheme true 不是沉浸式
     * @param color 状态栏颜色
     */
    public void setFitSystemForTheme(boolean fitSystemForTheme, String color) {
        setFitSystem(fitSystemForTheme);
        //初始设置
        StatusBarCompat.compat(mContext, Color.parseColor(color));
    }

    /**
     * 设置是否是沉浸式
     * @param fitSystemForTheme
     */
    public void setFitSystem(boolean fitSystemForTheme) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if(fitSystemForTheme) {
            ViewGroup contentFrameLayout = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
            View parentView = contentFrameLayout.getChildAt(0);
            if (parentView != null && Build.VERSION.SDK_INT >= 14) {
                parentView.setFitsSystemWindows(true);
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
    }


    /**
     * 处理因为Activity重建导致的fragment叠加问题
     */
    public void clearFragmentsBeforeCreate() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments.size() == 0){
            return;
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        for (Fragment fragment : fragments) {
            fragmentTransaction.remove(fragment);
        }
        fragmentTransaction.commitNow();
    }

    /**
     * 结束除了当前Activity外的其他Activity
     */
    protected void finishOtherActivities() {
        UserActivityLifecycleCallbacks lifecycleCallbacks = BaseApplication.getInstance().getLifecycleCallbacks();
        if(lifecycleCallbacks == null) {
            finish();
            return;
        }
        List<Activity> activities = lifecycleCallbacks.getActivityList();
        if(activities == null || activities.isEmpty()) {
            finish();
            return;
        }
        for(Activity activity : activities) {
            if(activity != lifecycleCallbacks.current()) {
                activity.finish();
            }
        }
    }

    /**
     * hide keyboard
     */
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm!=null&&getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null){
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }
}
