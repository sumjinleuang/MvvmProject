package com.sumjin.peppymvvm.section.circle.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sumjin.peppymvvm.common.model.domain.Titles;

import java.util.ArrayList;
import java.util.List;

public class CircleViewModel extends AndroidViewModel {

    private MutableLiveData<List<Titles>> mLiveData;

    public CircleViewModel(@NonNull Application application) {
        super(application);
        mLiveData = new MutableLiveData<>();
    }

    public void getCategories(){
        List<Titles> titles=new ArrayList<>();
        titles.add(new Titles(1,"关注"));
        titles.add(new Titles(2,"圈子"));
        titles.add(new Titles(3,"医生"));

        mLiveData.setValue(titles);
    }

    public MutableLiveData<List<Titles>> getLiveData() {
        return mLiveData;
    }

    public void setLiveData(MutableLiveData<List<Titles>> liveData) {
        mLiveData = liveData;
    }
}
