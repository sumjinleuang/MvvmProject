package com.sumjin.peppymvvm.section.login.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.sumjin.peppymvvm.common.livedatas.SingleSourceLiveData;
import com.sumjin.peppymvvm.common.net.Resource;
import com.sumjin.peppymvvm.common.respositories.EMClientRepository;

import org.jetbrains.annotations.NotNull;

public class LoginViewModel extends AndroidViewModel {
    private EMClientRepository mRepository;
    private SingleSourceLiveData<Resource<String>> registerObservable;
    private SingleSourceLiveData<Integer> pageObservable;


    public LoginViewModel(@NonNull @NotNull Application application) {
        super(application);
        mRepository = new EMClientRepository();
        registerObservable = new SingleSourceLiveData<>();
        pageObservable = new SingleSourceLiveData<>();
    }

    /**
     * 获取页面跳转的observable
     * @return
     */
    public LiveData<Integer> getPageSelect() {
        return pageObservable;
    }

    /**
     * 设置跳转的页面
     * @param page
     */
    public void setPageSelect(int page) {
        pageObservable.setValue(page);
    }

    /**
     * 注册环信账号
     * @param userName
     * @param pwd
     * @return
     */
    public void register(String userName, String pwd) {
       // registerObservable.setSource(mRepository.registerToHx(userName, pwd));
    }

    public LiveData<Resource<String>> getRegisterObservable() {
        return registerObservable;
    }

    /**
     * 清理注册信息
     */
    public void clearRegisterInfo() {
        registerObservable.setValue(null);
    }

}
