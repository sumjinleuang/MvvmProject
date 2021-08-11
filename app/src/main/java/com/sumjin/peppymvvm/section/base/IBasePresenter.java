package com.sumjin.peppymvvm.section.base;

public interface IBasePresenter<T> {

    void registerCallback(T t);

    void unregisterCallback(T t);

}
