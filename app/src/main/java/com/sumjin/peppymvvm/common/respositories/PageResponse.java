package com.sumjin.peppymvvm.common.respositories;

import androidx.lifecycle.LiveData;

public class PageResponse {

    private int currentPage;

    private LiveData mLiveData;

    public PageResponse(int currentPage, LiveData liveData) {
        this.currentPage = currentPage;
        mLiveData = liveData;
    }

    public PageResponse() {
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public LiveData getLiveData() {
        return mLiveData;
    }

    public void setLiveData(LiveData liveData) {
        mLiveData = liveData;
    }
}
