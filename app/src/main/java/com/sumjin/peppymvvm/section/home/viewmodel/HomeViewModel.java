package com.sumjin.peppymvvm.section.home.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.sumjin.peppymvvm.common.model.domain.OnSellContent;
import com.sumjin.peppymvvm.common.respositories.HomeRepository;
import com.sumjin.peppymvvm.common.respositories.PageResponse;
import com.sumjin.peppymvvm.common.net.Resource;

public class HomeViewModel extends AndroidViewModel {

    private HomeRepository mRepository;
    public static final int DEFAULT_PAGE = 1;
    private int mCurrentPage = DEFAULT_PAGE;
    // 商品列表信息
    private LiveData<Resource<OnSellContent>> mGoodsObservable;

    //当前状态  由于多次点击加载更多，出现bug
    private boolean mIsLoading = false;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        mRepository = new HomeRepository();

    }

    public LiveData<Resource<OnSellContent>> getGoodsObservable() {
        return mGoodsObservable;
    }

    //获取商品信息
    public void getGoodsList(){
        if (mGoodsObservable==null) {
            PageResponse onSellPageContent = mRepository.getOnSellPageContent(1, false);
            mGoodsObservable= onSellPageContent.getLiveData();
        }

        //mGoodsObservable.setValue(data);

    }


    public void loadMore(){
        if (mIsLoading) {
            return;
        }
        mIsLoading=true;

        //页面++
        mCurrentPage++;


        PageResponse onSellPageContent = mRepository.getOnSellPageContent(mCurrentPage, true);
        mCurrentPage=onSellPageContent.getCurrentPage();
        mGoodsObservable= onSellPageContent.getLiveData();
        //mGoodsObservable.setValue(data);
        mIsLoading=false;
    }

}
