package com.sumjin.peppymvvm.section.circle.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.sumjin.peppymvvm.R;
import com.sumjin.peppymvvm.common.net.Resource;
import com.sumjin.peppymvvm.common.util.SizeUtil;
import com.sumjin.peppymvvm.common.util.Status;
import com.sumjin.peppymvvm.common.util.Statuss;
import com.sumjin.peppymvvm.section.base.BaseFragment;
import com.sumjin.peppymvvm.common.model.domain.DynamicInfo;
import com.sumjin.peppymvvm.common.model.domain.Titles;
import com.sumjin.peppymvvm.section.circle.adapter.CircleContentAdapter;
import com.sumjin.peppymvvm.common.util.Constants;
import com.sumjin.peppymvvm.common.util.LogUtils;
import com.sumjin.peppymvvm.common.util.PreferenceManager;
import com.sumjin.peppymvvm.section.circle.viewmodel.CirclePageViewModel;

import java.util.List;

import butterknife.BindView;

public class CirclePagerFragment extends BaseFragment {

    private static final String TAG = "CirclePagerFragment";
    private int mCategoryId;

    @BindView(R.id.home_pager_content_list)
    RecyclerView mCircleList;

    @BindView(R.id.home_pager_refresh)
    TwinklingRefreshLayout mRefreshLayout;

    private CircleContentAdapter mCircleContentAdapter;
    private CirclePageViewModel mCirclePageViewModel;

    public static CirclePagerFragment newInstance(Titles titles){
        CirclePagerFragment homePagerFragment=new CirclePagerFragment();
        Bundle bundle=new Bundle();
        bundle.putString(Constants.KEY_HOME_PAGER_TITLE,titles.getTitle());
        bundle.putInt(Constants.KEY_HOME_PAGER_MATEGIRALID,titles.getId());
        homePagerFragment.setArguments(bundle);
        return homePagerFragment;
    }

