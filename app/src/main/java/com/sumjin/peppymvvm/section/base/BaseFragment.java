package com.sumjin.peppymvvm.section.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sumjin.peppymvvm.R;
import com.sumjin.peppymvvm.common.util.Status;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {
    public Activity mContext;
    public Status mCurrentStatus = Status.NONE;
    private FrameLayout mBaseContainer;
    private View mSuccessView;
    private View mLoadingView;
    private View mErrorView;
    private View mEmptyView;
    private Unbinder mBind;

    @BindView(R.id.network_iv)
    public View netWorkIv;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = loadRootView(inflater,container);
        mBaseContainer = rootView.findViewById(R.id.base_container);
        loadStatusView(inflater,container);

        mBind = ButterKnife.bind(this, rootView);

        initView(rootView);
        initViewModel();
        initListener();
        initPresenter();
        loadData();
        initObservable();

        netWorkIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRetry();
            }
        });
        return rootView;
    }

    protected void onRetry() {

    }

    protected void initObservable() {

    }

    protected void initViewModel(){

    }

    protected View loadRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_base_view, container, false);
    }


    protected void loadData() {

    }

    protected void initPresenter() {

    }

    protected void initListener() {

    }

    protected void initView(View rootView) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mBind != null) {
            mBind.unbind();
        }
        release();
        releaseVideo();
    }

    protected void releaseVideo() {

    }

    @Override
    public void onResume() {
        super.onResume();
        resumeVideo();
    }

    @Override
    public void onPause() {
        super.onPause();
        pauseVideo();
    }


    protected void pauseVideo() {

    }

    protected void resumeVideo() {

    }

    protected void release() {

    }

    protected void loadStatusView(LayoutInflater inflater, ViewGroup container) {
        mSuccessView = loadSuccessView(inflater, container);
        mBaseContainer.addView(mSuccessView);

        //LoadingView
        mLoadingView = loadingView(inflater, container);
        mBaseContainer.addView(mLoadingView);
        //错误页面
        mErrorView = loadErrorView(inflater, container);
        mBaseContainer.addView(mErrorView);

        mEmptyView = loadEmptyView(inflater, container);
        mBaseContainer.addView(mEmptyView);

        setStatus(Status.NONE);


    }

    protected View loadEmptyView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_empty,container,false);
    }

    protected View loadErrorView(LayoutInflater inflater, ViewGroup container) {
        View errorView = inflater.inflate(R.layout.fragment_error, container, false);
        return errorView;
    }

    protected void setStatus(Status status) {
        this.mCurrentStatus=status;
        mSuccessView.setVisibility(mCurrentStatus== Status.SUCCESS?View.VISIBLE:View.GONE);
        mLoadingView.setVisibility(mCurrentStatus== Status.LOADING?View.VISIBLE:View.GONE);
        mErrorView.setVisibility(mCurrentStatus== Status.NETWORK_ERROR?View.VISIBLE:View.GONE);
        mEmptyView.setVisibility(mCurrentStatus== Status.EMPTY?View.VISIBLE:View.GONE);
    }

    protected View loadingView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_loading,container,false);
    }

    protected View loadSuccessView(LayoutInflater inflater, ViewGroup container) {
        int resId=getRootViewResId();
        return inflater.inflate(resId,container,false);
    }

    protected abstract int getRootViewResId();
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = (Activity) context;
    }
}
