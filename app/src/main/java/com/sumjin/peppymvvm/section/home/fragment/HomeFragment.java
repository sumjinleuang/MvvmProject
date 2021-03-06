package com.sumjin.peppymvvm.section.home.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Rect;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.sumjin.peppymvvm.R;
import com.sumjin.peppymvvm.common.model.domain.OnSellContent;
import com.sumjin.peppymvvm.common.net.Resource;
import com.sumjin.peppymvvm.common.util.SizeUtil;
import com.sumjin.peppymvvm.common.util.Status;
import com.sumjin.peppymvvm.common.util.Statuss;
import com.sumjin.peppymvvm.common.util.ToastUtil;
import com.sumjin.peppymvvm.section.base.BaseFragment;
import com.sumjin.peppymvvm.section.home.activity.ScanQRCodeActivity;
import com.sumjin.peppymvvm.section.home.adapter.GoodsAdapter;
import com.sumjin.peppymvvm.section.home.viewmodel.HomeViewModel;

import java.util.List;

import butterknife.BindView;

@SuppressLint("NonConstantResourceId")
public class HomeFragment extends BaseFragment implements GoodsAdapter.OnSellPageItemClickListener {
    public static final int DEFAULT_SPAN_COUNT = 2;
    private static final String TAG = "HomeFragment";

    @BindView(R.id.scan_icon)
    public View mScanBtn;

    @BindView(R.id.list_goods)
    public RecyclerView mGoodsList;

    @BindView(R.id.goods_refresh_layout)
    public TwinklingRefreshLayout mTwinklingRefreshLayout;

    private GoodsAdapter mGoodsAdapter;
    private HomeViewModel mHomeViewModel;

    @Override
    protected View loadRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_base_home, container, false);
    }

    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View rootView) {
        setStatus(Status.SUCCESS);
        mGoodsList.setLayoutManager(new GridLayoutManager(getContext(), DEFAULT_SPAN_COUNT));
        mGoodsAdapter = new GoodsAdapter();
        mGoodsList.setAdapter(mGoodsAdapter);
        mGoodsList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = SizeUtil.px2dip(getContext(),2.5f);
                outRect.bottom = SizeUtil.px2dip(getContext(),2.5f);
                outRect.left = SizeUtil.px2dip(getContext(),2.5f);
                outRect.right = SizeUtil.px2dip(getContext(),2.5f);
            }
        });

        mTwinklingRefreshLayout.setEnableRefresh(false);
        mTwinklingRefreshLayout.setEnableLoadmore(true);
        mTwinklingRefreshLayout.setEnableOverScroll(true);

    }

    @Override
    protected void initListener() {
        mScanBtn.setOnClickListener(v -> {
            //?????????????????????
            startActivity(new Intent(getContext(), ScanQRCodeActivity.class));
        });

        mTwinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                //????????????????????????
               mHomeViewModel.loadMore();
            }
        });
        mGoodsAdapter.setOnSellPageItemClickListener(this);

    }

    @Override
    protected void loadData() {
        //mHomeViewModel.getGoodsList();
    }

    @Override
    protected void initViewModel() {

        ViewModelProvider.AndroidViewModelFactory instance =
                ViewModelProvider.AndroidViewModelFactory
                        .getInstance(getActivity().getApplication());
        mHomeViewModel = new ViewModelProvider(this, instance).get(HomeViewModel.class);

    }

    @Override
    protected void initObservable() {
        mHomeViewModel.getGoodsList();
        mHomeViewModel.getGoodsObservable().observe(this, new Observer<Resource<OnSellContent>>() {
            @Override
            public void onChanged(Resource<OnSellContent> onSellContentResource) {
                updateView(onSellContentResource);
            }
        });
    }

    private void updateView(Resource<OnSellContent> data) {
        Statuss status = data.status;
        switch (data.status){
            case CONTENT:
                setStatus(Status.SUCCESS);
                Log.e(TAG,"?????? ----->"+data.data);
                List<OnSellContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean> dataBeans =
                        data.data.getData().getTbk_dg_optimus_material_response()
                                .getResult_list().getMap_data();
                mGoodsAdapter.setData(dataBeans);
                break;
            case LOADING:
                Log.e(TAG,"??????????????? -----");
                setStatus(Status.LOADING);
                break;
            case ERROR:
                Log.e(TAG,"???????????? -----");
                setStatus(Status.NETWORK_ERROR);
                break;
            case EMPTY:
                Log.e(TAG,"???????????? -----");
                setStatus(Status.EMPTY);
                break;
            case LOAD_MORE:
                Log.e(TAG,"?????????????????? -----");
                List<OnSellContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean> onSell =
                        data.data.getData().getTbk_dg_optimus_material_response()
                                .getResult_list().getMap_data();
                mGoodsAdapter.setMoreLoaded(onSell);
                break;
            case MORE_EMPTY:
                Log.e(TAG,"?????????????????? -----");
                break;
            case MORE_ERROR:
                Log.e(TAG,"?????????????????? -----");
                break;
            case MORE_LOADING:
                Log.e(TAG,"?????????????????? -----");
                break;
        }
    }


    @Override
    public void onSellItemClick(OnSellContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean data) {
        //????????????
        ToastUtil.showToast("????????????????????????");

    }
}
