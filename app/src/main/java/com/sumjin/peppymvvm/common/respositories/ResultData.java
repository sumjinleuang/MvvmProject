package com.sumjin.peppymvvm.common.respositories;

import com.sumjin.peppymvvm.common.model.ApiException;

public class ResultData<T> {

    private Boolean success;
    private int code;
    private String message;
    private T data;

    public static final int CODE_SUCCESS = 10000;

    public T apiData(){
        //如果是成功的code，我们就返回数据，否则抛出异常
        if (code == CODE_SUCCESS){
            return data;
        }else{
            throw new ApiException(code,message);
        }
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