    @Override
    protected void initView(View rootView) {
        setStatus(Status.SUCCESS);
        mCircleList.setLayoutManager(new LinearLayoutManager(getContext()));
        mCircleContentAdapter = new CircleContentAdapter();
        mCircleList.setAdapter(mCircleContentAdapter);

        mCircleList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top= SizeUtil.px2dip(view.getContext(),8);
                outRect.bottom=SizeUtil.px2dip(view.getContext(),8);
                outRect.right=SizeUtil.px2dip(view.getContext(),8);
                outRect.left=SizeUtil.px2dip(view.getContext(),8);
            }
        });

        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setEnableLoadmore(true);
    }

    @Override
    protected void initListener() {
        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                //加载更多  逻辑
                mCirclePageViewModel.loadMore(mCategoryId);
            }
        });
    }

    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_circle_pager;
    }

    @Override
    protected void loadData() {
        Bundle arguments = getArguments();
        String title = arguments.getString(Constants.KEY_HOME_PAGER_TITLE);
        mCategoryId = arguments.getInt(Constants.KEY_HOME_PAGER_MATEGIRALID);
        LogUtils.d(this,"title =---->"+title);
        LogUtils.d(this,"materiaId----->"+ mCategoryId);
        mCirclePageViewModel.setTitleId(mCategoryId);

        Boolean isLogin= PreferenceManager.getInstance().getAccountIsLogin();
        //如果登录了，通过环信sdk获取，没登录

        String username=isLogin?"test1":"test1";

        //加载动态 类似朋友圈
        mCirclePageViewModel.getDynamicInfo(username,mCategoryId);

    }


    @Override
    protected void initViewModel() {
        ViewModelProvider.AndroidViewModelFactory instance =
                ViewModelProvider.AndroidViewModelFactory
                        .getInstance(getActivity().getApplication());

        mCirclePageViewModel = new ViewModelProvider(this, instance).get(CirclePageViewModel.class);
    }

    @Override
    protected void initObservable() {
        mCirclePageViewModel.getDynamicInfoLiveData().observe(this, new Observer<Resource<DynamicInfo>>() {
            @Override
            public void onChanged(Resource<DynamicInfo> dynamicInfoResource) {
                updateView(dynamicInfoResource);

            }
        });
    }

    @Override
    protected void onRetry() {
        super.onRetry();
        setStatus(Status.SUCCESS);
        DynamicInfo netErrorData = getNetErrorData();
        mCircleContentAdapter.setData(netErrorData.getDatas());
    }

    private void updateView(Resource<DynamicInfo> data) {
        Statuss status = data.status;
        switch (data.status){
            case CONTENT:
                Log.e(TAG,"内容 ----->"+data.data);
                setStatus(Status.SUCCESS);
                List<DynamicInfo.DatasBean> dataBean = data.data.getDatas();
                mCircleContentAdapter.setData(dataBean);
                break;
            case LOADING:
                Log.e(TAG,"正在加载中 -----");
                setStatus(Status.LOADING);
                break;
            case ERROR:
                Log.e(TAG,"网络错误 -----");
                setStatus(Status.NETWORK_ERROR);
                break;
            case EMPTY:
                Log.e(TAG,"内容为空 -----");
                setStatus(Status.EMPTY);
                break;
            case LOAD_MORE:
                Log.e(TAG,"加载更多成功 -----");
                break;
            case MORE_EMPTY:
                Log.e(TAG,"没有更多内容 -----");
                break;
            case MORE_ERROR:
                Log.e(TAG,"加载更多失败 -----");
                break;
            case MORE_LOADING:
                Log.e(TAG,"正在加载更多 -----");
                break;
        }
    }

    private DynamicInfo getNetErrorData() {
        String netErrorString ="{\"msg\":\"获取数据成功\",\"state\":10000,\"datas\":[{\"id\":15,\"nickname\":\"22333\",\"username\":\"test2\",\"content\":\"发顺丰散发刷卡缴费卢卡斯\",\"createtime\":\"2020-08-05 14:39:31.0\",\"likes\":0,\"comments\":0,\"shares\":0,\"dynamic_type\":\"text\",\"status\":1,\"download_url\":\"\",\"state\":0},{\"id\":14,\"nickname\":\"222\",\"username\":\"test1\",\"content\":\"发顺丰散发刷卡缴费卢卡斯\",\"createtime\":\"2020-08-05 14:39:30.0\",\"likes\":0,\"comments\":7,\"shares\":0,\"dynamic_type\":\"text\",\"status\":1,\"download_url\":\"\",\"state\":0},{\"id\":13,\"nickname\":\"222\",\"username\":\"test1\",\"content\":\"发顺丰散发刷卡缴费卢卡斯\",\"createtime\":\"2020-08-05 14:39:29.0\",\"likes\":0,\"comments\":0,\"shares\":0,\"dynamic_type\":\"text\",\"status\":1,\"download_url\":\"\",\"state\":0},{\"id\":12,\"nickname\":\"222\",\"username\":\"test1\",\"content\":\"发顺丰散发刷卡缴费卢卡斯\",\"createtime\":\"2020-08-05 14:39:28.0\",\"likes\":0,\"comments\":0,\"shares\":0,\"dynamic_type\":\"text\",\"status\":1,\"download_url\":\"\",\"state\":0},{\"id\":11,\"nickname\":\"222\",\"username\":\"test1\",\"content\":\"发顺丰散发刷卡缴费卢卡斯\",\"createtime\":\"2020-08-05 14:39:27.0\",\"likes\":0,\"comments\":0,\"shares\":0,\"dynamic_type\":\"text\",\"status\":1,\"download_url\":\"\",\"state\":0},{\"id\":10,\"nickname\":\"222\",\"username\":\"test1\",\"content\":\"发顺丰散发刷卡缴费卢卡斯\",\"createtime\":\"2020-08-05 14:39:26.0\",\"likes\":0,\"comments\":0,\"shares\":0,\"dynamic_type\":\"text\",\"status\":1,\"download_url\":\"\",\"state\":0},{\"id\":9,\"nickname\":\"222\",\"username\":\"test1\",\"content\":\"发顺丰散发刷卡缴费卢卡斯\",\"createtime\":\"2020-08-05 14:39:25.0\",\"likes\":0,\"comments\":0,\"shares\":0,\"dynamic_type\":\"text\",\"status\":1,\"download_url\":\"\",\"state\":0},{\"id\":8,\"nickname\":\"222\",\"username\":\"test1\",\"content\":\"发顺丰散发刷卡缴费卢卡斯\",\"createtime\":\"2020-08-05 14:39:24.0\",\"likes\":0,\"comments\":0,\"shares\":0,\"dynamic_type\":\"text\",\"status\":1,\"download_url\":\"\",\"state\":0},{\"id\":7,\"nickname\":\"222\",\"username\":\"test1\",\"content\":\"发顺丰散发刷卡缴费卢卡斯\",\"createtime\":\"2020-08-05 14:39:23.0\",\"likes\":0,\"comments\":0,\"shares\":0,\"dynamic_type\":\"text\",\"status\":1,\"download_url\":\"\",\"state\":0},{\"id\":6,\"nickname\":\"222\",\"username\":\"test1\",\"content\":\"发顺丰散发刷卡缴费卢卡斯\",\"createtime\":\"2020-08-05 10:28:17.0\",\"likes\":0,\"comments\":0,\"shares\":0,\"dynamic_type\":\"text\",\"status\":1,\"download_url\":\"\",\"state\":0}],\"page\":1,\"size\":10,\"count\":10}";
        Gson gson = new Gson();
        DynamicInfo dynamicInfo = gson.fromJson(netErrorString,DynamicInfo.class);

        return dynamicInfo;
    }


}
