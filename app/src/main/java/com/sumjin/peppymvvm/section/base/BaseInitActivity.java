package com.sumjin.peppymvvm.section.base;

import android.content.Intent;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sumjin.peppymvvm.R;
import com.sumjin.peppymvvm.common.interfaceOrImplement.OnResourceParseCallback;
import com.sumjin.peppymvvm.common.net.Resource;
import com.sumjin.peppymvvm.common.util.Status;
import com.sumjin.peppymvvm.common.util.Statuss;
import com.sumjin.peppymvvm.common.util.ToastUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseInitActivity extends BaseActivity {

    private Unbinder mBind;
    private boolean isNeedSkin =false;
    public Status mCurrentStatus = Status.NONE;

    private FrameLayout mBaseContainer;
    private View mSuccessView;
    private View mLoadingView;
    private View mErrorView;
    private View mEmptyView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        //清明节换肤，通过网络框架加载值。
        if (isNeedSkin){
            switchSkin();
        }
        mBaseContainer = findViewById(R.id.base_container);
        loadStatusView();

        mBind = ButterKnife.bind(this);

        initIntent(getIntent());
        initView();
        initViewModel();
        initListener();
        initPresenter();
        initObservable();
        initData();
    }

    protected void initIntent(Intent intent) {

    }

    private void loadStatusView() {
        mSuccessView = loadSuccessView();
        mBaseContainer.addView(mSuccessView);

        //LoadingView
        mLoadingView = loadingView();
        mBaseContainer.addView(mLoadingView);
        //错误页面
        mErrorView = loadErrorView();
        mBaseContainer.addView(mErrorView);

        mEmptyView = loadEmptyView();
        mBaseContainer.addView(mEmptyView);

        setStatus(Status.NONE);
    }

    protected View loadEmptyView() {
        return LayoutInflater.from(this).inflate(R.layout.fragment_empty, mBaseContainer,false);
    }

    protected View loadErrorView() {
        return LayoutInflater.from(this).inflate(R.layout.fragment_error, mBaseContainer,false);
    }

    protected View loadingView() {
        return LayoutInflater.from(this).inflate(R.layout.fragment_loading, mBaseContainer,false);
    }

    protected View loadSuccessView() {
        int layoutResId = getLayoutResId();
        return LayoutInflater.from(this).inflate(layoutResId, mBaseContainer,false);
    }

    protected void initObservable() {

    }

    protected void initViewModel() {

    }

    protected void initData() {

    }

    protected void setStatus(Status status) {
        this.mCurrentStatus=status;
        mSuccessView.setVisibility(mCurrentStatus== Status.SUCCESS?View.VISIBLE:View.GONE);
        mLoadingView.setVisibility(mCurrentStatus== Status.LOADING?View.VISIBLE:View.GONE);
        mErrorView.setVisibility(mCurrentStatus== Status.NETWORK_ERROR?View.VISIBLE:View.GONE);
        mEmptyView.setVisibility(mCurrentStatus== Status.EMPTY?View.VISIBLE:View.GONE);
    }

    private void switchSkin() {
        ColorMatrix cm=new ColorMatrix();
        cm.setSaturation(0);
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(cm));

        View contentContainer = getWindow().getDecorView();
//        LogUtils.d(this,"viewById --->"+contentContainer);
        contentContainer.setLayerType(View.LAYER_TYPE_SOFTWARE,paint);
    }

    protected void initPresenter() {

    }

    protected void initListener() {

    }

    protected abstract void initView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBind != null) {
            mBind.unbind();
        }
        this.release();
    }

    protected void release() {

    }

    protected abstract int getLayoutResId();

    /**
     * 解析Resource<T>
     * @param response
     * @param callback
     * @param <T>
     */
    public <T> void parseResource(Resource<T> response, @NonNull OnResourceParseCallback<T> callback) {
        if(mContext != null) {
            if(response == null) {
                return;
            }
            if(response.status == Statuss.SUCCESS) {
                callback.hideLoading();
                callback.onSuccess(response.data);
            }else if(response.status == Statuss.ERROR) {
                callback.hideLoading();
                if(!callback.hideErrorMsg) {
                    ToastUtil.showToast(response.getMessage());
                }
                callback.onError(response.errorCode, response.getMessage());
            }else if(response.status == Statuss.LOADING) {
                callback.onLoading(response.data);
            }
        }
    }



}
