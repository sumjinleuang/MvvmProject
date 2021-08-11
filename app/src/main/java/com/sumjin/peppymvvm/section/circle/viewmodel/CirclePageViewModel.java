package com.sumjin.peppymvvm.section.circle.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sumjin.peppymvvm.common.model.domain.DynamicInfo;
import com.sumjin.peppymvvm.common.respositories.CircleRepository;
import com.sumjin.peppymvvm.common.respositories.PageResponse;
import com.sumjin.peppymvvm.common.net.Resource;

import java.util.HashMap;
import java.util.Map;

public class CirclePageViewModel extends AndroidViewModel {

    private LiveData<Resource<DynamicInfo>> mDynamicInfoLiveData;

    public static final int DEFAULT_PAGE = 1;
    private Integer mCurrentPage;

    private final Map<Integer, Integer> pageInfo = new HashMap<>();

    private String mCurrentUser;
    private CircleRepository mRepository;
    private int mTitleId;

    public LiveData<Resource<DynamicInfo>> getDynamicInfoLiveData() {
        return mDynamicInfoLiveData;
    }

    public CirclePageViewModel(@NonNull Application application) {
        super(application);
        mRepository = new CircleRepository();
        mDynamicInfoLiveData = new MutableLiveData<>();
    }

    public void setTitleId(int titleId) {
        mTitleId = titleId;
    }

    public void getDynamicInfo(String username, int titleId) {
        this.mCurrentUser = username;
        if (mTitleId == titleId) {

            //根据分类id 去加载内容
            Integer targetPager = pageInfo.get(titleId);
            if (targetPager == null) {
                targetPager = DEFAULT_PAGE;
                pageInfo.put(titleId, targetPager);
            }

            PageResponse goodsList = mRepository.getGoodsList(username, targetPager, false);

            mDynamicInfoLiveData = goodsList.getLiveData();
        }


    }

    public void loadMore(int titleId){
        if (mTitleId == titleId) {
            mCurrentPage = pageInfo.get(titleId);
            if (mCurrentPage == null) {
                mCurrentPage = 1;
            }
            //页面++
            mCurrentPage++;

            PageResponse goodsList = mRepository.getGoodsList(mCurrentUser, mCurrentPage, true);
            mCurrentPage = goodsList.getCurrentPage();
            pageInfo.put(titleId,mCurrentPage);
            mDynamicInfoLiveData = goodsList.getLiveData();
        }
    }


}
