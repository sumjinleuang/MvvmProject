package com.sumjin.peppymvvm.section.login.viewmodels;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.hyphenate.easeui.domain.EaseUser;
import com.sumjin.peppymvvm.common.net.Resource;
import com.sumjin.peppymvvm.common.respositories.EMClientRepository;

public class LoginFragmentViewModel extends AndroidViewModel {
    private EMClientRepository mRepository;
    private MediatorLiveData<Resource<EaseUser>> loginObservable;

    public LoginFragmentViewModel(@NonNull Application application) {
        super(application);
        mRepository = new EMClientRepository();
        loginObservable = new MediatorLiveData<>();
    }

    /**
     * 登录环信
     * @param userName
     * @param pwd
     * @param isTokenFlag
     */
    public void login(String userName, String pwd, boolean isTokenFlag) {

        loginObservable.addSource(mRepository.loginToServer(userName, pwd, isTokenFlag), response -> {
            loginObservable.setValue(response);
        });
    }

    public LiveData<Resource<EaseUser>> getLoginObservable() {
        return loginObservable;
    }
}
