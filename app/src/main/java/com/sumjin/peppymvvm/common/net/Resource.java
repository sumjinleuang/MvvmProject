package com.sumjin.peppymvvm.common.net;

import android.text.TextUtils;

import androidx.annotation.Nullable;


import com.sumjin.peppymvvm.BaseApplication;
import com.sumjin.peppymvvm.common.util.Statuss;

public class Resource<T> {
    public Statuss status;

    public T data;
    public Throwable error;

    public int errorCode;
    private String message;
    private int messageId;

    public int currentPage;


    public Resource(Statuss status, T data, int errorCode) {
        this.status = status;
        this.data = data;
        this.errorCode = errorCode;
        this.messageId = ErrorCode.Error.parseMessage(errorCode).getMessageId();
    }

    public Resource(Statuss status, T data, int errorCode, String message) {
        this.status = status;
        this.data = data;
        this.errorCode = errorCode;
        this.message = message;
    }


    private Resource(Statuss status, T data, Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    //环信--
    public static <T> Resource<T> success(@Nullable T data) {
        return new Resource<>(Statuss.SUCCESS, data, ErrorCode.EM_NO_ERROR);
    }

    public static <T> Resource<T> error(int code, @Nullable T data) {
        return new Resource<>(Statuss.ERROR, data, code);
    }

    public static <T> Resource<T> error(int code, String message, @Nullable T data) {
        return TextUtils.isEmpty(message) ?
                new Resource<>(Statuss.ERROR, data, code) :
                new Resource<>(Statuss.ERROR, data, code, message);
    }



    /**
     * 获取错误信息
     * @return
     */
    public String getMessage() {
        if(!TextUtils.isEmpty(message)) {
            return message;
        }
        if(messageId > 0) {
            return BaseApplication.getInstance().getString(messageId);
        }
        return "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resource<?> resource = (Resource<?>) o;

        if (errorCode != resource.errorCode) return false;
        if (status != resource.status) return false;
        if (data != null ? !data.equals(resource.data) : resource.data != null) return false;
        return message != null ? message.equals(resource.message) : resource.message == null;
    }

    @Override
    public int hashCode() {
        int result = status != null ? status.hashCode() : 0;
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + errorCode;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "mStatus=" + status +
                ", data=" + data +
                ", errorCode=" + errorCode +
                ", message='" + message + '\'' +
                '}';
    }

    //自己设计的---
    public static <T> Resource<T> content(T data) {
        return new Resource<>(Statuss.CONTENT, data, null);
    }

    public static <T> Resource<T> error(T data, Throwable error) {
        return new Resource<>(Statuss.ERROR, data, error);
    }
    public static <T> Resource<T> error(Throwable error) {
        return error(null, error);
    }

    public static <T> Resource<T> empty(T data) {
        return new Resource<>(Statuss.EMPTY, data, null);
    }
    public static <T> Resource<T> empty() {
        return empty(null);
    }

    public static <T> Resource<T> loading(T data) {
        return new Resource<>(Statuss.LOADING, data, null);
    }
    public static <T> Resource<T> loading() {
        return loading(null);
    }

    public static <T> Resource<T> loadMore(T data) {
        return new Resource<>(Statuss.LOAD_MORE, data, null);
    }

    public static <T> Resource<T> loadError(T data, Throwable error) {
        return new Resource<>(Statuss.MORE_ERROR, data, error);
    }
    public static <T> Resource<T> loadError(Throwable error) {
        return loadError(null, error);
    }

    public static <T> Resource<T> loadEmpty(T data) {
        return new Resource<>(Statuss.MORE_EMPTY, data, null);
    }
    public static <T> Resource<T> loadEmpty() {
        return loadEmpty(null);
    }

    public static <T> Resource<T> moreLoading(T data) {
        return new Resource<>(Statuss.MORE_LOADING, data, null);
    }
    public static <T> Resource<T> moreLoading() {
        return moreLoading(null);
    }
}
